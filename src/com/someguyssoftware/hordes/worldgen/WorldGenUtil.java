/**
 * 
 */
package com.someguyssoftware.hordes.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/**
 * @author Mark Gottschling on Jan 23, 2015
 *
 */
public class WorldGenUtil {

	/**
	 * This method get a VALID Y pos.  This takes into account the the surface position can not be lava, a log, water, leaves or foliage.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static int getValidSurfaceY(World world, int x, int y, int z) {
		
		boolean isGoodBlock = false;
		
		// check if the block below the chest is viable
		while (!isGoodBlock) {
			Block block = world.getBlock(x, y -1, z);
			
			if (block == Blocks.lava || block == Blocks.log || block == Blocks.log2) {
				// return without generating.
				return -1;
			}

			if (block != null && (block.isAir(world, x, y, z) || block.isBurning(world, x, y, z) || block == Blocks.water ||
					block.isLeaves(world, x, y, z) || block.isFoliage(world, x, y, z)) || block.getMaterial().isReplaceable()) {
				y--;
			}
			else {
				isGoodBlock = true;
			}
			
			if (y < 0) {
				return -1;
			}
		}		
		return y;
	}
}
