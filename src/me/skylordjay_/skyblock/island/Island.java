package me.skylordjay_.skyblock.island;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

import me.skylordjay_.skyblock.SkyBlock;

/**
 * 
 * @author SkyLordJay_
 *
 */

@SuppressWarnings("deprecation")
public class Island {

	private String ownerName;
	public Location loc;
	private Location spawn;
	int r = 420;

	public Island(Player owner, Location loc, boolean gen) {
		ownerName = owner.getName();
		this.loc = loc;
		spawn = loc.clone().add(1.5, 3, 5.5);
		spawn.setYaw(-180);
		if (gen) {
			gen();
		}
	}

	ItemStack[] items = { new ItemStack(Material.ICE, 2), new ItemStack(Material.LAVA_BUCKET),
			new ItemStack(Material.CACTUS), new ItemStack(Material.PUMPKIN, 1), new ItemStack(Material.MELON, 2),
			new ItemStack(Material.RED_MUSHROOM), new ItemStack(Material.BROWN_MUSHROOM),
			new ItemStack(Material.BONE, 3), new ItemStack(Material.SUGAR_CANE, 3), new ItemStack(Material.APPLE, 3) };

	private void gen() {
		LocalSession ls = SkyBlock.getSkyBlock().worldedit.getSession(Bukkit.getPlayerExact(ownerName));
		LocalPlayer lp = SkyBlock.getSkyBlock().worldedit.wrapPlayer(Bukkit.getPlayerExact(ownerName));
		try {
			SchematicFormat sf = SchematicFormat
					.getFormat(new File(SkyBlock.getSkyBlock().getDataFolder(), "island.schematic"));
			CuboidClipboard cc = sf.load(new File(SkyBlock.getSkyBlock().getDataFolder(), "island.schematic"));
			cc.paste(ls.createEditSession(lp), BukkitUtil.toVector(loc), true);
		} catch (DataException | IOException e) {
			e.printStackTrace();
		} catch (MaxChangedBlocksException e) {
			e.printStackTrace();
		}
		Location chestLoc = loc.clone().add(1, 3, 0);
		if (chestLoc.getBlock().getType() != Material.CHEST) {
			chestLoc.getBlock().setType(Material.CHEST);
		}
		Chest chest = (Chest) chestLoc.getBlock().getState();
		for (ItemStack item : items) {
			chest.getInventory().addItem(item);
		}
	}

	public boolean isAt(Location location) {
		if (location == null) {
			return false;
		}
		int x = (int) Math.abs(location.getBlockX() - loc.getBlockX());
		int z = (int) Math.abs(location.getBlockZ() - loc.getBlockZ());
		return x < r && z < r;
	}

	public Player getPlayer() {
		return Bukkit.getPlayerExact(ownerName);
	}

	public Location getLocation() {
		return loc;
	}

	public Location getSpawnLocation() {
		return spawn;
	}

}
