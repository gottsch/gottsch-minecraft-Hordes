/**
 * 
 */
package com.someguyssoftware.hordes.block;

import com.someguyssoftware.hordes.Bounds;
import com.someguyssoftware.hordes.Hordes;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author Mark Gottschling on Dec 9, 2014
 *
 */
public class GenericBlockContainer extends BlockContainer {

	// the class of the tileEntityClass this GravestoneBlock should use.
	private Class<?> tileEntityClass;
	// the icon to use for the particles when block is being hit
	private String particleIcon;
	
	// bounds
	private Bounds widthBounds;
	private Bounds heightBounds;
	private Bounds depthBounds;

	public GenericBlockContainer(Material material) {
		super(material);
		//setCreativeTab(Treasure.treasureTab);
		setHardness(2.0F);
		setWidthBounds(new Bounds(0.0F, 1.0F));
		setHeightBounds(new Bounds(0.0F, 1.0F));
		setDepthBounds(new Bounds(0.0F, 1.0F));;
	}

	public GenericBlockContainer(Material material, String name) {
		super(material);
		//setCreativeTab(Treasure.treasureTab);
		setHardness(2.0F);
		setBlockName(name);
		setWidthBounds(new Bounds(0.0F, 1.0F));
		setHeightBounds(new Bounds(0.0F, 1.0F));
		setDepthBounds(new Bounds(0.0F, 1.0F));;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
	    TileEntity tileEntity = null;
		try {
			tileEntity = (TileEntity) getTileEntityClass().newInstance();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	    return tileEntity;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	/**
	 * Return false because it is smaller than a regular cube, so we need all sides rendered, else you'll see through other blocks.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		// get metadata (ie direction)
		int dir = world.getBlockMetadata(x, y, z);
		
		float widthMax= (float) Math.ceil(getWidthBounds().getEnd());
		float depthMax = (float) Math.ceil(getDepthBounds().getEnd());
		
		if (dir == 2) {
			// north
			setBlockBounds(
				getWidthBounds().getStart(), 
				getHeightBounds().getStart(),
				getDepthBounds().getStart(),
				getWidthBounds().getEnd(),
				getHeightBounds().getEnd(),
				getDepthBounds().getEnd()
			);
		}
		else if (dir == 3) {
			// south invert starting position
			setBlockBounds(
					widthMax- getWidthBounds().getEnd(), 
					getHeightBounds().getStart(),
					depthMax - getDepthBounds().getEnd(),
					widthMax - getWidthBounds().getStart(),
					getHeightBounds().getEnd(),
					depthMax - getDepthBounds().getStart()
				);			
		}
		else if (dir == 5) {
			// east - transpose and invert x,z bounds
			setBlockBounds(
				depthMax - getDepthBounds().getEnd(), 
				getHeightBounds().getStart(),
				widthMax - getWidthBounds().getEnd(),
				depthMax - getDepthBounds().getStart(),
				getHeightBounds().getEnd(),
				widthMax - getWidthBounds().getStart()
			);
		}
		else if (dir == 4) {
			// west - transpose only
			setBlockBounds(
					getDepthBounds().getStart(), 
					getHeightBounds().getStart(),
					getWidthBounds().getStart(),
					getDepthBounds().getEnd(),
					getHeightBounds().getEnd(),
					getWidthBounds().getEnd()
				);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack stack) {
		Hordes.logger.debug("Executing GenericBlockContainer.onBlockPlacedby @ " + x + ", " + y + ", "+ z);
		int playerDirection = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;

		switch (playerDirection)
		{
		case 0: // south
		world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		break;

		case 1: // west
		world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		break;

		case 2: // north
		world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		break;

		case 3: // east
		world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		break;
		}
	}
	
    /**
     * This icon is used as the particles when the chest is destroyed.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(getParticleIcon());
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
	public GenericBlockContainer setTileEntityClass(Class<?> tileEntityClass) {
		this.tileEntityClass = tileEntityClass;
		return this;
	}

	/**
	 * @return the particleIcon
	 */
	public String getParticleIcon() {
		return particleIcon;
	}

	/**
	 * @param particleIcon the particleIcon to set
	 */
	public GenericBlockContainer setParticleIcon(String particleIcon) {
		this.particleIcon = particleIcon;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public Bounds getWidthBounds() {
		return widthBounds;
	}

	/**
	 * 
	 * @param widthBounds
	 * @return The block container
	 */
	public GenericBlockContainer setWidthBounds(Bounds widthBounds) {
		this.widthBounds = widthBounds;
		return this;
	}

	public Bounds getHeightBounds() {
		return heightBounds;
	}

	public GenericBlockContainer setHeightBounds(Bounds heightBounds) {
		this.heightBounds = heightBounds;
		return this;
	}

	public Bounds getDepthBounds() {
		return depthBounds;
	}

	public GenericBlockContainer setDepthBounds(Bounds depthBounds) {
		this.depthBounds = depthBounds;
		return this;
	}
}
