package me.skylordjay_.skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.skylordjay_.skyblock.SkyBlock;
import me.skylordjay_.skyblock.interfaces.TextFormat;
import me.skylordjay_.skyblock.island.Island;
import me.skylordjay_.skyblock.island.IslandManager;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class PlayerMove implements Listener, TextFormat {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().getName().equals(SkyBlock.getSkyBlock().world.getName())) {
			Island i = IslandManager.getIslandManager().getIsland(p);
			if (i != null) {
				if (i.isAt(e.getFrom()) && !i.isAt(e.getTo())) {
					e.setCancelled(true);
					p.sendMessage(textColor + "You may not leave the bounds of your island.");
				}
			}
		}
	}

}
