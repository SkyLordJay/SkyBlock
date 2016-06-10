package me.skylordjay_.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skylordjay_.skyblock.SkyBlock;
import me.skylordjay_.skyblock.interfaces.TextFormat;
import me.skylordjay_.skyblock.island.Island;
import me.skylordjay_.skyblock.island.IslandManager;
import me.skylordjay_.skyblock.tpa.TpaManager;

/**
 * 
 * @author SkyLordJay_
 *
 */

public class IslandCommand implements CommandExecutor, TextFormat {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length >= 1) {
				IslandManager im = IslandManager.getIslandManager();
				if (args[0].equalsIgnoreCase("create")) {
					if (!im.hasIsland(p)) {
						p.sendMessage(prefix + textColor + "Creating island...");
						IslandManager.createIsland(p);
					} else {
						p.sendMessage(commandError + "delete");
					}
				}
				if (args[0].equalsIgnoreCase("delete")) {
					if (im.hasIsland(p)) {
						p.sendMessage(prefix + textColor + "Deleting island...");
						IslandManager.deleteIsland(p);
					} else {
						p.sendMessage(commandError + textColor + "create");
					}
				}
				if (args[0].equalsIgnoreCase("home")) {
					if (im.hasIsland(p)) {
						p.sendMessage(prefix + textColor + "Teleporting...");
						im.sendHome(p);
					} else {
						p.sendMessage(commandError + textColor + "create");
					}
				}
				if (args[0].equalsIgnoreCase("tpa")) {
					if (!(args.length >= 2)) {
						p.sendMessage(commandError + "tpa " + boldColor + "<" + textColor + "player, accept, deny" + boldColor + ">");
						return false;
					}
					if (args[1].equalsIgnoreCase("accept")) {
						TpaManager.getTpaManager().acceptRequest(p);
						return false;						
					}
					if (args[1].equalsIgnoreCase("deny")) {
						TpaManager.getTpaManager().denyRequest(p);
						return false;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						p.sendMessage(playerErrorMessage + args[1]);
					} else {
						TpaManager.getTpaManager().request(p, target);
					}
				}
				if (p.hasPermission("skyblock.admin")) {
					if (args[0].equalsIgnoreCase("info")) {
						SkyBlock sb = SkyBlock.getSkyBlock();
						p.sendMessage(prefix + boldColor + ")▔▔▔▔▔▔▔▔▔(" + infoColor + "SkyBlock " + boldColor + ")▔▔▔▔▔▔▔▔(");
						p.sendMessage(prefix + textColor + "Version" + whiteColor + ": " + numberColor + ""
								+ sb.getDescription().getVersion());
						p.sendMessage(prefix + 
								textColor + "SkyBlock World" + whiteColor + ": " + infoColor + "" + sb.world.getName());
						p.sendMessage(prefix + textColor + "Total Islands" + whiteColor + ": " + numberColor + ""
								+ im.getTotalIslands());
						p.sendMessage(prefix + textColor + "Current Islands In Use" + whiteColor + ": " + numberColor + ""
								+ im.getTotalIslandsInUse());
						p.sendMessage(prefix + boldColor + ")▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔(");
					}
					if (args[0].equalsIgnoreCase("tp")) {
						if (!(args.length >= 2)) {
							p.sendMessage(commandError + textColor + "tp " + boldColor + "<" + textColor + "player"
									+ boldColor + ">");
							return false;
						}
						Player target = Bukkit.getPlayer(args[1]);
						if (target == null) {
							p.sendMessage(playerErrorMessage + boldColor + args[1]);
							return false;
						}
						if (im.hasIsland(target)) {
							Island i = im.getIsland(target);
							p.sendMessage(prefix + textColor + "Teleporting...");
							p.teleport(i.getLocation());
						} else {
							p.sendMessage(prefix + textColor + "That player does not own an island.");
						}
					}
				}
			} else {
				if (p.hasPermission("skyblock.admin")) {
					p.sendMessage(opHelpMessage);
				} else {
					p.sendMessage(helpMessage);
				}
			}
		}
		return false;
	}

}
