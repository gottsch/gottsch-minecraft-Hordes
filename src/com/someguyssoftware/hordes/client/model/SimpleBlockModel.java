package com.someguyssoftware.hordes.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * 
 * @author Mark Gottschling on Nov 11, 2014
 *
 */
public class SimpleBlockModel extends ModelBase
{
  //fields
  ModelRenderer Shape1;
  
  /**
   * 
   */
  public SimpleBlockModel() {
	textureWidth = 64;
	textureHeight = 64;
	
    Shape1 = new ModelRenderer(this, 0, 0);
    Shape1.addBox(0F, 0F, 0F, 16, 16, 16);
    Shape1.setRotationPoint(-8F, 8F, -8F);
    Shape1.setTextureSize(64, 64);
    Shape1.mirror = true;
    setRotation(Shape1, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
