/**
 * 
 */
package com.someguyssoftware.hordes.eventhandler;

import java.util.Map.Entry;

import com.someguyssoftware.hordes.Horde;
import com.someguyssoftware.hordes.HordeMind;
import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.HordesProperties;
import com.someguyssoftware.hordes.Hordestone;
import com.someguyssoftware.hordes.tileentity.HordestoneTileEntity;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * @author Mark Gottschling on Jan 16, 2015
 *
 */
public class TickHandler {

	//@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		// NOTE do not use as there is no access to the World object.
	}
	
	// TODO move all server code to world - just ensure that we only process one of the world's ticks ie. Overworld.
	/**
	 * Using the WorldTickEvent as opposed to the Server tick event because the WorldTickEvent has a natural access to the world object.
	 * @param event
	 */
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		// only process on the server
		// ensure we only process overworld ticks
		if (event.world.provider.dimensionId != 0 || event.world.isRemote) {
			return;
		}

		boolean isMaxPopulationMet = true;
		
		HordeMind mind = Hordes.getHordeMind();
		Boolean resetMindTicks = false;
		// update tick count in the HordeMind
		mind.setTicks(mind.getTicks() + 1);

		//	check all the hordes
		for (Entry<String, Horde> entry : mind.getHordes().entrySet()) {
			Horde horde = entry.getValue();

			// check each hordestone
			for (Hordestone stone : horde.getHordestones()) {
				// updating tick
				// TODO need to stop increment ticks if max levels have been reached
				
				// if the population is less than the max, increment the growth counter
				if (horde.getCurrentPopulation() < horde.getMaxPopulation()) {
					stone.setGrowthTicks(stone.getGrowthTicks()+1);
					isMaxPopulationMet = false;
				}
				stone.setBuildTicks(stone.getBuildTicks() + 1);
				stone.setHarvestTicks(stone.getHarvestTicks() + 1);

				// TODO this needs to move - or add a flag that is set if met and reset for every horde
				if (mind.getTicks() > HordesProperties.ticksPerHordeMindUpdate) {
					Hordes.logger.debug("Mind ticks=" + mind.getTicks());

					Hordes.logger.debug("\tChecking update for Horde Hordestone " + horde.getId() + " @ " + stone.getX() + " " + stone.getY() + " " + stone.getZ());
					Hordes.logger.debug("\tGrow ticks=" + stone.getGrowthTicks());
					Hordes.logger.debug("\tBuild ticks=" + stone.getBuildTicks());
					Hordes.logger.debug("\tHarvest ticks=" + stone.getHarvestTicks());		

					// check for building
					if (horde.getCurrentBuildings() < horde.getMaxBuildings() && stone.getBuildTicks() > HordesProperties.ticksPerBuild) {
						Hordes.logger.debug("\t>Time to Build!");
						// reset build ticks
						stone.setBuildTicks(0);
					}

					// check for harvesting
					if (stone.getHarvestTicks() > HordesProperties.ticksPerHarvest) {

						// TODO check if there are any harvesters still

						// TODO calculate the amount of resources to harvest

						// TODO calculate percentages of each resources to allocate to each category ex. building materials, food

						// TODO calcualte percentage of building materials go to each type - wood, stone, glass, dirt, etc.

						// TODO consume food

						Hordes.logger.debug("\t>Time to Harvest");

						// reset harvest ticks
						stone.setHarvestTicks(0);
					}


					// check for growth
					if (!isMaxPopulationMet && stone.getGrowthTicks() > HordesProperties.ticksPerHordeGrowth) {
						Hordes.logger.debug("\t>Time to Grow!");

						// grow the horde
						horde.grow(event.world, stone);

						// reset the growth ticks
						stone.setGrowthTicks(0);
					}

					// check for expansion to new hordestone
					// TODO check the current number of hordestones against max and the time elapsed.

					// check for job reassignment	 - would have to have a list of every entity connected to this stone.... or every entity in the entire horde.
					
					// TODO need a flag or something that is set to reset the ticks
					resetMindTicks = true;
				}
			}
		}
		// reset update ticks counter
		if (resetMindTicks) {
			Hordes.getHordeMind().setTicks(0);
		}
		
	}
}


