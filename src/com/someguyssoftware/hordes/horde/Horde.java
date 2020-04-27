/**
 * 
 */
package com.someguyssoftware.hordes.horde;

import java.util.ArrayList;
import java.util.List;

import com.someguyssoftware.gottschcore.positional.Coords;
import com.someguyssoftware.gottschcore.positional.ICoords;
import com.someguyssoftware.hordes.block.Hordestone;
import com.someguyssoftware.hordes.structure.Plot;

/**
 * @author Mark Gottschling on Mar 30, 2020
 *
 */
public class Horde {
	private String id;
//	private int hordeType; TODO change to enum
	private int maxPopulation;
	private int currentPopulation;
	private int maxBuildings;
	private int currentBuildings;

	/*
	 * The home location (x,y,z) of the horde. The initial spawner will be placed
	 * here. If the spawner is destroyed, this will still remain the home.
	 */
	private ICoords home;

	// TODO should there only be 1 hordestone per horde, or per building
	private List<Hordestone> hordestones;
	// TODO probably should be a containing object of all the plots. ie AABB,
	// list<plot>, active<plot>, unused<plot>, dead<plot>?
	private List<Plot> plots;

	// NOTE will be in seconds or milliseconds
	private int growthRate;

	/**
	 * 
	 */
	public Horde() {
		// set default home
		setHome(new Coords(0, 0, 0)); // TODO change - leave null or EMPTY_COORDS
		// set default population
		setMaxPopulation(50);
		setCurrentPopulation(0);
		// set default buildings
		setMaxBuildings(10);
		setCurrentBuildings(0);

		setHordestones(new ArrayList<Hordestone>());
		setPlots(new ArrayList<Plot>());
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

	public ICoords getHome() {
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

	public List<Plot> getPlots() {
		return plots;
	}

	public void setPlots(List<Plot> plots) {
		this.plots = plots;
	}

}
