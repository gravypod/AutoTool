package com.WildAmazing.marinating.AutoTool;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	AutoTool plugin;

	Commands(AutoTool plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		
		Player player = null;
		
		if (sender instanceof Player) {
			player = (Player) sender;
		} else {
			sender.sendMessage("You must be a player in game to use this!");
			return true;
		}
			
		if (!player.hasPermission("auto.tool"))
			return true;
		
		if (!plugin.ACTIVATED.containsKey(player)) {
			plugin.ACTIVATED.put(player, new AutoPlayer(player, plugin.ALWAYSON));
		}
		
		AutoPlayer a = (AutoPlayer) plugin.ACTIVATED.get(player);
		
		if (args.length == 0) {
			
			if (a.getAuto()) {
				
				player.sendMessage(ChatColor.GREEN + "AutoTool is enabled.");
				
			} else {
				
				player.sendMessage(ChatColor.RED + "AutoTool is disabled.");
				
			}
			
		} else if (args.length == 1) {
			
			if (args[0].equalsIgnoreCase("on")) {
				
				if (a.getAuto()) {
					
					player.sendMessage(ChatColor.YELLOW + "AutoTool is already enabled.");
					
				} else {
					a.setAuto(true);
					a.setSubAuto(true);
					player.sendMessage(ChatColor.GREEN + "AutoTool is now enabled.");
				}
				
			} else if (args[0].equalsIgnoreCase("off")) {
				
				if (!a.getAuto()) {
					
					player.sendMessage(ChatColor.YELLOW + "AutoTool is already disabled.");
					
				} else {
					
					a.setAuto(false);
					a.setSubAuto(false);
					player.sendMessage(ChatColor.RED + "AutoTool is now disabled.");
					
				}
				
			}
		}
		
		return true;

	}
}
