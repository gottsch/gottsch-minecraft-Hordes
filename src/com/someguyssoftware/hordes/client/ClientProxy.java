/**
 * 
 */
package com.someguyssoftware.hordes.client;

import com.someguyssoftware.hordes.CommonProxy;

/**
 * 
 * @author Mark Gottschling on Aug 16, 2015
 *
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		registerItemRenderers();
	}
	
	/**
	 * 
	 */
	public void registerItemRenderers() {
		// register item renderers
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PlansCreator.tabItem, 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.tabId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.baseBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.baseBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.metaBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.metaBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.groundBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.groundBlockId, "inventory"));
//
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.connectorBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.connectorBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.northConnectorBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.northConnectorBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.eastConnectorBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.eastConnectorBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.southConnectorBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.southConnectorBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.westConnectorBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.westConnectorBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.upConnectorBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.upConnectorBlockId, "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.downConnectorBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.downConnectorBlockId, "inventory"));
//
//
//		
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PlansCreator.draftingPaper, 0, new ModelResourceLocation(PlansCreator.modid + ":" + GeneralConfig.draftingPaperId, "inventory"));
//
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(PlansCreator.wallBlock), 0, new ModelResourceLocation(PlansCreator.modid + ":" + "wall_block", "inventory"));

	}
}
