/**
 * 
 */
package com.someguyssoftware.hordes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;

import com.someguyssoftware.gottschcore.GottschCore;
import com.someguyssoftware.gottschcore.annotation.Credits;
import com.someguyssoftware.gottschcore.command.ShowVersionCommand;
import com.someguyssoftware.gottschcore.config.IConfig;
import com.someguyssoftware.gottschcore.config.ILoggerConfig;
import com.someguyssoftware.gottschcore.mod.AbstractMod;
import com.someguyssoftware.gottschcore.mod.IMod;
import com.someguyssoftware.gottschcore.version.BuildVersion;
import com.someguyssoftware.hordes.horde.HordeMindManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * @author Mark Gottschling on Mar 30, 2020
 *
 */
@Mod(modid = Hordes.MODID, name = Hordes.NAME, version = Hordes.VERSION, dependencies = "required-after:gottschcore@[1.12.1,)", acceptedMinecraftVersions = "[1.12.2]", updateJSON = Hordes.UPDATE_JSON_URL)
@Credits(values = { "Hordes was first developed by Mark 'gottsch' Gottschling on Dec 7, 2014." })
public class Hordes extends AbstractMod {

	// constants
	public static final String MODID = "hordes";
	protected static final String NAME = "Hordes";
	protected static final String VERSION = "0.0.1";

	public static final String UPDATE_JSON_URL = "https://raw.githubusercontent.com/gottsch/gottsch-minecraft-Hordes/master/Hordes2-1.12.2/update.json";

	private static final String VERSION_URL = "";
	private static final BuildVersion MINECRAFT_VERSION = new BuildVersion(1, 12, 2);

	// latest version
	private static BuildVersion latestVersion;

	// logger
	public static Logger logger = LogManager.getLogger(Hordes.NAME);

	@Instance(value = Hordes.MODID)
	public static Hordes instance;

	// TEMP home
	public static SimpleNetworkWrapper simpleNetworkWrapper; // used to transmit your network messages

	/**
	 * 
	 */
	public Hordes() {
	}

	/**
	 * 
	 * @param event
	 */
	@Override
	@EventHandler
	public void preInt(FMLPreInitializationEvent event) {
		super.preInt(event);

		// initialize/reload the config
//		((TreasureConfig) getConfig()).init();

		// register additional events
//		MinecraftForge.EVENT_BUS.register(new LogoutEventHandler(getInstance()));
//		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler(getInstance()));
//		MinecraftForge.EVENT_BUS.register(new WorldEventHandler(getInstance()));
//		MinecraftForge.EVENT_BUS.register(new MimicEventHandler(getInstance()));

		// configure logging
		// create a rolling file appender
		Appender appender = createRollingFileAppender(Hordes.instance, Hordes.NAME + "Appender",
				(ILoggerConfig) getConfig());
		// add appender to mod logger
		addAppenderToLogger(appender, Hordes.NAME, (ILoggerConfig) getConfig());
		// add appender to the GottschCore logger
		addAppenderToLogger(appender, GottschCore.instance.getName(), (ILoggerConfig) getConfig());

	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void serverStarted(FMLServerStartingEvent event) {
		if (!getConfig().isModEnabled())
			return;

		// add a show version command
		event.registerServerCommand(new ShowVersionCommand(this));

		/*
		 * FOR DEBUGGING ONLY register additional commands
		 */
//		event.registerServerCommand(new SpawnChestCommand());
	}

	/**
	 * 
	 */
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// don't process is mod is disabled
		if (!getConfig().isModEnabled())
			return;

		super.init(event);
	}

	/**
	 * 
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (!getConfig().isModEnabled())
			return;

		// perform any post init
		super.postInit(event);
		HordeMindManager.getInstance().postInit(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.someguyssoftware.gottschcore.mod.IMod#getConfig()
	 */
	@Override
	public IConfig getConfig() {
		return null; // HordesConfig.instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.someguyssoftware.gottschcore.mod.IMod#getMinecraftVersion()
	 */
	@Override
	public BuildVersion getMinecraftVersion() {
		return Hordes.MINECRAFT_VERSION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.someguyssoftware.gottschcore.mod.IMod#getVerisionURL()
	 */
	@Override
	public String getVerisionURL() {
		return Hordes.VERSION_URL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.someguyssoftware.gottschcore.mod.IMod#getName()
	 */
	@Override
	public String getName() {
		return Hordes.NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.someguyssoftware.gottschcore.mod.IMod#getId()
	 */
	@Override
	public String getId() {
		return Hordes.MODID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.someguyssoftware.gottschcore.mod.AbstractMod#getInstance()
	 */
	@Override
	public IMod getInstance() {
		return Hordes.instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.someguyssoftware.gottschcore.mod.AbstractMod#getVersion()
	 */
	@Override
	public String getVersion() {
		return Hordes.VERSION;
	}

	@Override
	public BuildVersion getModLatestVersion() {
		return latestVersion;
	}

	@Override
	public void setModLatestVersion(BuildVersion version) {
		Hordes.latestVersion = version;
	}

	@Override
	public String getUpdateURL() {
		return Hordes.UPDATE_JSON_URL;
	}
}
