/**
 * 
 */
package com.someguyssoftware.hordes.client;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.someguyssoftware.hordes.CommonProxy;
import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.client.model.SimpleBlockModel;
import com.someguyssoftware.hordes.client.render.tileentity.GenericTileEntityRenderer;
import com.someguyssoftware.hordes.tileentity.HordestoneTileEntity;

import cpw.mods.fml.client.registry.ClientRegistry;

/**
 * 
 * @author Mark Gottschling on Dec 8, 2014
 *
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		// horde spawner
		ClientRegistry.bindTileEntitySpecialRenderer(HordestoneTileEntity.class,
				new GenericTileEntityRenderer(
						new SimpleBlockModel(), 
						Hordes.modid + ":textures/blocks/horde_spawner.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Hordes.hordestone),
				new GenericItemRender(HordestoneTileEntity.class));		
	}

}
