package me.skylordjay_.skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.skylordjay_.skyblock.SkyBlock;
import me.skylordjay_.skyblock.island.Island;
import me.skylordjay_.skyblock.island.IslandManager;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class PlayerRespawn implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().getName().equals(SkyBlock.getSkyBlock().world.getName())) {
			if (IslandManager.getIslandManager().hasIsland(p)) {
				Island i = IslandManager.getIslandManager().getIsland(p);
				e.setRespawnLocation(i.getSpawnLocation());
			}
		}
	}

}
