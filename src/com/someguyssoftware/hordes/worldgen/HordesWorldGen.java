/**
 * 
 */
package com.someguyssoftware.hordes.worldgen;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.config.GeneralConfig;


/**
 * 
 * @author Mark Gottschling on Aug 17, 2015
 *
 */
public class HordesWorldGen implements IWorldGenerator {
	public static final int CHUNK_RADIUS = 16;	
	private int chunksSinceLastLabyrinth = 0;
	
	/* (non-Javadoc)
	 * @see net.minecraftforge.fml.common.IWorldGenerator#generate(java.util.Random, int, int, net.minecraft.world.World, net.minecraft.world.chunk.IChunkProvider, net.minecraft.world.chunk.IChunkProvider)
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		// test for which overworld dimension
		switch(world.provider.getDimensionId()) {
		// surface/overworld
		case 0:
			break;
		default:
			return;
		}
		
	    // increment last labyrinth chunk count
	    chunksSinceLastLabyrinth++;
	    
		// get the x,z world coords, centered in the current chunk
        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;
        
        int xSpawn = xPos + random.nextInt(CHUNK_RADIUS);
        int zSpawn = zPos + random.nextInt(CHUNK_RADIUS);
        // the get first surface y (could be leaves, trunk, water, etc)
        int ySpawn = world.getChunkFromChunkCoords(chunkX, chunkZ).getHeight(8, 8);
        
        BlockPos pos = new BlockPos(xSpawn, ySpawn, zSpawn);
        
        // TODO will there be multiple generators for the different categories/patterns/biomes ?
        // TODO does biome go here or in generator?
        // get the biome
     	BiomeGenBase biome = world.getBiomeGenForCoords(pos);
     	
     	boolean isGenerated = false;
     	if (chunksSinceLastLabyrinth > GeneralConfig.minChunksPerHorde) {
     	// clear count
			chunksSinceLastLabyrinth = 0;
			isGenerated = Hordes.getWorldGenerators().get("DungeonGen").generate(world, random, pos);
			// TODO may need to expand generate with additional params and then within the DungeonGen set member properties and call IWorldGen.generate()
			if (isGenerated) {
				// TODO do whatever needs to be done when a dungeon is created - ex. record location or something
				Hordes.log.info("Generated horde!");
			}
     	}
     	// TODO save world data
//		if (Treasure.getTreasureGenSavedData() != null) {
//			Treasure.getTreasureGenSavedData().markDirty();
//		}
	}

}
