package com.someguyssoftware.hordes.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.tileentity.HordestoneTileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This class represents a 'home' location for a group of mobs within a single Horde.  One Hordestone is dedicated as 'Primary' to represent the 
 * 'home' Hordestone of the entire Horde.
 * @author Mark Gottschling on Jan 17, 2015
 *
 */
public class HordestoneBlock extends BlockContainer {

	/**
	 * 
	 * @param material
	 */
	public HordestoneBlock(Material material) {
		super(material);
		setBlockTextureName(Hordes.modid + ":horde_spawner");
		//setParticleIcon(Hordes.modid + ":horde_spawner");
	}

    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(Hordes.modid + ":horde_spawner");
    }
    
	/**
	 * 
	 * @param material
	 * @param name
	 */
//	public HordeSpawner(Material material, String name) {
//		super(material, name);
//		setBlockTextureName(Hordes.modid + ":horde_spawner");
//		setParticleIcon(Hordes.modid + ":horde_spawner");
//	}
	
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
	@Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new HordestoneTileEntity();
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}
	@Override
    public int getRenderType() {
        return 0;
    }
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
    @Override
    public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
    	Random rand = new Random();
        return 30 + rand.nextInt(30) + rand.nextInt(30);
    }
    
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemById(0);
    }
}
