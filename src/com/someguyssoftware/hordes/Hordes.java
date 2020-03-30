/**
 * @author Mark Gottschling on Aug 16, 2015
 *
 */
/**
 * 
 */
package com.someguyssoftware.hordes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

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

import com.someguyssoftware.hordes.config.GeneralConfig;
import com.someguyssoftware.hordes.config.HordesConfiguration;
import com.someguyssoftware.hordes.eventhandler.PlayerEventHandler;
import com.someguyssoftware.mod.version.Version;
import com.someguyssoftware.mod.version.VersionChecker;
import com.someguyssoftware.plans.IPlan;
import com.someguyssoftware.plans.Plan;
import com.someguyssoftware.plans.PlansManager;
import com.someguyssoftware.plans.builder.IPlansBuilder;
import com.someguyssoftware.plans.builder.PlansBuilder;
import com.someguyssoftware.plans.config.IJsonMixinConfig;
import com.someguyssoftware.plans.io.IPlansFileHandler;
import com.someguyssoftware.plans.io.PlansFileHandler;

/**
 * 
 * @author Mark Gottschling on Aug 16, 2015
 *
 */
@Mod(modid=Hordes.modid, name=Hordes.name, version=Hordes.version, dependencies="required-after:PlansAPI@[4.0]")
public class Hordes {
		// constants
		private static final String VERSION_URL = "https://www.dropbox.com/s/7fo371bk8isv8pd/hordes-versions.json?dl=1";
		private static final String MINECRAFT_VERSION = "1.8";
		
		public static final String modid = "Hordes";
		public static final String name = "Hordes!";
		public static final String version = "1.0"; 
		
		// latest version
		public static Version latestVersion;
		public static boolean isVersionChecked = false;
		
		// credit
		@SuppressWarnings("unused")
		private static String credits = "Hordes was first developed by Mark Gottschling on August 16, 2015.";
		
		// logger
		public static Logger log = LogManager.getLogger("Hordes");
		
		@Instance(value=Hordes.modid)
		public static Hordes instance = new Hordes();
		
		@SidedProxy(clientSide="com.someguyssoftware.hordes.client.ClientProxy", serverSide="com.someguyssoftware.hordes.CommonProxy")
		public static CommonProxy proxy;
		
		// simple network wrapper
	    public static SimpleNetworkWrapper network = null;

		// Hordes! world generators
		public static Map<String, WorldGenerator> worldGenerators;
		
		// forge world generators
//	    public static HordesWorldGen hordesWorldGen;
	    
	    // hordes manager
	    public static HordesManager hordesManager;
	    
	    /**
	     * 
	     */
	    public Hordes() {
	    	
	    }
	    
		@EventHandler
		public void preInt(FMLPreInitializationEvent event) {
			// register player events (version checker)
			FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
				
	        // register the packet handlers
	        network = NetworkRegistry.INSTANCE.newSimpleChannel(Hordes.modid);
	        // TODO create messages to be received on the client side
	        // TODO research 1.8
//	        network.registerMessage(HermitCancelMessageHandler.class, HermitCancelMessage.class, 0, Side.CLIENT);
//	        network.registerMessage(HermitSuccessMessageHandler.class, HermitSuccessMessage.class, 1, Side.CLIENT);
	        
			// initialize
			//initialize(event.getSuggestedConfigurationFile());
			initialize(event.getModConfigurationDirectory());
			
			// register blocks
			registerBlocks();
			
			// register items
			registerItems();
			
			// regsiter tile entities
			registerTileEntities();
		}
		
		@EventHandler
		public void init(FMLInitializationEvent event) {
			// load the world (minecraft) low-level generators map (NOTE must be AFTER all blocks are registered)
			log.debug("Setting up generator map");
			
	        if (GeneralConfig.enableHordes) {
//	    		HordesGenerator hordesGenerator = new HordesGenerator();
//	    		getWorldGenerators().put("HordesGen", hordesGenerator);
	    		
	    		// add to world generators
//	    		hordesWorldGen = new HordesWorldGen();
//	        	GameRegistry.registerWorldGenerator(hordesWorldGen, 100);
	        }		
			
			// register client renderers
			proxy.registerRenderers();
		}
		
		@EventHandler
		public void postInit(FMLPostInitializationEvent event) {
			// get the latest version from the website
			latestVersion = VersionChecker.getVersion(VERSION_URL, MINECRAFT_VERSION);
		}
		
