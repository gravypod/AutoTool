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
	
	public static Logger log = Logger.getLogger("Minecraft");
	
	static String mainDirectory = "plugins/AutoTool";
	
	public HashMap<Player, AutoPlayer> ACTIVATED = new HashMap<Player, AutoPlayer>();
	
	public static File CONFIG = new File(mainDirectory + File.separator + "AutoTool.txt");
	
	private ArrayList<Material> pickaxes;
	
	private ArrayList<Material> shovels;
	
	private ArrayList<Material> axes;
	
	private ArrayList<Material> PICKAXE;
	
	private ArrayList<Material> SHOVEL;
	
	private ArrayList<Material> AXE;
	
	static Properties prop = null;
	
	public boolean ALWAYSON = false;
	
	@Override
	public void onEnable() {
	
		long start = System.currentTimeMillis();
		
		prop = new Properties();
		PICKAXE = new ArrayList<Material>();
		SHOVEL = new ArrayList<Material>();
		AXE = new ArrayList<Material>();
		pickaxes = new ArrayList<Material>();
		shovels = new ArrayList<Material>();
		axes = new ArrayList<Material>();
		
		new File(mainDirectory).mkdir();
		
		if (!CONFIG.exists())
			
			if (!stupConfig()) {
				
				log.info("[AutoTool] Could not load config, disabling.");
				
				return;
				
			} else {
				
				log.info("[AutoTool] Detected existing config file and loading.");
				
			}
		
		loadProcedure();
		
		getServer().getPluginManager().registerEvents(new AutoToolPlayerListener(this), this);
		
		getCommand("autotool").setExecutor(new Commands(this));
		
		log.info("[AutoTool] By marinating loaded in " + (System.currentTimeMillis() - start) / 1000.0D + " seconds.");
	}
	
	public void loadProcedure() {
	
		FileInputStream in = null;
		
		try {
			
			in = new FileInputStream(CONFIG);
			prop.load(in);
			
		} catch (Exception e) {
		} finally {
			
			Material materialInConfig = null;
			
			for (String configItem : ((String) prop.get("Pickaxes")).split(",")) {
				
				materialInConfig = Material.getMaterial(configItem);
				
				if (materialInConfig == null)
					materialInConfig = Material.getMaterial(Integer.parseInt(configItem));
				
				pickaxes.add(materialInConfig);
			}
			
			for (String configItem : ((String) prop.get("Pickaxe-items")).split(",")) {
				
				materialInConfig = Material.getMaterial(configItem);
				
				if (materialInConfig == null)
					materialInConfig = Material.getMaterial(Integer.parseInt(configItem));
				
				PICKAXE.add(materialInConfig);
				
			}
			
			for (String configItem : ((String) prop.get("Axes")).split(",")) {
				
				materialInConfig = Material.getMaterial(configItem);
				
				if (materialInConfig == null)
					materialInConfig = Material.getMaterial(Integer.parseInt(configItem));
				
				axes.add(materialInConfig);
			}
			
			for (String configItem : ((String) prop.get("Axe-items")).split(",")) {
				
				materialInConfig = Material.getMaterial(configItem);
				
				if (materialInConfig == null)
					materialInConfig = Material.getMaterial(Integer.parseInt(configItem));
				
				AXE.add(materialInConfig);
				
			}
			
			for (String configItem : ((String) prop.get("Shovels")).split(",")) {
				
				materialInConfig = Material.getMaterial(configItem);
				
				if (materialInConfig == null)
					materialInConfig = Material.getMaterial(Integer.parseInt(configItem));
				
				shovels.add(materialInConfig);
			}
			
			for (String configItem : ((String) prop.get("Shovel-items")).split(",")) {
				
				materialInConfig = Material.getMaterial(configItem);
				
				if (materialInConfig == null)
					materialInConfig = Material.getMaterial(Integer.parseInt(configItem));
				
				SHOVEL.add(materialInConfig);
			}
			
			ALWAYSON = Boolean.parseBoolean(((String) prop.get("On-by-default?")).toLowerCase());
			
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void onDisable() {
	
		ACTIVATED.clear();
		pickaxes.clear();
		shovels.clear();
		axes.clear();
		PICKAXE.clear();
		SHOVEL.clear();
		AXE.clear();
		log.info("[AutoTool] Closed and unloaded.");
		
	}
	
	public boolean stupConfig() {
	
		try {
			
			CONFIG.createNewFile();
			FileOutputStream out = new FileOutputStream(CONFIG);
			prop.put("Pickaxes", "WOOD_PICKAXE,STONE_PICKAXE,IRON_PICKAXE,GOLD_PICKAXE,DIAMOND_PICKAXE");
			prop.put("Pickaxe-items", "STONE,IRON_ORE,GOLD_ORE,REDSTONE_ORE,COAL_ORE,DIAMOND_ORE,LAPIS_ORE,COBBLESTONE,MOSSY_COBBLESTONE,NETHERRACK,OBSIDIAN,IRON_BLOCK,GOLD_BLOCK,DIAMOND_BLOCK,GLOWSTONE,LAPIS_BLOCK,SANDSTONE,BRICK,STEP,BRICK_STAIRS,COBBLESTONE_STAIRS,");
			prop.put("Shovels", "WOOD_SPADE,STONE_SPADE,IRON_SPADE,GOLD_SPADE,DIAMOND_SPADE");
			prop.put("Shovel-items", "DIRT,GRASS,GRAVEL,SAND,SNOW_BLOCK,CLAY");
			prop.put("Axes", "WOOD_AXE,STONE_AXE,IRON_AXE,GOLD_AXE,DIAMOND_AXE");
			prop.put("Axe-items", "LOG,WOOD,WOOD_DOOR");
			prop.put("On-by-default?", "false");
			prop.store(out, "All items must be seperated by an ',' and may either be an Item id or the item name in caps");
			out.flush();
			out.close();
			log.info("[AutoTool] New file created.");
			return true;
			
		} catch (IOException ex) {
			
			log.warning("[AutoTool] File creation error: " + ex.getMessage());
			return false;
			
		}
		
	}
	
	public boolean isPickaxe(Block block) {
	
		return PICKAXE.contains(block.getType());
	}
	
	public boolean isShovel(Block block) {
	
		return SHOVEL.contains(block.getType());
	}
	
	public boolean isAxe(Block block) {
	
		return AXE.contains(block.getType());
	}
	
	public ArrayList<Material> getAxes() {
	
		return axes;
	}
	
	public ArrayList<Material> getPickaxes() {
	
		return pickaxes;
	}
	
	public ArrayList<Material> getShovels() {
	
		return shovels;
	}
}
