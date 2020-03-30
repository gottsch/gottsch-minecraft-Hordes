package com.someguyssoftware.hordes.client;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

/**
 * 
 * @author Mark Gottschling on Aug 29, 2014
 *
 */
public class GenericItemRender implements IItemRenderer {

	private Class<?> tileEntityClass;
	
	/**
	 * 
	 */
	public GenericItemRender(Class<?> tileEntity) {
		setTileEntityClass(tileEntity);
	}
	
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	/**
	 * Renders the TileEntity
	 */
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		try {
			// create a new instance of the class the inherits from TileEntity
			TileEntity tileEntity = (TileEntity) getTileEntityClass().newInstance();
			// render the tile entity
			TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity, 0D, 0D, 0D, 0F);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @return the tileEntityClass
	 */
	public Class<?> getTileEntityClass() {
		return tileEntityClass;
	}


	/**
	 * @param tileEntityClass the tileEntityClass to set
	 */
	public void setTileEntityClass(Class<?> tileEntityClass) {
		this.tileEntityClass = tileEntityClass;
	}

}
