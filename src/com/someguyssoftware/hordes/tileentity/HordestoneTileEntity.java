/**
 * 
 */
package com.someguyssoftware.hordes.tileentity;

import java.util.Random;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.someguyssoftware.hordes.Horde;
import com.someguyssoftware.hordes.Hordes;

// TODO most the logic code in here is to be moved to the HordeMind
// TODO this class may be decrecated - don't really need it at all.
// TODO may still need a break - or that might be the block - to update the mind
/**
 * This class represents a 'home' location for a group of mobs within a single Horde.  One Hordestone is dedicated as 'Primary' to represent the 
 * 'home' Hordestone of the entire Horde.
 * 
 * @author Mark Gottschling on Nov 11, 2014
 *
 */
public class HordestoneTileEntity extends TileEntity {
	public static final String HAS_SPAWNED_NAME = "hasSpawned";

    private boolean hasSpawned = false;
    
    // NOTE Default Visible Render Chunks  Radius = 8 so only 8 *16 (128) radius blocks away will have any entities
    // loaded in memory to update.  Might need to create virtual entities to continue executing their task
    // while the actual entity is saved off.  the virtual entities would have to call the exact same methods
    // as actual entities to preserve realism
    public static final int DEFAULT_ACTIVATE_RANGE = 128;
    public static final int DEFAULT_SPAWN_DELAY = 6000;
    
    /** Reference to the horde mind */
    private Horde horde;
    private String hordeId;
    
    /** The distance from which a player activates the spawner. */
    private int activatingRangeFromPlayer;    
    private int spawnCounter; // TODO remove; replaced with ticks
    private short minSpawn;
    private short maxSpawn;
    
    @Deprecated
    private int ticks;
    private int growthTicks;
    private int buildTicks;
    private int harvestTicks;
    
    /**
     * Empty constructor that can be constructor by call from MC ie. when loading from world nbt
     */
    public HordestoneTileEntity() {
    	setSpawnCounter(0);
    	setActivatingRangeFromPlayer(DEFAULT_ACTIVATE_RANGE); // TODO will you be able to change this?   	
    	setMinSpawn((short) 2);
    	setMaxSpawn((short) 5);
    }
    
    /**
     * 
     * @param hordeMind
     */
    public HordestoneTileEntity(Horde hordeMind) {
    	setHorde(hordeMind);
    	setSpawnCounter(0);
    	setActivatingRangeFromPlayer(DEFAULT_ACTIVATE_RANGE); // TODO will you be able to change this?
    	setMinSpawn((short) 2);
    	setMaxSpawn((short) 5);
    }
        
	/**
	 * 
	 */
    public void readFromNBT(NBTTagCompound nbt) {
    	
        super.readFromNBT(nbt);
        /*
        // load the setting if already spawned the warriors
        //setHasSpawned(nbt.getBoolean(HAS_SPAWNED_NAME));
        String hordeMindId = nbt.getString("hordeMindKey");
        int spawnCounter = nbt.getInteger("spawnCounter");
        short minSpawn = nbt.getShort("minSpawn");
        short maxSpawn = nbt.getShort("maxSpawn");
        
//        this.setHorde(Hordes.getHordeMinds().get(hordeMindId));
        this.setSpawnCounter(spawnCounter);
        this.setMinSpawn(minSpawn);
        this.setMaxSpawn(maxSpawn);
        */
    }

    /**
     * 
     */
    public void writeToNBT(NBTTagCompound nbt) {
    	
        super.writeToNBT(nbt);
        /*
        // write if spawned already and other stuff
        //nbt.setBoolean(HAS_SPAWNED_NAME, this.isHasSpawned());
        
        nbt.setString("hordeMindKey", getHorde().getId());
        nbt.setInteger("spawnCounter", getSpawnCounter());
        nbt.setShort("minSpawn",getMinSpawn());
        nbt.setShort("maxSpawn", getMaxSpawn());
        */
    }

