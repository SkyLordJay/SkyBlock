package me.skylordjay_.skyblock.tpa;

import org.bukkit.entity.Player;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class TPA {

	public Player p;
	public Long time;

	public TPA(Player p) {
		this.p = p;
		time = System.currentTimeMillis();
	}

}
