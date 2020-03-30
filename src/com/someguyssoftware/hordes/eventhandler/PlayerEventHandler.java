/**
 * 
 */
package com.someguyssoftware.hordes.eventhandler;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.someguyssoftware.hordes.Hordes;
import com.someguyssoftware.mod.version.VersionChecker;

/**
 * 
 * @author Mark Gottschling on Aug 14, 2015
 *
 */
public class PlayerEventHandler {
	
	@SubscribeEvent
	public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event) {
		if (Hordes.latestVersion == null) {
			return;
		}
		
		if (Hordes.isVersionChecked) {
			return;
		}
		
		boolean isCurrent = VersionChecker.checkVersion(Hordes.latestVersion, Hordes.instance.getClass().getAnnotation(Mod.class).version());
		
		if (!isCurrent) {
			StringBuilder builder = new StringBuilder();
			builder
				.append(EnumChatFormatting.WHITE)
				.append("A new ")
				.append(EnumChatFormatting.GOLD)
				.append("Hordes! ")
				.append(EnumChatFormatting.WHITE)
				.append("version is available: ")
				.append(EnumChatFormatting.GOLD)
				.append(Hordes.latestVersion.getMod());

			event.player.addChatMessage(new ChatComponentText(builder.toString()));
		}
		
		// update the flag to indicate that the version has been checked on this client launch
		Hordes.isVersionChecked = true;
	}
}
