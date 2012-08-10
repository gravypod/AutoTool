package com.WildAmazing.marinating.AutoTool;

import org.bukkit.Material;
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
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!this.plugin.ACTIVATED.containsKey(p))
			this.plugin.ACTIVATED.put(p, new AP(p, this.plugin.ALWAYSON));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();

		if (p.getItemInHand().getType().toString().toLowerCase().contains("sword"))
			return;
		
		if (e.getClickedBlock() != null)
			try {
				AP a = (AP) this.plugin.ACTIVATED.get(p);
				if (a.getAuto()) {
					if (a.getSubAuto()) {
						if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							a.setSubAuto(false);
							return;
						}
						Inventory i = p.getInventory();
						int index = -99;
						
						if ((this.plugin.isPickaxe(e.getClickedBlock())) && (!this.plugin.getPickaxes().contains(p.getItemInHand().getType()))) {
							for (Material m : this.plugin.getPickaxes()) {
								if (i.contains(m))
									index = i.first(m);
							}
						} else if ((this.plugin.isShovel(e.getClickedBlock())) && (!this.plugin.getShovels().contains(p.getItemInHand().getType()))) {
							for (Material m : this.plugin.getShovels()) {
								if (i.contains(m))
									index = i.first(m);
							}
						} else if ((this.plugin.isAxe(e.getClickedBlock())) && (!this.plugin.getAxes().contains(p.getItemInHand().getType()))) {
								for (Material m : this.plugin.getAxes()) {
									if (i.contains(m)) {
										index = i.first(m);
									}
								}
						}
						

						if (index == -99)
							return;
						
						if (p.getItemInHand().getType() == Material.AIR) {
							
							p.setItemInHand(i.getItem(index));
							i.setItem(index, null);
							
						} else {
							
							ItemStack temp = i.getItem(index);
							i.setItem(index, p.getItemInHand());
							p.setItemInHand(temp);
							
						}
						
					}
					
					if ((p.getItemInHand() != null) && ((this.plugin.getPickaxes().contains(p.getItemInHand().getType())) || (this.plugin.getAxes().contains(p.getItemInHand().getType())) || (this.plugin.getShovels().contains(p.getItemInHand().getType())))) {
						a.setSubAuto(true);
					}
					
					if (e.getAction() == Action.LEFT_CLICK_BLOCK)
						a.setSubAuto(true);
				}
				
			} catch (NullPointerException er) {
				this.plugin.ACTIVATED.put(p, new AP(p, this.plugin.ALWAYSON));
			}
		
	}
}