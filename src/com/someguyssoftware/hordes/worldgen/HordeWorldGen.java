/**
 * 
 */
package com.someguyssoftware.hordes.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import com.someguyssoftware.hordes.Coords;
import com.someguyssoftware.hordes.Horde;
import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.HordesProperties;
import com.someguyssoftware.hordes.Hordestone;
import com.someguyssoftware.hordes.LandPlot;
import com.someguyssoftware.hordes.tileentity.HordestoneTileEntity;
import com.someguyssoftware.hordes.world.HordesWorldSavedData;
import com.someguyssoftware.mod.util.TimeKeyGenerator;

import cpw.mods.fml.common.IWorldGenerator;

/**
 * 
 * @author Mark Gottschling on Dec 9, 2014
 *
 */
public class HordeWorldGen implements IWorldGenerator {
	public static final int CHUNK_RADIUS = 16;	
    
	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.IWorldGenerator#generate(java.util.Random, int, int, net.minecraft.world.World, net.minecraft.world.chunk.IChunkProvider, net.minecraft.world.chunk.IChunkProvider)
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		// test for which overworld dimension
		switch(world.provider.dimensionId) {
		// surface/overworld
		case 0:
			break;
		default:
			return;
		}
		
	    // increment horde count  ??
		Hordes.getHordeMind().setChunksSinceLastHorde(Hordes.getHordeMind().getChunksSinceLastHorde() + 1);
		//Hordes.getHordeMind().incrementTicks(1);

	    // if the number of chunks since last horde spawn hasn't been met
	    if (Hordes.getHordeMind().getChunksSinceLastHorde() < HordesProperties.minChunksPerHorde) {
	    	return;
	    }
	    
	    // if the maxium number of hordes has already been met
	    if (Hordes.getHordeMind().getHordes().size() >= HordesProperties.maxConcurrentHordes) {
	    	return;
	    }
	    
	    // if use limited game hordes is active and the maximum number of hordes has already been met
	    if (HordesProperties.useLimitedGameHordes &&
	    		Hordes.getHordeMind().getGameHordeCount() >= HordesProperties.maxHordes) {
	    	return;
	    }	    	
	    
		// get the x,z world coords, centered in the current chunk
        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;
        
        int xSpawn = xPos + random.nextInt(CHUNK_RADIUS);
        int zSpawn = zPos + random.nextInt(CHUNK_RADIUS);
        
        // the get first surface y (could be leaves, trunk, water, etc)
        int ySpawn = world.getHeightValue(xSpawn, zSpawn);
        
        // ensure that ySpawn is a valid land location
        ySpawn = WorldGenUtil.getValidSurfaceY(world, xSpawn, ySpawn, zSpawn);
        if (ySpawn < 0) {
        	return;
        }
        
        // test against block distance from the spawn point
		ChunkCoordinates coords = world.getSpawnPoint();
		int distance = (int) coords.getDistanceSquared(xSpawn, ySpawn, zSpawn);
		if (distance < HordesProperties.minHordeGenBlockDistance) {
			return;
		}
		
		Hordes.logger.debug("Spawn Point at = " + coords.posX + " " + coords.posY + " " + coords.posZ);
		
		// place the hordestone
		world.setBlock(xSpawn, ySpawn, zSpawn, Hordes.hordestone);
		Hordes.logger.debug("CHEATER! Hordestone @ " + xSpawn  + " " + ySpawn + " " + zSpawn);
		
		// TODO this could change just to a block
		// get the hordestone tile entity
		HordestoneTileEntity hordestoneTe = (HordestoneTileEntity) world.getTileEntity(xSpawn, ySpawn, zSpawn);
		
		// setup the tile entity
		if (hordestoneTe != null && hordestoneTe.hasWorldObj()) {
			// create a new Horde
			Horde horde = new Horde();
			
			// set the horde properties
			String hordeId = TimeKeyGenerator.generateBase32TimeKey();
			horde.setId(hordeId);
			
			// register the home coords of the horde
			horde.setHome(new Coords(xSpawn, ySpawn, zSpawn));
			
			// TODO do we want to use the same random?
			// determine the max population
			int maxPopulation = random.nextInt(Math.abs(HordesProperties.maxHordePopulation - HordesProperties.minHordePopulation))
					+ HordesProperties.minHordePopulation;
			horde.setMaxPopulation(maxPopulation);
			
			// determine the growth rate
			int growthRate = random.nextInt(Math.abs(HordesProperties.maxMobsPerHordeGrowth - HordesProperties.minMobsPerHordeGrowth))
					+ HordesProperties.minMobsPerHordeGrowth;
			horde.setGrowthRate(growthRate);
			
			// add horde mind to the map
			synchronized (HordeWorldGen.class) {
				Hordes.getHordeMind().getHordes().put(hordeId, horde);
				Hordes.logger.debug("Added newly generated Horde to the HordeMind.");
				// update the game horde count
				Hordes.getHordeMind().setGameHordeCount(Hordes.getHordeMind().getGameHordeCount() + 1);
			}
			
			// create a new Hordestone object
			Hordestone hordestone = new Hordestone(hordeId, xSpawn, ySpawn, zSpawn);
			hordestone.setId(TimeKeyGenerator.generateBase32TimeKey());
			// place the chest
			hordestone.placeChest(world);
			
			// add the tile entity to the hordestone list
			horde.getHordestones().add(hordestone);	
			
			// add the hordestone to the land plots list
			horde.getPlots().add(
					new LandPlot(
							hordestone.getId(),
							new Coords(hordestone.getX(), -1, hordestone.getZ()),
							new Coords(hordestone.getX(), -1, hordestone.getZ())
							)
					);
			
			// TODO calculate the NESW boundary points - this is a memory object, it does not get saved in NBT, recalc'ed after every plot add
			// necessary ?  only need to call when adding a new hordestone or building, maybe waypoints?
			
			// execute the initial growth
			horde.grow(world, hordestone);
			
			Hordes.logger.debug("Hordestone spawning initial zombies...");
			
			// update the population
			horde.setCurrentPopulation((short) 5);
			
			// mark it as dirty
			HordesWorldSavedData.get(world).markDirty();			
		}
		else {
			// remove the hordestone block
			world.setBlockToAir(xSpawn,	ySpawn, zSpawn	);
		}
		
		Hordes.logger.debug("Resetting chunk count since last horde to 0.");
		// reset the chunk count
		Hordes.getHordeMind().setChunksSinceLastHorde(0);
	}
	

}
