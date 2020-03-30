/**
 * 
 */
package com.someguyssoftware.hordes.item;

import java.util.HashMap;

import com.someguyssoftware.hordes.Horde;
import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.world.HordesWorldSavedData;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author Mark Gottschling on Dec 8, 2014
 *
 */
public class WorldSavedDataItem extends Item {

	/**
	 * 
	 */
	public WorldSavedDataItem() {
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		/*
		if (!world.isRemote) {
			Hordes.logger.debug("Using horde world saved data item.");
			Horde mind = null;
			// change a value in the HordeMinds
			if (Hordes.getHordeMinds() == null) {
				Hordes.setHordeMinds(new HashMap<String, Horde>());
			}
			
			// TODO delete this test - this code no longer works as "test" isn't a valid key anymore.
			if (Hordes.getHordeMinds().size() == 0) {
				mind = new Horde();
				Hordes.getHordeMinds().put("test", mind);
			}
			else {
				mind = Hordes.getHordeMinds().get("test");
			}
			
			mind.setCurrentPopulation((short) (mind.getCurrentPopulation() + 1));
			
			Hordes.logger.debug("Population is now at " + mind.getCurrentPopulation());
			
			// call markDirty on the HordeWorldSavedData
			//Hordes.getHordeWorldSavedData().markDirty();
			HordesWorldSavedData.get(world).markDirty();
		}
		return itemStack;	
		*/
		return null;
	}
}