    /**
     * Updates the spawner code.  This is the main method that is called.  It is called 20x a second
     */
    public void updateEntity() {
    	// TODO may be moved all to the one server tick event? research
    	
    	// update the tick count
//    	this.ticks++;
//    	this.growthTicks++;
//    	this.buildTicks++;
//    	this.harvestTicks++;
    	
    	/*
        // update the spawner
        if (this.isActivated() && !this.getWorld().isRemote) { // <-- this is redundant, just cycle through all players 
        	// check with horde mind if max population has been reached.	
        	if (getHorde().getCurrentPopulation() < getHorde().getMaxPopulation()) {
	        	// check the counter if it is time to spawn yet
	        	if (getSpawnCounter() >= DEFAULT_SPAWN_DELAY) {
		            // spawn new entities
	        		int numberOfMobs = getMaxSpawn();
	        		if (getMaxSpawn() > getMinSpawn()) {
	        			numberOfMobs = this.getWorld().rand.nextInt(getMaxSpawn()-getMinSpawn()) + getMinSpawn();
	        		}
	        		Hordes.logger.debug("Horde Spawner spawning " + numberOfMobs + " zombies...");
	            	spawn(this.xCoord, this.yCoord, this.zCoord, numberOfMobs);
		            
	            	// update current populate of Horde Mind
	            	getHorde().setCurrentPopulation((short) (getHorde().getCurrentPopulation() + numberOfMobs));
		            // reset the spawn counter
		            resetSpawnCounter();
		        }
	        	else {
	        		// increment the spawn counter
	        		this.spawnCounter++;
	        	}
        	}
        }
        */
    	
        super.updateEntity();
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param z
     * @param numberOfMobs
     */
    public void spawn(int x, int y, int z, int numberOfMobs) {
    	Random random = new Random();

 //   	int mobCount = random.nextInt(numberOfMobs) + 1;
 //   	for (int i = 0; i < mobCount; i++) {
    	for (int i = 0; i < numberOfMobs; i++) {
    		// TODO check the maxPopulation hasn't been reached
    		// randomized the x,z position to spawn
    		int xOffset = random.nextInt(16) - 8;
    		int zOffset = random.nextInt(16) - 8; 
    		int ySpawn = getWorld().getHeightValue(x + xOffset, z + zOffset);
    		
    		// TODO entity needs reference to HordeMind
    		// create a mob
        	EntityZombie warrior = new EntityZombie(getWorld());
        	warrior.setLocationAndAngles((double)x + 0.5D + xOffset,  (double)ySpawn, (double)z + 0.5D + zOffset, 0.0F, 0.0F);
        	getWorld().spawnEntityInWorld(warrior);
        	Hordes.logger.debug("Spawned zombie");
        	// TODO update HordeMind with currentPopulation
    	}
    }
    
    /**
     * Returns true if there's a player close enough to this mob spawner to activate it.
     */
    public boolean isActivated()
    {
        return this.getWorld()
        		.getClosestPlayer(
        				(double)this.xCoord + 0.5D,
        				(double)this.yCoord + 0.5D,
        				(double)this.zCoord + 0.5D,
        				(double)this.getActivatingRangeFromPlayer()) != null;
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
    }
    
    /**
     * Receives NBT data from server.  Used for synching NBT data between server and client
     */
    @Override
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
    	// update tile entity with data received from server
    	readFromNBT(packet.func_148857_g());
    	// mark the block for an update
    	//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    /**
     * 
     */
    public void resetSpawnCounter() {
    	this.spawnCounter = 0;
    }
    
    /**
     * Get the world object
     * @return
     */
    public World getWorld() {
        return this.worldObj;
    }

	public boolean isHasSpawned() {
		return hasSpawned;
	}

	public void setHasSpawned(boolean hasSpawned) {
		this.hasSpawned = hasSpawned;
	}

	public synchronized int getActivatingRangeFromPlayer() {
		return activatingRangeFromPlayer;
	}

	public synchronized void setActivatingRangeFromPlayer(
			int activatingRangeFromPlayer) {
		this.activatingRangeFromPlayer = activatingRangeFromPlayer;
	}

	public synchronized int getSpawnCounter() {
		return spawnCounter;
	}

	public synchronized void setSpawnCounter(int spawnCounter) {
		this.spawnCounter = spawnCounter;
	}

	public synchronized Horde getHorde() {
		return horde;
	}

	public synchronized void setHorde(Horde horde) {
		this.horde = horde;
	}

	public synchronized short getMinSpawn() {
		return minSpawn;
	}

	public synchronized void setMinSpawn(short minSpawn) {
		this.minSpawn = minSpawn;
	}

	public synchronized short getMaxSpawn() {
		return maxSpawn;
	}

	public synchronized void setMaxSpawn(short maxSpawn) {
		this.maxSpawn = maxSpawn;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public String getHordeId() {
		return hordeId;
	}

	public void setHordeId(String hordeId) {
		this.hordeId = hordeId;
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
}
