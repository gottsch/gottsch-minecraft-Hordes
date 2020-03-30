/**
 * 
 */
package com.someguyssoftware.hordes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.BaseConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.someguyssoftware.hordes.block.HordestoneBlock;
import com.someguyssoftware.hordes.eventhandler.TickHandler;
import com.someguyssoftware.hordes.eventhandler.WorldLoadHandler;
import com.someguyssoftware.hordes.item.WorldSavedDataItem;
import com.someguyssoftware.hordes.tileentity.HordestoneTileEntity;
import com.someguyssoftware.hordes.world.HordesWorldSavedData;
import com.someguyssoftware.hordes.worldgen.HordeWorldGen;
import com.someguyssoftware.plans.ConnectorPlans;
import com.someguyssoftware.plans.ConnectorPlansProcessor;
import com.someguyssoftware.plans.Plans;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author Mark Gottschling on Dec 7, 2014
 *
 */
@Mod(modid = Hordes.modid, name = "Hordes", version = "1.0")
public class Hordes {
	// credit
	private static String credits = "Hordes was developed by Mark 'gottsch' Gottschling on Dec 7, 2014";
	
	// logger
	public static Logger logger = LogManager.getLogger("Hordes");
	
	// unique id for mod - must also be the sub-package name where this mod's assets reside
	public static final String modid = "sgs_hordes";

	@Instance(value="sgs_hordes")
	public static Hordes instance;
	
	@SidedProxy(clientSide="com.someguyssoftware.hordes.client.ClientProxy", serverSide="com.someguyssoftware.hordes.CommonProxy")
	public static CommonProxy proxy;
	
	/**
	 * This is the worldSavedData in NBT format
	 */
	private static HordesWorldSavedData hordeWorldSavedData;
	
	/**
	 * This isthe HordeMind object.  This is the main processor for each horde community.
	 */
	private static HordeMind hordeMind;
	
	// test item
	public Item worldSavedDataItem;
	
	// plans map
    public static Map<String, List<Plans>> plansMap;
    
    // plans processor
    public static ConnectorPlansProcessor plansProcessor;
	
    // blocks
    public static HordestoneBlock hordestone;
    
    // items
    
    // tile entities
    public static HordestoneTileEntity hordestoneTileEntity;
    
	/**
	 * 
	 * @param suggestedConfigurationFile
	 */
    private void initialize(File suggestedConfigurationFile) {
		// load properties from configuration file
		HordesProperties.initialize(suggestedConfigurationFile);
    	
		// configure logging
		configLogging();
    	
    	// intialize the horde mind
    	if (getHordeMind() == null) {
    		setHordeMind(new HordeMind());
    	}
    	
    	// blocks
    	hordestone = new HordestoneBlock(Material.rock);
    	
    	// items
    	worldSavedDataItem = new WorldSavedDataItem();
    	
    	// tile entities
    	
    	// create a plans processor
    	plansProcessor = new ConnectorPlansProcessor();
    	
		// load all the structure plans
		plansMap = new HashMap<String, List<Plans>>();
		//loadPlansMap(plansMap);
	}
    
    /**
     * Create all objects, tabs, etc and register with Minecraft
     * @param event
     */
	@EventHandler
	public void preInt(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new WorldLoadHandler());
		//MinecraftForge.EVENT_BUS.register(new TickHandler());
		FMLCommonHandler.instance().bus().register(new TickHandler());
		
		// initialize
		initialize(event.getSuggestedConfigurationFile());
				
		// register blocks
		GameRegistry.registerBlock(hordestone, "horde_spawner");
		
		// register items
		GameRegistry.registerItem(worldSavedDataItem, "test-item");
		