		/** 
		 * Non-EventHandler initialization method
		 */
		private void initialize(File configFile) {		
			// load properties from configuration files
			HordesConfiguration.initialize(configFile);

			// configure logging
			configLogging();
			
//			tabItem = new PlansCreatorTabItem().setUnlocalizedName(GeneralConfig.tabId);
//			tab = new CreativeTabs(CreativeTabs.getNextID(), GeneralConfig.tabId) {
//		        @SideOnly(Side.CLIENT)
//		        public Item getTabIconItem() {
//		            return tabItem;
//		        }
//		    };
			
			// initialize the world generators map
			setWorldGenerators(new LinkedHashMap<String, WorldGenerator>());
			
			// initialize hordes manager
			log.debug("Initializing DungeonManager...");
			try {
				hordesManager = new HordesManager(PlansManager.getInstance().getConnection());
			} catch (ClassNotFoundException |SQLException e) {
				log.error("Unable to create an instance o fHordesManager:", e);
				e.printStackTrace();
			}
			
			// load plans - TODO from config
			try {
				loadPlans("");
			} catch (Exception e) {
				log.error("Error occurred attempting to load plans:", e);
			}
		}
		
		// TODO move these to CommonProxy
		/**
		 * 
		 */
		public void registerBlocks() {
			
		}
		
		/**
		 * 
		 */
		public void registerItems() {
			
		}
		
		/**
		 * 
		 */
		public void registerTileEntities() {
			
		}
		
		/**
		 * 
		 * @param path
		 */
		public void loadPlans(String path) throws FileNotFoundException, Exception {
			if (path == null || path.isEmpty()) {
				path = "mods/hordes/plans";
			}

			// determine if a valid path/directory
			File file = new File(path);
			if (!file.isDirectory()) {								
				throw new FileNotFoundException(String.format("The path \"%s\" is not valid.", path));
			}
			
			// TODO may want to get the plan builder from the hordes generator or have a common Labyrinths or PlanBuilder
			// get the plans builder config
			IPlansBuilder plansBuilder = new PlansBuilder();
			// load the plan
			IPlansFileHandler fileHandler = new PlansFileHandler((IJsonMixinConfig) plansBuilder.getConfig());
			
			// create a Path from path
			Path dir = FileSystems.getDefault().getPath(path);
			
			// read all files and place in registry if not already there
			DirectoryStream<Path> stream = null;
			try {
				stream = Files.newDirectoryStream(dir, "*.plan.json");
				// for every plan file in the given directory
				for (Path entry : stream) {
					String key = entry.toString().replace("\\",  "/");				

					IPlan plan = fileHandler.load(key, Plan.class);
					
					// register the plan
					if (plan != null) {	
						log.info("Registering plan with key:" + key);
						PlansManager.getInstance().add(key, plan);
					}
					else {
						log.warn("Unable to load plan " + key);
					}
				}
			}
			catch(DirectoryIteratorException | IOException e) {
					throw new Exception("Unknown error occurred attempting to load plans:", e);
			}
			finally {
				if (stream != null) {
					try {
						stream.close();
					}
					catch(Exception e) {
						return;
					}
				}
			}
		}
		
		/**
		 * Add rolling file appender to the current logging system.
		 */
		private void configLogging() {
			// get config properties
			String loggerLevel = GeneralConfig.loggerLevel;
			String loggerFolder = GeneralConfig.loggerFolder;
			
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
	        
	        // create a rolling file appender for Hordes logger (which is used by the Hordes mod)
	        Appender appender = RollingFileAppender.createAppender(
	        	loggerFolder + "hordes.log",
	        	loggerFolder + "hordes-%d{yyyy-MM-dd-HH_mm_ss}.log",
	        	"true",
	        	"Hordes",
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
	        AppenderRef appenderReference = AppenderRef.createAppenderRef("Hordes", null, null);
	        
	        // create logger config
	        AppenderRef[] refs = new AppenderRef[] {appenderReference};
	        
	        // set the logger name "Hordes" to use the rolling file appender
	        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", loggerLevel, "Hordes", "true", refs, null, config, null );
	        // create a logger for the Labyrinths library to use the same rolling file appender
//	        LoggerConfig labyrinthsLoggerConfig = LoggerConfig.createLogger("false", loggerLevel, "Labyrinths", "true", refs, null, config, null);
	        // create a logger for the Plans library to use the same rolling file appender
	        LoggerConfig plansLoggerConfig = LoggerConfig.createLogger("false", loggerLevel, "Plans", "true", refs, null, config, null);
	        
	        // add appenders to logger config
	        loggerConfig.addAppender(appender, null, null);
//	        labyrinthsLoggerConfig.addAppender(appender, null, null);
	        plansLoggerConfig.addAppender(appender, null, null);
	        
	        // add loggers to base configuratoin
	        ((BaseConfiguration) config).addLogger("Hordes", loggerConfig);
//	        ((BaseConfiguration) config).addLogger("Labyrinths", labyrinthsLoggerConfig);
	        ((BaseConfiguration) config).addLogger("Plans", plansLoggerConfig);
	        
	        // update existing loggers
	        loggerContext.updateLoggers();	
		}

		/**
		 * @return the worldGenerators
		 */
		public static Map<String, WorldGenerator> getWorldGenerators() {
			return worldGenerators;
		}

		/**
		 * @param worldGenerators the worldGenerators to set
		 */
		public static void setWorldGenerators(
				Map<String, WorldGenerator> worldGenerators) {
			Hordes.worldGenerators = worldGenerators;
		}
}

