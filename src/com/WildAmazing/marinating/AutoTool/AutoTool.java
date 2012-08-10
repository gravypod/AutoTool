package com.WildAmazing.marinating.AutoTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoTool extends JavaPlugin {
	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	public static Logger log = Logger.getLogger("Minecraft");
	static String mainDirectory = "plugins/AutoTool";
	public HashMap<Player, AP> ACTIVATED = new HashMap<Player, AP>();
	public static File CONFIG = new File(mainDirectory + File.separator + "AutoTool.txt");
	private ArrayList<Material> pickaxes;
	private ArrayList<Material> shovels;
	private ArrayList<Material> axes;
	private ArrayList<Material> PICKAXE;
	private ArrayList<Material> SHOVEL;
	private ArrayList<Material> AXE;
	static Properties prop = new Properties();
	public boolean ALWAYSON = false;

	public void onEnable() {
		
		
		long start = System.currentTimeMillis();
		this.PICKAXE = new ArrayList<Material>();
		this.SHOVEL = new ArrayList<Material>();
		this.AXE = new ArrayList<Material>();
		this.pickaxes = new ArrayList<Material>();
		this.shovels = new ArrayList<Material>();
		this.axes = new ArrayList<Material>();
		this.pickaxes.add(Material.WOOD_PICKAXE);
		this.pickaxes.add(Material.STONE_PICKAXE);
		this.pickaxes.add(Material.IRON_PICKAXE);
		this.pickaxes.add(Material.GOLD_PICKAXE);
		this.pickaxes.add(Material.DIAMOND_PICKAXE);
		this.shovels.add(Material.WOOD_SPADE);
		this.shovels.add(Material.STONE_SPADE);
		this.shovels.add(Material.IRON_SPADE);
		this.shovels.add(Material.GOLD_SPADE);
		this.shovels.add(Material.DIAMOND_SPADE);
		this.axes.add(Material.WOOD_AXE);
		this.axes.add(Material.STONE_AXE);
		this.axes.add(Material.IRON_AXE);
		this.axes.add(Material.GOLD_AXE);
		this.axes.add(Material.DIAMOND_AXE);
		
		getCommand("autotool").setExecutor(new CommandHandler(this));
		getServer().getPluginManager().registerEvents(new AutoToolPlayerListener(this), this);
		
		if (!CONFIG.exists()) {
			
			new File(mainDirectory).mkdir();
			
			try {
				
				CONFIG.createNewFile();
				FileOutputStream out = new FileOutputStream(CONFIG);
				prop.put("Pickaxe items", "STONE IRON_ORE GOLD_ORE REDSTONE_ORE COAL_ORE DIAMOND_ORE LAPIS_ORE COBBLESTONE MOSSY_COBBLESTONE NETHERRACK OBSIDIAN IRON_BLOCK GOLD_BLOCK DIAMOND_BLOCK GLOWSTONE LAPIS_BLOCK SANDSTONE BRICK STEP BRICK_STAIRS COBBLESTONE_STAIRS ");

				prop.put("Shovel items", "DIRT GRASS GRAVEL SAND SNOW_BLOCK CLAY");
				prop.put("Axe items", "LOG WOOD WOOD_DOOR");
				prop.put("On by default?", "false");
				prop.store(out, "Make sure all item names are in caps and separated by spaces.");
				out.flush();
				out.close();
				log.info("[AutoTool] New file created.");
				
			} catch (IOException ex) {
				
				log.warning("[AutoTool] File creation error: " + ex.getMessage());
			}
		
		} else {
			
			log.info("[AutoTool] Detected existing config file and loading.");
			
		}
		
		
		loadProcedure();
		
		
		
		
		for (Player player : getServer().getOnlinePlayers()) {
			
			this.ACTIVATED.put(player, new AP(player, this.ALWAYSON));
			
		}
		
		log.info("[AutoTool] By marinating loaded in " + (System.currentTimeMillis() - start) / 1000.0D + " seconds.");

	}

	public void loadProcedure() {
		
		try {
			
			FileInputStream in = new FileInputStream(CONFIG);
			
			prop.load(in);
			
			for (String s : ((String) prop.get("Pickaxe items")).split(" ")) {
				this.PICKAXE.add(Material.valueOf(s.toUpperCase()));
			}
			
			for (String s : ((String) prop.get("Axe items")).split(" ")) {
				this.AXE.add(Material.valueOf(s.toUpperCase()));
			}
			
			for (String s : ((String) prop.get("Shovel items")).split(" ")) {
				this.SHOVEL.add(Material.valueOf(s.toUpperCase()));
			}
			
			this.ALWAYSON = Boolean.parseBoolean(((String) prop.get("On by default?")).toLowerCase());
			
			in.close();
			
		} catch (Exception e) {
			log.severe("[AutoTool] Loading error: " + e.getMessage());
		}
		
	}

	public void onDisable() {
		
		this.ACTIVATED.clear();
		this.pickaxes.clear();
		this.shovels.clear();
		this.axes.clear();
		this.PICKAXE.clear();
		this.SHOVEL.clear();
		this.AXE.clear();
		log.info("[AutoTool] Closed and unloaded.");
		
	}

	public boolean isDebugging(Player player) {
		if (this.debugees.containsKey(player)) {
			return ((Boolean) this.debugees.get(player)).booleanValue();
		}
		return false;
	}

	public void setDebugging(Player player, boolean value) {
		this.debugees.put(player, Boolean.valueOf(value));
	}

	public boolean isPickaxe(Block b) {
		return this.PICKAXE.contains(b.getType());
	}

	public boolean isShovel(Block b) {
		return this.SHOVEL.contains(b.getType());
	}

	public boolean isAxe(Block b) {
		return this.AXE.contains(b.getType());
	}

	public ArrayList<Material> getAxes() {
		return this.axes;
	}

	public ArrayList<Material> getPickaxes() {
		return this.pickaxes;
	}

	public ArrayList<Material> getShovels() {
		return this.shovels;
	}
}