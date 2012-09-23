package com.WildAmazing.marinating.AutoTool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AutoToolPlayerListener implements Listener {
	
	private AutoTool plugin;
	
	public AutoToolPlayerListener(AutoTool p) {
	
		this.plugin = p;
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
	
		Player player = event.getPlayer();
		
		if (player.getName().startsWith("["))
			return;
		
		if (!plugin.ACTIVATED.containsKey(player)) {
			
			plugin.ACTIVATED.put(player, new AutoPlayer(player, plugin.ALWAYSON));
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
	
		Player player = event.getPlayer();
		
		if (player.getItemInHand().getType().name().toLowerCase().contains("sword"))
			return;
		
		if (event.getClickedBlock() != null)
			if (plugin.ACTIVATED.containsKey(player)) {
				
				Material itemInHand = player.getItemInHand().getType();
				AutoPlayer autoPlayer = plugin.ACTIVATED.get(player);
				
				if (autoPlayer.getAuto()) {
					
					if (autoPlayer.getSubAuto()) {
						
						if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							
							autoPlayer.setSubAuto(false);
							return;
							
						}
						
						Inventory playerInventory = player.getInventory();
						Block clickedBlock = event.getClickedBlock();
						
						if ((plugin.isPickaxe(clickedBlock)) && (!plugin.getPickaxes().contains(itemInHand))) {
							
							for (Material m : plugin.getPickaxes()) {
								
								if (playerInventory.contains(m)) {
									setItem(player, playerInventory.first(m));
								}
								
							}
							
							return;
							
						}
						
						if ((plugin.isShovel(clickedBlock)) && (!plugin.getShovels().contains(itemInHand))) {
							
							for (Material m : plugin.getShovels()) {
								
								if (playerInventory.contains(m)) {
									setItem(player, playerInventory.first(m));
								}
								
							}
							
							return;
							
						}
						
						if ((plugin.isAxe(clickedBlock)) && (!plugin.getAxes().contains(itemInHand))) {
							
							for (Material m : plugin.getAxes()) {
								
								if (playerInventory.contains(m)) {
									setItem(player, playerInventory.first(m));
								}
								
							}
							
							return;
							
						}
						
					}
					
					autoPlayer.setSubAuto(true);
					
				}
				
			} else {
				
				plugin.ACTIVATED.put(player, new AutoPlayer(player, plugin.ALWAYSON));
				
			}
		
	}
	
	public void setItem(Player player, int index) {
	
		Inventory playerInventory = player.getInventory();
		
		if (player.getItemInHand().getType() == Material.AIR) {
			
			player.setItemInHand(playerInventory.getItem(index));
			playerInventory.setItem(index, null);
			
		} else {
			
			ItemStack temp = playerInventory.getItem(index);
			playerInventory.setItem(index, player.getItemInHand());
			player.setItemInHand(temp);
			
		}
		
	}
}
