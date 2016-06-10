package me.skylordjay_.skyblock.tpa;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.skylordjay_.skyblock.interfaces.TextFormat;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class TpaManager implements TextFormat {

	private static TpaManager that;

	public Map<String, TPA> players = new HashMap<>();

	public TpaManager() {
		that = this;
	}

	public static TpaManager getTpaManager() {
		return that;
	}

	public void acceptRequest(Player p) {
		if (players.containsKey(p.getName())) {
			TPA tpa = players.get(p.getName());
			long timeLeft = tpa.time / 1000 + 30 - System.currentTimeMillis() / 1000;
			if (timeLeft <= 0) {
				p.sendMessage(prefix + "Your teleport request has expired.");
			} else {
				p.sendMessage(prefix + "You have accepted player, " + tpa.p.getName() + "'s teleport request.");
				tpa.p.sendMessage(prefix + "Player, " + p.getName() + " has accepted your teleport request.");
				tpa.p.teleport(p);
				players.remove(p.getName());
			}
		} else {
			p.sendMessage(prefix + "You don't have any teleport request.");
		}
	}

	public void denyRequest(Player p) {
		if (players.containsKey(p.getName())) {
			TPA tpa = players.get(p.getName());
			tpa.p.sendMessage(prefix + "Your teleport request was denied.");
			p.sendMessage(prefix + "You have denied player, " + tpa.p.getName() + "'s teleport request.");
			players.remove(p.getName());
		}
	}

	public void request(Player p, Player target) {
		if (players.containsKey(target.getName())) {
			TPA tpa = players.get(target.getName());
			if (tpa.p.getName().equals(p.getName())) {
				long timeLeft = tpa.time / 1000 + 30 - System.currentTimeMillis() / 1000;
				if (!(timeLeft <= 0)) {
					p.sendMessage(prefix + "You already sent a teleport request to that person.");
					return;
				}
					players.remove(p.getName());
			}
		}
		players.put(target.getName(), new TPA(p));
		p.sendMessage(prefix + "Teleport request sent to " + target.getName() + ".");
		target.sendMessage(prefix + linkColor + p.getName() + textColor + " has requested a teleport request. Type "
				+ linkColor + "'/island tpa accept" + textColor + "' to accept the request, Type '" + linkColor
				+ "/island tpa deny" + textColor + "' to deny the request. This request will expire in 42 seconds. "
				+ ChatColor.BLUE + "NOTE" + boldColor + ": " + textColor + "This player " + ChatColor.BLUE
				+ "WILL HAVE FULL ACCESS" + textColor + " to your island if you accept.");
	}

}
