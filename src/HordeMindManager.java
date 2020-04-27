import java.util.HashMap;
import java.util.Map;

import com.someguyssoftware.hordes.horde.HordeMind;

import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * 
 */

/**
 * @author Mark Gottschling on Mar 30, 2020
 *
 */
public class HordeMindManager {

	private static final HordeMindManager INSTANCE = new HordeMindManager();
	private final Map<Integer, HordeMind> hordeMindsByDimesionId = new HashMap<>();

	/**
	 * 
	 */
	private HordeMindManager() {
	}

	/**
	 * Call during FMLPostInitializationEvent
	 * 
	 * @param event
	 */
	public void postInit(FMLPostInitializationEvent event) {
		for (DimensionType dimensionType : DimensionType.values()) {
			hordeMindsByDimesionId.put(new Integer(dimensionType.getId()), new HordeMind());
		}
	}

	/**
	 * 
	 * @return
	 */
	public HordeMindManager getInstance() {
		return INSTANCE;
	}
}
