/**
 * 
 */
package com.someguyssoftware.hordes.structure;

import com.someguyssoftware.gottschcore.positional.ICoords;

import net.minecraft.world.gen.structure.template.PlacementSettings;

/**
 * @author Mark Gottschling on Mar 30, 2020
 *
 */
public class Plot {
	private ICoords coords;
	private int width;
	private int depth;
	private PlacementSettings placementSettings;
	private boolean occupied;
	private TemplateHolder templateHolder;
//	private StructureType structureType;
//	private List<HordeResource> resources;
	private int age;
}
