/**
 * 
 */
package com.someguyssoftware.hordes.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**
 * 
 * @author Mark Gottschling on Aug 16, 2015
 *
 */
public class GeneralConfig {

	// logging
	public static String loggerLevel;
	public static String loggerFolder;
	public static String loggerSize;
	
	// resources
	public static String plansDirectory;

	// enablements
	public static Boolean enableHordes;

	// props
	public static int minChunksPerHorde;
	
	// ids
	public static String tabId;


	
	/**
	 * 
	 */
	public GeneralConfig() {
		
	}
	
	/**
	 * 
	 * @param file
	 */
	public static void initialize(File file) {
        Configuration config = new Configuration(file);
        config.load();
        
       // logging
        loggerLevel = config.getString("loggerLevel", "01-mod", "info", "The logging level. Set to 'off' to disable logging. [trace|debug|info|warn|error|off]");
        loggerFolder = config.getString("loggerFolder", "01-mod", "mods/hordes/logs/", "The directory where the logs should be stored. This is relative to the Minecraft install path.");
        loggerSize = config.getString("loggerSize", "01-mod", "1000K", "The size a log file can be before rolling over to a new file.");
        
        // resources
        plansDirectory = config.getString("plansDirectory", "02-resources", "mods/hordes/plans", "");

        // enable hordes
        enableHordes = config.getBoolean("enableHordes", "03-hordes", true, "Enable/disable the generation of hordes.");
        
        // props
        minChunksPerHorde = config.getInt("minChunksPerHorde", "03-hordes", 50, 50, 32000, "");
        
        // ids
        // TODO category comments
        tabId = config.getString("tabsId", "99-ids", "hordes_tab", "");

        ///////////////////////////////////////

        // the the default values
       if(config.hasChanged()) {
    	   config.save();
       }
	}
}
