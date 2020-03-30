/**
 * 
 */
package com.someguyssoftware.hordes.world;

import java.util.HashMap;
import java.util.Map.Entry;

import com.someguyssoftware.hordes.Coords;
import com.someguyssoftware.hordes.Horde;
import com.someguyssoftware.hordes.HordeMind;
import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.hordes.Hordestone;
import com.someguyssoftware.hordes.tileentity.HordestoneTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

/**
 * @author Mark Gottschling on Dec 7, 2014
 *
 */
public class HordesWorldSavedData extends WorldSavedData {
	private static final String HORDES_WORLD_DATA_KEY = "hordesWorldDataKey";
	private static final String HORDES_LIST_KEY = "hordeList";
	
	// TODO make consts for all the stored value keys
	
	/**
	 * Empty constructor
	 */
	public HordesWorldSavedData() {
		super(HORDES_WORLD_DATA_KEY);
	}
	
	/**
	 * 
	 * @param key
	 */
	public HordesWorldSavedData(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.world.WorldSavedData#readFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		Hordes.logger.debug("Reading saved world data.");
		
		HordeMind mind = Hordes.getHordeMind();
		
		if (mind == null) {
			Hordes.setHordeMind(new HordeMind());
			mind = Hordes.getHordeMind();
		}
		else {
			mind.getHordes().clear();
		}
		
		// get the horde mind data
		mind.setTicks(nbt.getInteger("hordeMindTicks"));
		Hordes.logger.debug("Reading hordeMindTicks=" + nbt.getInteger("hordeMindTicks"));
		
		mind.setChunksSinceLastHorde(nbt.getInteger("hordeMindChunksSinceLastHorde"));
		Hordes.logger.debug("Reading hordeMindChunks=" + nbt.getInteger("hordeMindChunksSinceLastHorde"));
		
		mind.setGameHordeCount(nbt.getInteger("hordeMindGameHordeCount"));
		Hordes.logger.debug("Reading hordeMindGameHordeCount=" + nbt.getInteger("hordeMindGameHordeCount"));
		
		NBTTagList hordeNbtList = nbt.getTagList(HORDES_LIST_KEY, 10);
		Hordes.logger.debug("Reading the hordeNbtList size=" + hordeNbtList.tagCount());
		
		// process each horde in the list
		for (int i = 0; i < hordeNbtList.tagCount(); i++) {
			NBTTagCompound hordeNbt = hordeNbtList.getCompoundTagAt(i);
			// create new HordeMind
			Horde horde = new Horde();
			
			// retrieve data from NBT
			String hordeId = hordeNbt.getString("hordeId");			
			horde.setId(hordeId);
			Hordes.logger.debug("Reading the hordeId=" + horde.getId());
			horde.setMaxPopulation(hordeNbt.getInteger("maxPopulation"));
			horde.setCurrentPopulation(hordeNbt.getInteger("currentPopulation"));
			horde.setGrowthRate(hordeNbt.getInteger("growthRate"));
			
			// retrieve the hordestones
			NBTTagList hordestonesNbt = hordeNbt.getTagList("hordestones", 10);
			//Hordes.logger.debug("Reading the hordestonesNbt size=" + hordestonesNbt.tagCount());
			for (int k = 0; k < hordestonesNbt.tagCount(); k++) {
				NBTTagCompound hordestoneNbt = hordestonesNbt.getCompoundTagAt(k);
				int x = hordestoneNbt.getInteger("x");
				int y = hordestoneNbt.getInteger("y");
				int z = hordestoneNbt.getInteger("z");
				
				//Hordes.logger.debug("Reading the xyz=" + x + " " + y + " " + z);
				// get the horde stone tile entity
				Hordestone stone = null;
				try {
					stone = new Hordestone(hordeId, x, y, z);
					stone.setId(hordestoneNbt.getString("id"));
					stone.getChestCoords().setX(hordestoneNbt.getInteger("chestX"));
					stone.getChestCoords().setY(hordestoneNbt.getInteger("chestY"));
					stone.getChestCoords().setZ(hordestoneNbt.getInteger("chestZ"));
				}
				catch(Exception e) {
					Hordes.logger.error(e);					
				}
				
				// add the te to the horde
				if (horde.getHordestones() != null && stone != null) {
					horde.getHordestones().add(stone);
				}
				else {
					Hordes.logger.debug("Hordestone Tile Entity could not be found. :(");
				}
			}
			
			// add the horde to the hordes list
			mind.getHordes().put(horde.getId(), horde);
			Hordes.logger.debug("Adding Horde to Mind using key=" + horde.getId());
		}
		
		/*
		// read HordeMinds from nbt and construct map in Hordes		
		if (Hordes.getHordeMinds() == null) {
			Hordes.setHordeMinds(new HashMap<String, Horde>());
		}

		// clear any previous data
		Hordes.getHordeMinds().clear();		
		
		NBTTagList mindNbtList = nbt.getTagList(HORDES_MIND_LIST_KEY, 10);
		for (int i = 0; i < mindNbtList.tagCount(); i++) {
			NBTTagCompound mindNbt = mindNbtList.getCompoundTagAt(i);
			// create new HordeMind
			Horde mind = new Horde();
			
			// retrieve data from NBT
			String key = mindNbt.getString("key");
			mind.setHordeType(mindNbt.getShort("hordeType"));
			mind.setMaxPopulation(mindNbt.getShort("maxPopulation"));
			mind.setCurrentPopulation(mindNbt.getShort("currentPopulation"));
			mind.setMaxBuildings(mindNbt.getShort("maxBuildings"));
			mind.setCurrentBuildings(mindNbt.getShort("currentBuildings"));
			
			// create a new home coord object
			Coords home = new Coords();
			home.setX(mindNbt.getInteger("homeX"));
			home.setY(mindNbt.getInteger("homeY"));
			home.setZ(mindNbt.getInteger("homeZ"));
			mind.setHome(home);
			
			// add mind to map
			Hordes.getHordeMinds().put(key, mind);
		}
		*/
	}