		// register tile entities
		GameRegistry.registerTileEntity(HordestoneTileEntity.class, "horde_spawner_tile_entity");
	}

	/**
     * Generate world, initialize proxy
     * @param event
     */
	@EventHandler
	public void load(FMLInitializationEvent event) {
		// for multiplayer
        proxy.registerRenderers();
        
    	// world generators
        GameRegistry.registerWorldGenerator(new HordeWorldGen(), 20);

	}
	
    @EventHandler
    public void serverStarted(FMLServerStartingEvent event) {
    	// the horde mind will load here from WorldSavedData
    }
    
	/**
	 * Create entities
	 * @param event
	 */
	@EventHandler
	//@PostInit
	public void postInit(FMLPostInitializationEvent event) {
	}

	/**
	 * 
	 * @param plansMap
	 */
	private static void loadPlansMap(Map<String, List<Plans>> plansMap) {
		// load a list with all the folders
		List<String> plansFolders = new ArrayList<String>();
		plansFolders.add(HordesProperties.barbarianDirectory);
		plansFolders.add(HordesProperties.vikingDirectory);
		plansFolders.add(HordesProperties.duergarDirectory);

		
		// load the plans
		for (String folder : plansFolders) {
			// cycle through all files in the folder
			File[] files = new File(folder).listFiles();
			for (File file : files) {
				if (!file.isDirectory()) {
					try {
						// for all the plans folders
						Hordes.logger.info("Loading plans " + file.getPath());
						ConnectorPlans plans = (ConnectorPlans) loadPlans(file);
						// TODO plans in the map
						if (!plansMap.containsKey(folder)) {
							List<Plans> plansList = new ArrayList<Plans>();
							plansList.add(plans);
							plansMap.put(folder, plansList);
						}
						else {
							plansMap.get(folder).add(plans);
						}
					} catch (Exception e) {
						Hordes.logger.error("Unable to load plans: " + e);
						continue;
					}
				}
			}
		}
	}
	
	/**
	 * Loads a specific plans file.
	 * @param folder
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Plans loadPlans(File file) throws Exception {
		Hordes.logger.debug("Selected plans to load: " + file.getCanonicalPath());
		// load the plans						
		ConnectorPlans plans =
			(ConnectorPlans) plansProcessor.load(
					file.getCanonicalPath(), ConnectorPlans.class);		
		return plans;
	}
	
	//////////  Accessors / Mutators /////////////////

	public static synchronized HordesWorldSavedData getHordeWorldSavedData() {
		return hordeWorldSavedData;
	}

	public static synchronized void setHordeWorldSavedData(
			HordesWorldSavedData hordeWorldSavedData) {
		Hordes.hordeWorldSavedData = hordeWorldSavedData;
	}
	
	/**
	 * Add rolling file appender to the current logging system.
	 */
	public static void configLogging() {
		// get config properties
		String loggerLevel = HordesProperties.loggerLevel;
		String loggerFolder = HordesProperties.loggerFolder;
		
		// ensure the folder ends with a forward-slash
		if (!loggerFolder.endsWith("/")) {
			loggerFolder += "/";
		}

		final LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        final Configuration config = loggerContext.getConfiguration();
        
        // create a size-based trigger policy for 500K
        SizeBasedTriggeringPolicy policy = SizeBasedTriggeringPolicy.createPolicy("500K");
        // create the pattern for log statements
        PatternLayout layout = PatternLayout.createLayout("%d [%t] %p %c | %F:%L | %m%n", null, null, null, "true");
        
        // create a rolling file appender for SGS_Treasure logger (which is used by the Treasure mod)
        Appender appender = RollingFileAppender.createAppender(
        	loggerFolder + "hordes.log",
        	loggerFolder + "hordes-%d{yyyy-MM-dd-HH_mm_ss}.log",
        	"true",
        	"HORDES",
        	"true",
            "true",
            policy,
            null,
            layout,
            null,
            "true",
            "false",
            null,
            config);

        // start the appender
        appender.start();
        
        // add appenders to config
        ((BaseConfiguration) config).addAppender(appender);
        
        // create appender references
        AppenderRef metalsAppenderReference = AppenderRef.createAppenderRef("HORDES", null, null);
        
        // create logger config
        AppenderRef[] refs = new AppenderRef[] {metalsAppenderReference};
        
        // set the logger name "SGS Metals" to use the rolling file appender
        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", loggerLevel, "Hordes", "true", refs, null, config, null );
        
        // add appenders to logger config
        loggerConfig.addAppender(appender, null, null);
        
        // add loggers to base configuratoin
        ((BaseConfiguration) config).addLogger("Hordes", loggerConfig);
        
        // update existing loggers
        loggerContext.updateLoggers();	
	}

	public static HordeMind getHordeMind() {
		return hordeMind;
	}

	public static void setHordeMind(HordeMind hordeMind) {
		Hordes.hordeMind = hordeMind;
	}
}
