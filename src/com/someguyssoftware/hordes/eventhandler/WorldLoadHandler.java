/**
 * 
 */
package com.someguyssoftware.hordes.eventhandler;

import java.util.List;

import com.someguyssoftware.hordes.Horde;
import com.someguyssoftware.hordes.HordeMind;
import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.entity.monster.HordeEntity;
import com.someguyssoftware.hordes.world.HordesWorldSavedData;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author Mark Gottschling on Dec 7, 2014
 *
 */
public class WorldLoadHandler {
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {

		// TODO need to check against a json file.  All updates to the hordemind should save to a json file.  if worlddata is null, load from json.
		// only load from server
		if (!event.world.isRemote) {
			// clear any existing world-specific data in Hordes
			if (Hordes.getHordeMind() != null) {
				Hordes.getHordeMind().getHordes().clear();
			}
			Hordes.setHordeWorldSavedData(null);

			// only fire for overworld since hordes only exist there
			if (event.world.provider.dimensionId == 0) {
				// load HordeMinds from World data nbt and construct HordeMinds map
				HordesWorldSavedData hordesWorldSavedData = HordesWorldSavedData.get(event.world);
				Hordes.setHordeWorldSavedData(hordesWorldSavedData);				
				Hordes.logger.debug("Loaded HordesWorldSavedData.");
				
				Hordes.logger.debug("HordeMind # of hordes=" + Hordes.getHordeMind().getHordes().size());
			}
		}
	}
	
	//@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {}
	
	/**
	 * 
	 * @param event
	 */
	@SuppressWarnings("rawtypes")
	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload event) {
		// if a server event
		if(!event.world.isRemote) {
			// get all the entities in the chunk			
			List[] chunkEntityLists = event.getChunk().entityLists;
			
			for (List list : chunkEntityLists) {
				for (Object entity : list) {
					if (entity instanceof HordeEntity) {
						HordeEntity hordeEntity = (HordeEntity) entity;
						
						Hordes.logger.debug("A Horde Entity was found in the unloading chunk.");
						// TODO transfer state of horde entity to virtual entity in the HordeMind
					}
				}
			}
			
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load event) {
		
	}
}