	/* (non-Javadoc)
	 * @see net.minecraft.world.WorldSavedData#writeToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {			
		Hordes.logger.debug("Saving world data NBT...");
		
		// write the HordeMind to NBT
		nbt.setInteger("hordeMindTicks", Hordes.getHordeMind().getTicks());
		nbt.setInteger("hordeMindChunksSinceLastHorde", Hordes.getHordeMind().getChunksSinceLastHorde());
		nbt.setInteger("hordeMindGameHordeCount", Hordes.getHordeMind().getGameHordeCount());
		
		//Hordes.logger.debug("Writing hordeMindTicks=" + Hordes.getHordeMind().getTicks());
		//Hordes.logger.debug("Writing hordeMindChunks...=" + Hordes.getHordeMind().getChunksSinceLastHorde());
		//Hordes.logger.debug("Writing hordeMindGameHordeCount=" + Hordes.getHordeMind().getGameHordeCount());
		
		NBTTagList hordeNbtList = new NBTTagList();
		for (Entry<String, Horde> entry : Hordes.getHordeMind().getHordes().entrySet()) {
			NBTTagCompound hordeNbt = new NBTTagCompound();
			Horde horde = entry.getValue();
			
			Hordes.logger.debug("Writing hordeId=" + horde.getId());
			
			// set the key (name) of the mind
			hordeNbt.setString("hordeId", horde.getId());
			hordeNbt.setInteger("maxPopulation", horde.getMaxPopulation());
			hordeNbt.setInteger("currentPopulation", horde.getCurrentPopulation());
			hordeNbt.setInteger("growthRate", horde.getGrowthRate());
			
			// TODO set home
				
			// add every hordestone
			NBTTagList hordestoneNbtList = new NBTTagList();
			for (Hordestone hordestone : horde.getHordestones()) {
				Hordes.logger.debug("Writing hordestone location xyz=" + hordestone.getX() + " " + hordestone.getY() + " " + hordestone.getZ());
				NBTTagCompound hordestoneNbt = new NBTTagCompound();
				hordestoneNbt.setString("id", hordestone.getId());
				hordestoneNbt.setString("hordeId", hordestone.getHordeId());
				hordestoneNbt.setInteger("x", hordestone.getX());
				hordestoneNbt.setInteger("y", hordestone.getY());
				hordestoneNbt.setInteger("z", hordestone.getZ());
				hordestoneNbt.setInteger("chestX", hordestone.getChestCoords().getX());
				hordestoneNbt.setInteger("chestY", hordestone.getChestCoords().getY());
				hordestoneNbt.setInteger("chestZ", hordestone.getChestCoords().getZ())
				;
				// do I need these - they should be saved in the TE
				//hordestoneNbt.setInteger("growthTicks", hordestone.getGrowthTicks());
				//hordestoneNbt.setInteger("growthTicks", hordestone.getBuildTicks());
				//hordestoneNbt.setInteger("growthTicks", hordestone.getHarvestTicks());
				hordestoneNbtList.appendTag(hordestoneNbt);
			}
			// add the stones to the horde
			hordeNbt.setTag("hordestones", hordestoneNbtList);
			
			// add the horde to the horde list
			hordeNbtList.appendTag(hordeNbt);
		}
/*
		// write the HordeMinds map to nbt		
		// have to write as a list, so for each item need to store the key as well
		NBTTagList mindNbtList = new NBTTagList();
		for (Entry<String, Horde> entry : Hordes.getHordeMinds().entrySet()) {
			NBTTagCompound mindNbt = new NBTTagCompound();
			// set the key (name) of the mind
			mindNbt.setString("key", entry.getKey());
			
			// set the properties from the mind
			Horde mind = entry.getValue();
			mindNbt.setShort("hordeType", mind.getHordeType());
			mindNbt.setShort("maxPopulation", mind.getMaxPopulation());
			mindNbt.setShort("currentPopulation", mind.getCurrentPopulation());
			mindNbt.setShort("maxBuildings", mind.getMaxBuildings());
			mindNbt.setShort("currentBuildings", mind.getCurrentBuildings());
			
			// set the home coords
			mindNbt.setInteger("homeX", mind.getHome().getX());
			mindNbt.setInteger("homeY", mind.getHome().getY());
			mindNbt.setInteger("homeZ", mind.getHome().getZ());
			
			// add the mind to the mind list
			mindNbtList.appendTag(mindNbt);
		}
		*/
		// add the mind list to the main nbt
		nbt.setTag(HORDES_LIST_KEY, hordeNbtList);
		
	}

	/**
	 * 
	 * @param world
	 * @return
	 */
	public static HordesWorldSavedData get(World world) {
		HordesWorldSavedData data = (HordesWorldSavedData)world.loadItemData(HordesWorldSavedData.class, HORDES_WORLD_DATA_KEY);
		if (data == null) {
			data = new HordesWorldSavedData();
			world.setItemData(HORDES_WORLD_DATA_KEY, data);
		}
		return data;
	}
}
