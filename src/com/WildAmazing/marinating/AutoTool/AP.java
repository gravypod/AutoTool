package com.WildAmazing.marinating.AutoTool;

import org.bukkit.entity.Player;

public class AP {
	Player P;
	boolean isFullAuto;
	boolean autoSword;
	boolean auto;

	public AP(Player p, boolean on) {
		this.P = p;
		this.auto = on;
		this.isFullAuto = on;
		this.autoSword = false;
	}

	public void setSubAuto(boolean b) {
		this.auto = b;
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

	public void setAuto(boolean b) {
		this.isFullAuto = b;
	}

	public void setSword(boolean b) {
		this.autoSword = b;
	}
}