/**
 * 
 */
package com.someguyssoftware.hordes;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**
 * 
 * @author Mark Gottschling on Jan 16, 2015
 *
 */
public class HordesProperties {

	// mod enabled
	public static boolean enableHordesMod;

	// logging
	public static String loggerLevel;
	public static String loggerFolder;
	
	// horde mind settings
	public static int maxHordes;
	public static int maxConcurrentHordes;
	public static boolean useLimitedGameHordes;
	
	public static int maxHordePopulation;
	public static int minHordePopulation;
	public static int maxHordestonesPerHorde;
	public static int minHordestonesPerHorde;
	public static int maxBuildingsPerHorde;
	public static int minBuildingsPerHorde;
	public static int maxMobsPerHordeGrowth;
	public static int minMobsPerHordeGrowth;

	public static int ticksPerHordeMindUpdate;
	public static int ticksPerHordeGrowth;
	public static int ticksPerHarvest;
	public static int ticksPerBuild;
	
	public static int minChunksPerHorde;
	public static int minHordeGenBlockDistance;  // minumum distance, in blocks, a Horde can spawn from you.
	
	// TODO rate of harvest
	// TODO rate of build
		
    // resource directories
	public static String barbarianDirectory;
	public static String vikingDirectory;
	public static String duergarDirectory;
	
	// ids

	
	
	/**
	 * 
	 */
	public HordesProperties() {}
	
	/**
	 * 
	 * @param file
	 */
	public static void initialize(File file) {
        Configuration config = new Configuration(file);
        config.load();
        
        // application

        // logging
        loggerLevel = config.getString("loggerLevel", "01-mod", "debug", "The logging level. Set to 'off' to disable logging. [trace|debug|info|warn|error|off]");
        loggerFolder = config.getString("loggerFolder", "01-mod", "mods/sgs_hordes/logs/", "The directory where the logs should be stored. This is relative to the Minecraft install path.");
 
        // resources
        config.setCategoryComment("02-resources", "Locations of all the files used by the mod. These are relative to the Minecraft install path.");  
        barbarianDirectory = config.get("02-resources", "barbarianDirectory", "mods/sgs_hordes/plans/barbarians").getString();
        vikingDirectory = config.get("02-resources", "vikingDirectory", "mods/sgs_hordes/plans/vikings").getString();
        duergarDirectory = config.get("02-resources", "duergarDirectory", "mods/sgs_hordes/plans/duergars").getString();
        
        // horde settings
        minChunksPerHorde = config.getInt("minChunksPerHorde", "03-hordes", 256, 1, 32000, "The number of chunks generated for every 1 Horde generated.");
        minHordeGenBlockDistance = config.getInt("minChunksPerHorde", "03-hordes", 80, 10, 192, "The minimum number of blocks (distance) between the original spawn point and a Horde generation.");

        ticksPerHordeMindUpdate = config.getInt("ticksPerHordeUpdate", "03-hordes", 6000, 600, 36000, "The number of ticks before the next Hordes update. During is update, all Hordes are checked for growth, havesting and construction tasks. Default is 6000/5 minutes.");
        maxHordes = config.getInt("maxHordes", "03-hordes", 25, 1, 1000, "The total number of Hordes that can be in the game ever. Ex. if maxHordes=5, then only 5 unique hordes will be generated for the entire duration of the game.  Only used if useLimitedGameHordes=true.");
        maxConcurrentHordes = config.getInt("maxConcurrentHordes", "03-hordes", 5, 1, 20, "The total number of unique Hordes that can be in the game at any given time.");
        useLimitedGameHordes = config.getBoolean("useLimitedGameHordes", "03-hordes", false, "Set to true to enable a limited number of unique Hordes for the duration of the game.");
        
        maxHordePopulation = config.getInt("maxHordePopulation", "03-hordes", 35, 1, 50, "The maximum number of mobs in each Horde. The actual Horde size will be randomized between min and max.");
        minHordePopulation = config.getInt("minHordePopulation", "03-hordes", 15, 1, 50, "The minimum number of mobs in each Horde. The actual Horde size will be randomized between min and max.");
   
        maxMobsPerHordeGrowth = config.getInt("maxMobsPerHordeGrowth", "03-hordes", 5, 1, 20, "The maximum number of mobs a Horde can grow by on its grow cycle.");
        minMobsPerHordeGrowth = config.getInt("minMobsPerHordeGrowth", "03-hordes", 3, 1, 20, "The minimum number of mobs a Horde can grow by on its grow cycle.");
     
        maxHordestonesPerHorde = config.getInt("maxHordestonesPerHorde", "02-hordes", 5, 1, 10, "Hordestones are 'home' to a group of mobs in a Horde.  This is where mobs will spawn and rally to. There can be multiple Hordestones in each Horde.");
        maxBuildingsPerHorde = config.getInt("maxBuildingsPerHorde", "03-hordes", 10, 1, 50, "The total number of buildings a Horde will construct.");
        ticksPerHordeGrowth =  config.getInt("ticksPerHordeGrowth", "03-hordes", 9600, 1200, 36000, "The number of ticks before a Hordestone will attempt to spawn.");
        ticksPerHarvest = config.getInt("ticksPerHarvest",  "03-hordes", 6000, 1200, 36000, "The number of ticks before a 'harvester' mob will 'harvest update'.");
        ticksPerBuild = config.getInt("ticksPerBuild",  "03-hordes", 5000, 1200, 36000, "The number of ticks before a 'builder' mob will 'build update' a structure (or portion of a structure).");
        minHordeGenBlockDistance = config.getInt("minHordeGenBlockDistance", "03-hordes", 160, 50, 320, "The minimum distance from you a Horde can originally generate.c");
       

    	
        // the the default values
        config.save();
	}
}
