package com.frogman786.froggles;
 
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.frogman786.froggles.commands.gm;
import com.frogman786.froggles.commands.who;
import com.frogman786.froggles.commands.world;
import com.frogman786.froggles.commands.platform;
import com.frogman786.froggles.commands.time;
import com.frogman786.froggles.commands.pos;
import com.frogman786.froggles.commands.ban;
import com.frogman786.froggles.commands.spleef;
import com.frogman786.froggles.commands.me;
 
public class Froggles extends JavaPlugin{
   
    private static Plugin plugin;
    public static Map<String, String> replymap = new HashMap<String, String>();
    public static Map<String, String> configmap = new HashMap<String, String>();
    public static Map<String, String> trackingmap = new HashMap<String, String>();
    public void onEnable() {
        plugin = this;
        registerEvents(this, new Events());
        configini();
        commandini();
    }
   
    public void onDisable() {
       
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
			 
			String p = getConfig().getString(str);
			 
			configmap.put(str, p);
		}
    }
    
    private void commandini(){
    	getCommand("gm").setExecutor(new gm());
        getCommand("who").setExecutor(new who());
        getCommand("world").setExecutor(new world());
        getCommand("pos").setExecutor(new pos());
        getCommand("platform").setExecutor(new platform());
        getCommand("dawn").setExecutor(new time());
        getCommand("day").setExecutor(new time());
        getCommand("noon").setExecutor(new time());
        getCommand("dusk").setExecutor(new time());
        getCommand("night").setExecutor(new time());
        getCommand("kick").setExecutor(new ban());
        getCommand("spleeflayer").setExecutor(new spleef());
        getCommand("me").setExecutor(new me());
        getCommand("pms").setExecutor(new pms());
        getCommand("r").setExecutor(new pms());
        getCommand("pmsdebug").setExecutor(new pms());
        getCommand("cfgdebug").setExecutor(new com.frogman786.froggles.commands.cfg());
        getCommand("track").setExecutor(new com.frogman786.froggles.commands.tracking());
        getCommand("untrack").setExecutor(new com.frogman786.froggles.commands.tracking());
    }
   
   
   
 
}