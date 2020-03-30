/**
 * 
 */
package com.someguyssoftware.hordes.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * @author Mark Gottschling on Sep 20, 2014
 *
 */
public class GenericTileEntityRenderer extends TileEntitySpecialRenderer {

	private ModelBase model;
	private ResourceLocation texture;
	
	
	public GenericTileEntityRenderer(ModelBase model, String texture) {
		setModel(model);
		setTexture(new ResourceLocation(texture));
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#renderTileEntityAt(net.minecraft.tileentity.TileEntity, double, double, double, float)
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
		
		int i;
			
		if (!tileEntity.hasWorldObj()) {
			i = 0;
		}
		else {
			// get the meta data
            i = tileEntity.getBlockMetadata();
		}
		
		//The PushMatrix tells the renderer to "start" doing something.
		GL11.glPushMatrix();
		//This is setting the initial location.
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		ResourceLocation textures = getTexture(); 
		//binding the textures
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		//This rotation part is very important! Without it, your model will render upside-down and backwards!                   
		GL11.glRotatef(180F, 0F, 0F, 1.0F);
		GL11.glRotatef(180F, 0F, 1.0F, 0F);
		
		// rotate to face player if a direction has been set.  need this for xxxItemRender to work, since a xxxItemRender will not have a Block associated with it.
		 short short1 = 0;
        if (i == 2) // north
        {
            short1 = 180;
        }

        if (i == 3) // sourth
        {
            short1 = 0;
        }

        if (i == 4) // west
        {
            short1 = 90;
        }

        if (i == 5)  // east
        {
            short1 = -90;
        }
		
        // rotate to face player
        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);		
		
		//A reference to your Model file. Again, very important.
		this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		//Tell it to stop rendering for both the PushMatrix's
		GL11.glPopMatrix();
	}

	/**
	 * @return the model
	 */
	public ModelBase getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(ModelBase model) {
		this.model = model;
	}

	/**
	 * @return the texture
	 */
	public ResourceLocation getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(ResourceLocation texture) {
		this.texture = texture;
	}
	
}
