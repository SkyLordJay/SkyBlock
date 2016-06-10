package me.skylordjay_.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.skylordjay_.skyblock.island.IslandManager;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		IslandManager.getIslandManager().loadPlayer(e.getPlayer());
	}

}
