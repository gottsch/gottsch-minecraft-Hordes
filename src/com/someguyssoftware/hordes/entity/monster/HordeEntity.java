/**
 * 
 */
package com.someguyssoftware.hordes.entity.monster;

import java.util.Random;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.someguyssoftware.hordes.*;

/**
 * @author Mark Gottschling on Nov 21, 2014
 *
 */
public class HordeEntity extends EntityMob {

	private Hordestone Hordestone;
	// TODO implement IExtendedProperties for the above properties
	
	/**
	 * 
	 * @param world
	 */
	public HordeEntity(World world) {
		super(world);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
//        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
//        this.tasks.addTask(5, new EntityAIMoveTowardsTarget(this, 1.0D));
//        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
//        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
//        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.setSize(0.7F, 1.0F);
        
        // set the weapon/armour
        if (world != null && !this.worldObj.isRemote) {
        	setEquipment();   
        }
	}
	
	/**
	 * 
	 */
	public void setEquipment() {
		/*
		Random random = new Random();
        // 0 = held item
        // 1-4 = armour; 1 = boots, 2 = leggings, 3= chest, 4 = helmet
        int heldItem = random.nextInt(4);
        int wornArmor = random.nextInt(5);
        switch(heldItem) {
        case 0:
        	setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword, 1));
        	break;
        case 1:
        	setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword, 1));
        	break;
        case 2:
        	setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword, 1));
        	break;
        case 3:
        	setCurrentItemOrArmor(0, new ItemStack(Items.diamond_sword, 1));
        	break;
        } 
        
        switch(wornArmor) {
        case 0:
        	// no armor
        	break;        	
        case 1:
        	setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
        	break;
        case 2:
        	setCurrentItemOrArmor(1, new ItemStack(Items.golden_boots, 1));
        	break;
        case 3:
        	setCurrentItemOrArmor(3, new ItemStack(Items.iron_chestplate, 1));
        	break;
        case 4:
        	setCurrentItemOrArmor(3, new ItemStack(Items.diamond_chestplate, 1));
        	break;
        }
        */
	}
	
    /**
     * Get this Entity's EnumCreatureAttribute
     */
	@Override
    public EnumCreatureAttribute getCreatureAttribute(){
        return EnumCreatureAttribute.UNDEFINED;
    }

	/**
	 * 
	 */
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(65.0D); // TODO follows for a really long range if enough are guarding the horde
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D); // TODO random between min and max
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D); // TODO random between min and max
    }
    
    /**
     * @return
     */
    @Override
    public boolean isAIEnabled() {
    	return  true;
    }
}
