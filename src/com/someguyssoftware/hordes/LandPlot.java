/**
 * 
 */
package com.someguyssoftware.hordes;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Mark Gottschling on Jan 24, 2015
 *
 */
public class LandPlot implements ISupportsNBT {
	private String id;
	private Coords startCoords;
	private Coords endCoords ;
	
	/**
	 * 
	 */
	public LandPlot() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param start
	 * @param end
	 */
	public LandPlot(String id, Coords start, Coords end) {
		setId(id);
		setStartCoords(start);
		setEndCoords(start);
	}
	
	/* (non-Javadoc)
	 * @see com.someguyssoftware.hordes.ISupportsNBT#readFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		setId(tag.getString("id"));
		setStartCoords(new Coords(tag.getInteger("startX"), -1, tag.getInteger("startZ")));
		setEndCoords(new Coords(tag.getInteger("endX"), -1, tag.getInteger("endZ")));
	}	
	
	/* (non-Javadoc)
	 * @see com.someguyssoftware.hordes.ISupportsNBT#writeToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		// write the id
		tag.setString("id", getId());
		// write the coords
		tag.setInteger("startX", getStartCoords().getX());
		tag.setInteger("startZ", getStartCoords().getZ());
		tag.setInteger("endX", getEndCoords().getX());
		tag.setInteger("endZ", getEndCoords().getZ());		
	}
	
	////////// Accessors / Mutators ////////////////
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Coords getStartCoords() {
		return startCoords;
	}
	public void setStartCoords(Coords startCoords) {
		this.startCoords = startCoords;
	}
	public Coords getEndCoords() {
		return endCoords;
	}
	public void setEndCoords(Coords endCoords) {
		this.endCoords = endCoords;
	}
}
