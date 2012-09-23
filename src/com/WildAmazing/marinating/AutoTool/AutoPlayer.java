package com.WildAmazing.marinating.AutoTool;

import org.bukkit.entity.Player;

public class AutoPlayer {
	
	Player player;
	
	boolean isFullAuto;
	
	boolean autoSword;
	
	boolean auto;
	
	public AutoPlayer(Player player, boolean on) {
	
		this.player = player;
		this.auto = on;
		this.isFullAuto = on;
		this.autoSword = false;
	}
	
	public void setSubAuto(boolean isUsingAuto) {
	
		this.auto = isUsingAuto;
	}
	
	public boolean getSubAuto() {
	
		return this.auto;
	}
	
	public boolean getAuto() {
	
		return this.isFullAuto;
	}
	
	public boolean getSword() {
	
		return this.autoSword;
	}
	
	public void setAuto(boolean isFullAuto) {
	
		this.isFullAuto = isFullAuto;
	}
	
	public void setSword(boolean autoSword) {
	
		this.autoSword = autoSword;
	}
	
}
