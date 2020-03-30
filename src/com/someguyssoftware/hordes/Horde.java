/**
 * 
 */
package com.someguyssoftware.hordes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

import com.someguyssoftware.hordes.task.BuildTask;
import com.someguyssoftware.hordes.tileentity.HordestoneTileEntity;
import com.someguyssoftware.plans.Plans;
import com.someguyssoftware.plans.rotate.Rotate;

/**
 * This class represent a single Horde community.
 * @author Mark Gottschling on Dec 7, 2014
 *
 */
public class Horde {

	private String id;
	private int hordeType;
	private int maxPopulation;
	private int currentPopulation;
	private int maxBuildings;
	private int currentBuildings;
		
	/*
	 * The home location (x,y,z) of the horde.  The initial spawner will be placed here.  If the spawner is destroyed, this will still remain the home.
	 */
	private Coords home;
	
	private Plans currentPlans;
	private Rotate currentPlansRotation;
	@Deprecated
	private Queue<BuildTask> buildQueue;
	
	private List<Hordestone> hordestones;
	private List<LandPlot> plots;

	private int growthRate;
	
	/**
	 * 
	 */
	public Horde() {
		// set default home
		setHome(new Coords(0,0,0));
		// set default population
		setMaxPopulation((int)50);
		setCurrentPopulation((int)0);
		// set default buildings
		setMaxBuildings((int)10);
		setCurrentBuildings((int)0);
		setCurrentPlansRotation(Rotate.NO_ROTATE);
		// deprecated
		setBuildQueue(new LinkedList<BuildTask>());
		
		setHordestones(new ArrayList<Hordestone>());
		setPlots(new ArrayList<LandPlot>());
	}
	
	/**
	 * 
	 * @param stone
	 * @return
	 */
	public void grow(World world, Hordestone stone) {
		Random random = new Random();

		// for the size of the growth rate
    	for (int i = 0; i < getGrowthRate(); i++) {

    		// randomized the x,z position to spawn
    		int xOffset = random.nextInt(16) - 8;
    		int zOffset = random.nextInt(16) - 8; 
    		int ySpawn = world.getHeightValue(stone.getX() + xOffset, stone.getZ() + zOffset);
    		
    		// TODO entity needs reference the stone
    		// create a mob
        	EntityZombie warrior = new EntityZombie(world);
        	// warrior.setHorde(stone.getHorde());
        	warrior.setLocationAndAngles((double)stone.getX() + 0.5D + xOffset,  (double)ySpawn, (double)stone.getZ() + 0.5D + zOffset, 0.0F, 0.0F);
        	world.spawnEntityInWorld(warrior);
        	Hordes.logger.debug("Spawned zombie");
        	
        	// update Horde with currentPopulation        	
        	setCurrentPopulation(getCurrentPopulation() +1);
        	
        	// check is max pop has been reached
        	if (getCurrentPopulation() >= getMaxPopulation()) {
        		Hordes.logger.debug("Max population has been reached.");
        		return;
        	}
    	}
	}
	
	///// Accessors / Mutators //////
	public int getHordeType() {
		return hordeType;
	}

	public void setHordeType(int hordeType) {
		this.hordeType = hordeType;
	}

	public int getMaxPopulation() {
		return maxPopulation;
	}

	public void setMaxPopulation(int maxPopulation) {
		this.maxPopulation = maxPopulation;
	}

	public int getCurrentPopulation() {
		return currentPopulation;
	}

	public void setCurrentPopulation(int currentPopulation) {
		this.currentPopulation = currentPopulation;
	}

	public int getMaxBuildings() {
		return maxBuildings;
	}

	public void setMaxBuildings(int maxBuildings) {
		this.maxBuildings = maxBuildings;
	}

	public int getCurrentBuildings() {
		return currentBuildings;
	}

	public void setCurrentBuildings(int currentBuildings) {
		this.currentBuildings = currentBuildings;
	}

	public Coords getHome() {
		return home;
	}

	public void setHome(Coords home) {
		this.home = home;
	}

	public synchronized String getId() {
		return id;
	}

	public synchronized void setId(String id) {
		this.id = id;
	}

	public synchronized Plans getCurrentPlans() {
		return currentPlans;
	}

	public synchronized void setCurrentPlans(Plans currentPlans) {
		this.currentPlans = currentPlans;
	}

	public synchronized Rotate getCurrentPlansRotation() {
		return currentPlansRotation;
	}

	public synchronized void setCurrentPlansRotation(Rotate currentPlansRotation) {
		this.currentPlansRotation = currentPlansRotation;
	}

	public synchronized Queue<BuildTask> getBuildQueue() {
		return buildQueue;
	}

	public synchronized void setBuildQueue(Queue<BuildTask> buildQueue) {
		this.buildQueue = buildQueue;
	}

	@Override
	public String toString() {
		return "HordeMind [id=" + id + ", hordeType=" + hordeType
				+ ", maxPopulation=" + maxPopulation + ", currentPopulation="
				+ currentPopulation + ", maxBuildings=" + maxBuildings
				+ ", currentBuildings=" + currentBuildings + ", home=" + home
				+ ", currentPlans=" + currentPlans + ", currentPlansRotation="
				+ currentPlansRotation + ", buildQueue=" + buildQueue + "]";
	}

	public List<Hordestone> getHordestones() {
		return hordestones;
	}

	public void setHordestones(List<Hordestone> hordestones) {
		this.hordestones = hordestones;
	}

	public int getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(int growthRate) {
		this.growthRate = growthRate;
	}

	public List<LandPlot> getPlots() {
		return plots;
	}

	public void setPlots(List<LandPlot> plots) {
		this.plots = plots;
	}
	
}
