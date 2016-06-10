package me.skylordjay_.skyblock.island;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.skylordjay_.skyblock.SkyBlock;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class IslandManager {

	private static IslandManager islandManager;

	private static Map<UUID, Island> islands = new HashMap<>();

	private static double x = -50000, z = -50000, offset = 1000;
	private static Location nextLocation = new Location(SkyBlock.getSkyBlock().world, x, 72, z);

	public Block block1;
	public Block block2;

	File file;
	FileConfiguration config;

	private static File islandFile;
	private static FileConfiguration islandConfig;

	private static int totalIslands = 0;

	public IslandManager() {
		islandManager = this;
		registerFile();
		registerIslandFile();
		loadStats();
		loadNextLocation();
		for (Player ps : Bukkit.getOnlinePlayers()) {
			loadPlayer(ps);
		}
	}

	private void loadStats() {
		totalIslands = config.getInt("total-islands");
	}

	public void onDisable() {
		config.set("total-islands", totalIslands);
		ConfigurationSection s = config.getConfigurationSection("nextLocation");
		s.set("x", nextLocation.getX());
		s.set("z", nextLocation.getZ());
		saveFile();
		for (Player ps : Bukkit.getOnlinePlayers()) {
			unloadPlayer(ps);
		}
	}

	public static void createIsland(Player p) {
		totalIslands++;
		double x, z;
		x = nextLocation.getX() + offset;
		z = nextLocation.getZ();
		if (x > Math.abs(IslandManager.x)) {
			z += offset;
			nextLocation.setX(IslandManager.x);
			x = nextLocation.getX() + offset;
			nextLocation.setZ(z);
		}
		if (z > Math.abs(IslandManager.z)) {
			// its full. ?
		}
		Location loc = new Location(SkyBlock.getSkyBlock().world, x, 72, z);
		Location playerLoc = loc.clone().add(1.5, 3, 5.5);
		playerLoc.setYaw(-180);
		p.teleport(playerLoc);
		Island il = new Island(p, loc, true);
		islands.put(p.getUniqueId(), il);
		nextLocation = il.getLocation();
		ConfigurationSection s = islandConfig.createSection(p.getUniqueId().toString());
		s.set("name", p.getName());
		s.set("x", il.getLocation().getX());
		s.set("z", il.getLocation().getZ());
		saveIslands();
	}

	public static void deleteIsland(Player p) {
		p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
		islands.remove(p.getUniqueId());
		islandConfig.set(p.getUniqueId().toString(), null);
		saveIslands();
	}

	public static IslandManager getIslandManager() {
		return islandManager;
	}

	private void registerFile() {
		file = new File(SkyBlock.getSkyBlock().getDataFolder(), "IslandManager.yml");
		config = YamlConfiguration.loadConfiguration(file);
		saveFile();
	}

	private void saveFile() {
		try {
			config.save(file);
		} catch (IOException e) {

		}
	}

	private void registerIslandFile() {
		islandFile = new File(SkyBlock.getSkyBlock().getDataFolder(), "islands.yml");
		islandConfig = YamlConfiguration.loadConfiguration(islandFile);
		saveIslands();
	}

	private static void saveIslands() {
		try {
			islandConfig.save(islandFile);
		} catch (IOException e) {

		}
	}

	private void loadNextLocation() {
		if (config.contains("nextLocation")) {
			ConfigurationSection s = config.getConfigurationSection("nextLocation");
			double x = s.getDouble("x");
			double z = s.getDouble("z");
			nextLocation = new Location(SkyBlock.getSkyBlock().world, x, 72, z);
		} else {
			ConfigurationSection s = config.createSection("nextLocation");
			s.set("x", nextLocation.getX());
			s.set("z", nextLocation.getZ());
			saveFile();
		}
	}

	public void loadPlayer(Player p) {
		if (islandConfig.contains(p.getUniqueId().toString())) {
			ConfigurationSection s = islandConfig.getConfigurationSection(p.getUniqueId().toString());
			double x = s.getDouble("x");
			double z = s.getDouble("z");
			Island i = new Island(p, new Location(SkyBlock.getSkyBlock().world, x, 72, z), false);
			islands.put(p.getUniqueId(), i);
		}
	}

	public void unloadPlayer(Player p) {
		if (hasIsland(p)) {
			islands.remove(p.getUniqueId());
		}
	}

	public Island getIsland(Player p) {
		if (hasIsland(p)) {
			return islands.get(p.getUniqueId());
		}
		return null;
	}

	public void sendHome(Player p) {
		p.teleport(getIsland(p).getSpawnLocation());
	}

	public boolean hasIsland(Player p) {
		return islands.containsKey(p.getUniqueId());
	}

	public int getTotalIslands() {
		return totalIslands;
	}

	public int getTotalIslandsInUse() {
		return islandConfig.getKeys(false).size();
	}

}
