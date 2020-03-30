package com.someguyssoftware.hordes;

import net.minecraft.nbt.NBTTagCompound;

public interface ISupportsNBT {

	/**
	 * 
	 * @param tag
	 */
	public abstract void readFromNBT(NBTTagCompound tag);

	/**
	 * 
	 * @param tag
	 */
	public abstract void writeToNBT(NBTTagCompound tag);

}