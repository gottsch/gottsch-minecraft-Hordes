/**
 * 
 */
package com.someguyssoftware.hordes.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.someguyssoftware.gottschcore.config.IConfig;
import com.someguyssoftware.gottschcore.config.ILoggerConfig;
import com.someguyssoftware.hordes.Hordes;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Ignore;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Mark Gottschling on Mar 30, 2020
 *
 */
@Config(modid = Hordes.MODID, name = Hordes.MODID + "/" + Hordes.MODID + "-"
		+ HordesConfig.CONFIG_VERSION, type = Type.INSTANCE)
public class HordesConfig implements IConfig, ILoggerConfig {
	@Ignore
	public static final String CONFIG_VERSION = "c1.0";

	@Name("01 mod")
	@Comment({ "General mod properties." })
	public static final ModConfig MOD = new ModConfig();

	@Name("02 logging")
	@Comment({ "Logging properties" })
	public static final LoggerConfig LOGGING = new LoggerConfig();

	@Name("09 world generation")
	@Comment("World generation properties")
	public static final WorldGen WORLD_GEN = new WorldGen();

	@Ignore
	public static HordesConfig instance = new HordesConfig();

	/**
	 * 
	 */
	public HordesConfig() {
	}

	/**
	 * 
	 */
	public static void init() {
	}

	/**
	 * 
	 * @author Mark Gottschling on Mar 30, 2020
	 *
	 */
	@net.minecraftforge.fml.common.Mod.EventBusSubscriber
	public static class EventHandler {
		/**
		 * Inject the new values and save to the config file when the config has been
		 * changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Hordes.MODID)) {
				ConfigManager.sync(Hordes.MODID, Config.Type.INSTANCE);
				HordesConfig.init();
			}
		}
	}

	/*
	 * 
	 */
	public static class WorldGen {

		@Name("01 general")
		public GeneralProperties generalProperties = new GeneralProperties();

		public class GeneralProperties {

			@Name("00. Dimension white list:")
			@Comment({
					"Allowed Dimensions for generation.\nHordes was designed for \"normal\" overworld-type dimensions.\nThis setting does not use any wildcards (*). You must explicitly set the dimensions that are allowed." })
			public Integer[] rawDimensionsWhiteList = new Integer[] { 0 };
			@Ignore
			public List<Integer> dimensionsWhiteList = new ArrayList<>(3);

			/**
			 * 
			 */
			public void init() {
				HordesConfig.WORLD_GEN.getGeneralProperties().dimensionsWhiteList = Arrays
						.asList(HordesConfig.WORLD_GEN.getGeneralProperties().rawDimensionsWhiteList);
			}

			public List<Integer> getDimensionsWhiteList() {
				return dimensionsWhiteList;
			}

			public void setDimensionsWhiteList(List<Integer> dimensionsWhiteList) {
				this.dimensionsWhiteList = dimensionsWhiteList;
			}
		}

		public GeneralProperties getGeneralProperties() {
			return generalProperties;
		}

		public void setGeneralProperties(GeneralProperties generalProperties) {
			this.generalProperties = generalProperties;
		}
	}

	@Override
	public boolean isModEnabled() {
		return MOD.enabled;
	}

	@Override
	public void setModEnabled(boolean modEnabled) {
		MOD.enabled = modEnabled;
	}

	@Override
	public String getModsFolder() {
		return MOD.folder;
	}

	@Override
	public void setModsFolder(String modsFolder) {
		MOD.folder = modsFolder;
	}

	/*
	 * ILoggerConfig inherited methods
	 */

	@Override
	public String getLoggerLevel() {
		return LOGGING.level;
	}

	@Override
	public String getLoggerFolder() {
		return LOGGING.folder;
	}

	@Override
	public String getLoggerSize() {
		return LOGGING.size;
	}

	@Override
	public String getLoggerFilename() {
		return LOGGING.filename;
	}

	@Override
	public String getConfigFolder() {
		return MOD.configFolder;
	}

	@Override
	public void setConfigFolder(String configFolder) {
		MOD.configFolder = configFolder;
	}

	@Override
	public boolean isEnableVersionChecker() {
		return MOD.enableVersionChecker;
	}

	@Override
	public String getLatestVersion() {
		return MOD.latestVersion;
	}

	@Override
	public void setEnableVersionChecker(boolean enableVersionChecker) {
		MOD.enableVersionChecker = enableVersionChecker;
	}

	@Override
	public void setLatestVersion(String latestVersion) {
		MOD.latestVersion = latestVersion;

	}

	@Override
	public boolean isLatestVersionReminder() {
		return MOD.latestVersionReminder;
	}

	@Override
	public void setLatestVersionReminder(boolean latestVersionReminder) {
		MOD.latestVersionReminder = latestVersionReminder;
	}

	@Override
	public void setForgeConfiguration(Configuration forgeConfiguration) {
		// NOTE do nothing
	}

	@Override
	public Configuration getForgeConfiguration() {
		return null;
	}

	@Deprecated
	@Override
	public void setProperty(String category, String key, boolean value) {

	}

	@Deprecated
	@Override
	public void setProperty(String category, String key, String value) {

	}

	@Deprecated
	@Override
	public void setProperty(Property property, String value) {

	}
}
