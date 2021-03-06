package com.frogman786.froggles;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.frogman786.froggles.commands.Announce;
import com.frogman786.froggles.commands.cfg;
import com.frogman786.froggles.commands.chest;
import com.frogman786.froggles.commands.filter;
import com.frogman786.froggles.commands.gm;
import com.frogman786.froggles.commands.rainbow;
import com.frogman786.froggles.commands.tracking;
import com.frogman786.froggles.commands.who;
import com.frogman786.froggles.commands.world;
import com.frogman786.froggles.commands.platform;
import com.frogman786.froggles.commands.time;
import com.frogman786.froggles.commands.pos;
//import com.frogman786.froggles.commands.ban;
import com.frogman786.froggles.commands.spleef;
import com.frogman786.froggles.commands.me;
import com.frogman786.froggles.commands.zombies;
import com.frogman786.froggles.commands.frogcommand;
import com.frogman786.froggles.commands.spawns;
import com.frogman786.froggles.utils.PlayerHTMLformatted;
 
public class Froggles extends JavaPlugin{
   
    private static Plugin plugin;
    public static Map<String, String> replymap = new HashMap<String, String>();
    public static Map<String, String> configmap = new HashMap<String, String>();
    public static Map<String,Boolean> trackingmap = new HashMap<String, Boolean>();
    public static Map<String,Boolean> rainbowmap = new HashMap<String, Boolean>();
    public static Map<String,String> colourmap = new HashMap<String, String>();
    public static Map<String,String> freezemap = new HashMap<String, String>();
    public static String[] bannedblocks = {"AIR"};
	public static boolean zom_vill_safe = false;
	public static boolean frogcommand = false;
	public static Map<String, Integer> spawnmapx = new HashMap<String,Integer>();
	public static Map<String, Integer> spawnmapy = new HashMap<String,Integer>();
	public static Map<String, Integer> spawnmapz = new HashMap<String,Integer>();
	public static List<String> swears = Collections.synchronizedList(new ArrayList<String>());
	public static List<String> swearmessage = Collections.synchronizedList(new ArrayList<String>());
	public static List<String> netherdoor = Collections.synchronizedList(new ArrayList<String>());
	public static List<String> rankslist = Collections.synchronizedList(new ArrayList<String>());
	public static double gravity = 0.32;
	public static List<String> bouncingbullets = Collections.synchronizedList(new ArrayList<String>());
	public static List<String> rodeobullets = Collections.synchronizedList(new ArrayList<String>());
	public static List<String> voxelbullets = Collections.synchronizedList(new ArrayList<String>());
	
    public void onEnable() {
        plugin = this;
        registerEvents(this, new Events());
        configini();
        commandini();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                PlayerHTMLformatted.refreshList(Bukkit.getOnlinePlayers());
            }
        }, 0L, 1200L);
    }
   
    public void onDisable() {
       swearsaver();
    }
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    public static Plugin getPlugin() {
        return plugin;
    }
    
    private void configini(){
    	FileConfiguration config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		for(String str: getConfig().getKeys(true)) {
			
			 if(str.startsWith("message")){
			String p = getConfig().getString(str);
			configmap.put(str, p);
			 }
			 if(str.startsWith("colour")){
					String p = getConfig().getString(str);
					colourmap.put(str, p);
					 }
		}
		for(String word: getConfig().getStringList("filter.swears")){
			swears.add(word);
		}
		for(String word: getConfig().getStringList("filter.messages")){
			swearmessage.add(word);
		}
		for(String word: getConfig().getStringList("netherdoor.positions")){
			netherdoor.add(word);
		}
		for(String word: getConfig().getStringList("ranks")){
			rankslist.add(word);
		}
    }
    
    private void swearsaver(){
    	plugin.reloadConfig();
    	FileConfiguration config = getConfig();
    	config.set("filter.swears", swears);
    	config.set("filter.messages", swearmessage);
		config.options().copyDefaults(true);
		saveConfig();
    }
    
    private void commandini(){
    	//gamemode
    	getCommand("gm").setExecutor(new gm());
    	//info
        getCommand("who").setExecutor(new who());
        getCommand("colourcodes").setExecutor(new com.frogman786.froggles.commands.colours());
        //info coords
        getCommand("world").setExecutor(new world());
        getCommand("pos").setExecutor(new pos());
        //platform
        getCommand("platform").setExecutor(new platform());
        //time
        getCommand("dawn").setExecutor(new time());
        getCommand("day").setExecutor(new time());
        getCommand("noon").setExecutor(new time());
        getCommand("dusk").setExecutor(new time());
        getCommand("night").setExecutor(new time());
        //kick/ban
        //getCommand("kick").setExecutor(new ban());
        //spleef
        getCommand("spleeflayer").setExecutor(new spleef());
        getCommand("cuboid").setExecutor(new spleef());
        //announce
        getCommand("me").setExecutor(new me());
        //pms
        getCommand("pms").setExecutor(new pms());
        getCommand("r").setExecutor(new pms());
        //compass
        getCommand("track").setExecutor(new tracking());
        getCommand("untrack").setExecutor(new tracking());
        //debugs
        getCommand("pmsdebug").setExecutor(new pms());
        getCommand("cfgdebug").setExecutor(new cfg());
        getCommand("trackdebug").setExecutor(new tracking());
        //announcements
        getCommand("announce").setExecutor(new Announce());
        getCommand("announcepromo").setExecutor(new Announce());
        //automatic text formatting
        getCommand("rainbowtext").setExecutor(new rainbow());
        //implemented specifically for games
        getCommand("zombiesafety").setExecutor(new zombies());
        getCommand("gamespawn").setExecutor(new spawns());
        getCommand("randomchest").setExecutor(new chest());
        getCommand("randomtp").setExecutor(new com.frogman786.froggles.commands.RTP());
        getCommand("switchtp").setExecutor(new com.frogman786.froggles.commands.stp());
        //
        getCommand("chatfilter").setExecutor(new filter());
        //useless
        getCommand("runcheck").setExecutor(new com.frogman786.froggles.commands.runcheck());
        getCommand("frogcommand").setExecutor(new frogcommand());
        //fake join/quit message
        getCommand("fakejoin").setExecutor(new com.frogman786.froggles.commands.fakes());
        getCommand("fakequit").setExecutor(new com.frogman786.froggles.commands.fakes());
        //moving away from betteralias to static commands
        getCommand("clreff").setExecutor(new com.frogman786.froggles.commands.clear());
        getCommand("ranks").setExecutor(new com.frogman786.froggles.commands.ranks());
        getCommand("freeze").setExecutor(new com.frogman786.froggles.commands.freeze());
        getCommand("unfreeze").setExecutor(new com.frogman786.froggles.commands.freeze());
        getCommand("throw").setExecutor(new com.frogman786.froggles.commands.launcher());
        getCommand("bouncingbullets").setExecutor(new com.frogman786.froggles.commands.bbcommand());
        getCommand("rodeobullets").setExecutor(new com.frogman786.froggles.commands.rbcommand());
        getCommand("voxelbullets").setExecutor(new com.frogman786.froggles.commands.vbcommand());
        //TODO wait for json parsing, getCommand("rules").setExecutor(new com.frogman786.froggles.commands.rules());
    }
   
   
   
 
}