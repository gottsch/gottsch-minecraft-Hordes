package com.someguyssoftware.hordes.worldgen;

import java.util.Random;

import com.someguyssoftware.hordes.config.HordesConfig;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class HordeWorldGenerator implements IHordesWorldGenerator {

	/**
	 * 
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (HordesConfig.WORLD_GEN.getGeneralProperties().getDimensionsWhiteList()
				.contains(Integer.valueOf(world.provider.getDimensionType().getName().getDimension()))) {
//			generate(world, random, chunkX, chunkZ);
		}

		// TODO HordesMindManager/Registry is needed to manage the different hordes
		// between dimensions
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
