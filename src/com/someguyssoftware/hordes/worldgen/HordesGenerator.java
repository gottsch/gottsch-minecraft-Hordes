package com.someguyssoftware.hordes.worldgen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.plans.IPlan;
import com.someguyssoftware.plans.IRealizedPlan;
import com.someguyssoftware.plans.PlansManager;
import com.someguyssoftware.plans.builder.PlansBuilder;

/**
 * 
 * @author Mark Gottschling on Aug 17, 2015
 *
 */
public class HordesGenerator extends WorldGenerator {

	public PlansBuilder plansBuilder;
	
	/**
	 * 
	 */
	public HordesGenerator() {
		setPlansBuilder(new PlansBuilder());
	}
	
	/**
	 * 
	 * @param labyrinths
	 */
	public HordesGenerator(PlansBuilder builder) {
		setPlansBuilder(builder);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean generate(World worldIn, Random random, BlockPos blockPos) {
		boolean isGenerated = false;
		// TODO determine what horde type, size, etc, etc to build and build it. 
		
		// TODO fetch all groups for criteria
		List<String> groupNames = Hordes.hordesManager.getGroupNameByCategory("dwarf");
		String groupName = groupNames.get(random.nextInt(groupNames.size()));
		
		// TODO fetch all plans for selected/randomized groupName
		List<IPlan> plans = PlansManager.getInstance().getByGroup(groupName);
		
		// TODO fetch the build config or pass the pattern and size params

		// build the horde
//		IRealizedPlan realizedPlan = getPlansBuilder().build(worldIn, random, new Coords(blockPos), plans);
		IRealizedPlan realizedPlan = null;
		
		if (realizedPlan != null) {
			isGenerated = true;
			Hordes.log.info("Generated horde @ " + blockPos);
		}
		
		return isGenerated;
	}

	/**
	 * @return the plansBuilder
	 */
	public PlansBuilder getPlansBuilder() {
		return plansBuilder;
	}

	/**
	 * @param plansBuilder the plansBuilder to set
	 */
	public void setPlansBuilder(PlansBuilder plansBuilder) {
		this.plansBuilder = plansBuilder;
	}
	

}
