/**
 * 
 */
package com.someguyssoftware.hordes;

import java.util.Random;

import com.someguyssoftware.hordes.worldgen.WorldGenUtil;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/**
 * @author Mark Gottschling on Jan 20, 2015
 *
 */
public class Hordestone {
	// reference to the horde the stone belongs to
	private String hordeId;
	
	private String id;
	private int x;
	private int y;
	private int z;
	
    private int growthTicks;
    private int buildTicks;
    private int harvestTicks;
    
    private Coords chestCoords;
    
	/**
	 * 
	 */
	public Hordestone() {}
	
	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param z
	 */
	public Hordestone(String id, int x, int y, int z) {
		setHordeId(id);
		setX(x);
		setY(y);
		setZ(z);
		setChestCoords(new Coords());
	}
	
	/**
	 * 
	 * @param world
	 * @return
	 */
	public boolean placeChest(World world) {
		Random random = new Random();

		int xOffset = random.nextInt(3) -1;
		int zOffset = random.nextInt(3) -1;
		
		int xSpawn = getX() + xOffset;
		int zSpawn = getZ() + zOffset;
		int ySpawn = -1;
		int i = 0;
		
		while (ySpawn == -1) {
			WorldGenUtil.getValidSurfaceY(world, xSpawn,getY(), zSpawn);
			if (i > 10) {
				return false;
			}
			i++;
		}
		
		// TODO determine which way the chest should face
		
		// update the world with the chest
		world.setBlock(xSpawn, ySpawn, zSpawn, Blocks.chest, 0, 2);
		// update the blocks metadata (have to do this because chests don't seems to always get it with setBlock
		world.setBlockMetadataWithNotify(xSpawn, ySpawn, zSpawn, 0, 3);
		
		chestCoords = new Coords(xSpawn, ySpawn, zSpawn);
		return true;
	}
	
	public String getHordeId() {
		return hordeId;
	}
	public void setHordeId(String hordeId) {
		this.hordeId = hordeId;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}

	public int getGrowthTicks() {
		return growthTicks;
	}

	public void setGrowthTicks(int growthTicks) {
		this.growthTicks = growthTicks;
	}

	public int getBuildTicks() {
		return buildTicks;
	}

	public void setBuildTicks(int buildTicks) {
		this.buildTicks = buildTicks;
	}

	public int getHarvestTicks() {
		return harvestTicks;
	}

	public void setHarvestTicks(int harvestTicks) {
		this.harvestTicks = harvestTicks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Coords getChestCoords() {
		return chestCoords;
	}

	public void setChestCoords(Coords chestCoords) {
		this.chestCoords = chestCoords;
	}
}
