/**
 * 
 */
package com.someguyssoftware.hordes.horde;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Gottschling on Mar 30, 2020
 *
 */
public class HordeMind {
	// a counter of ticks
	private int ticks;

	// a count of chunks since the last horde spawn
	public int chunksSinceLastHorde;

	// the total count of hordes - active and inactive
	private int gameHordeCount;

	// the hordes TODO turn into a registry
	private Map<String, Horde> hordes;

	/**
	 * 
	 */
	public HordeMind() {
		ticks = 0;
		chunksSinceLastHorde = 0;
		setHordes(new HashMap<String, Horde>());
	}

	/**
	 * 
	 * @param i
	 */
	public void incrementTicks(int i) {
		this.ticks += i;
	}

	//////////// accessors / modifiers //////////////
	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public Map<String, Horde> getHordes() {
		return hordes;
	}

	public void setHordes(Map<String, Horde> hordes) {
		this.hordes = hordes;
	}

	public int getChunksSinceLastHorde() {
		return chunksSinceLastHorde;
	}

	public void setChunksSinceLastHorde(int chunksSinceLastHorde) {
		this.chunksSinceLastHorde = chunksSinceLastHorde;
	}

	public int getGameHordeCount() {
		return gameHordeCount;
	}

	public void setGameHordeCount(int gameHordeCount) {
		this.gameHordeCount = gameHordeCount;
	}
}
