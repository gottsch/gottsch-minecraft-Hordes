/**
 * 
 */
package com.someguyssoftware.hordes.block;

import com.someguyssoftware.gottschcore.block.ModBlock;
import com.someguyssoftware.gottschcore.positional.ICoords;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Mark Gottschling on Mar 30, 2020
 *
 */
public class Hordestone extends ModBlock implements IHordesBlock, ITileEntityProvider {

	private ICoords coords;

	/**
	 * 
	 * @param modID
	 * @param name
	 * @param material
	 */
	public Hordestone(String modID, String name, Material material) {
		super(modID, name, material);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	public ICoords getCoords() {
		return coords;
	}

	public void setCoords(ICoords coords) {
		this.coords = coords;
	}

}
