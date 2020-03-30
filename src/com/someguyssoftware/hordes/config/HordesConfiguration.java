/**
 * 
 */
package com.someguyssoftware.hordes.config;

import java.io.File;

// TODO add IModConfiguration to ModUtils library and create an Abstract class which implements initialize()
/**
 * 
 * @author Mark Gottschling on Aug 16, 2015
 *
 */
public class HordesConfiguration {
	private static final String HORDES_CONFIG_DIR = "hordes";
	
	public static String configPath;
	
	/*
	 * 
	 */
	public static void initialize(File configDir) {
		// build the path to the minecraft config directory
		configPath = (new StringBuilder()).append(configDir).append("/").append(HORDES_CONFIG_DIR).append("/").toString();
		
		// create the file to the general treasure config file
		File generalConfigFile = new File((new StringBuilder()).append(configPath).append("general.cfg").toString());
		// initialize the General config
		GeneralConfig.initialize(generalConfigFile);
	}
}
