package me.skylordjay_.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.skylordjay_.skyblock.island.IslandManager;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		IslandManager.getIslandManager().unloadPlayer(e.getPlayer());
	}

}
