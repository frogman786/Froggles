package com.frogman786.froggles.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class pos implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command,String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(player.hasPermission("frog.pos.self")){
				int x = (int) player.getLocation().getX();
				int y = (int) player.getLocation().getY();
				int z = (int) player.getLocation().getZ();
				player.sendMessage(ChatColor.DARK_AQUA+"World: " + ChatColor.DARK_GREEN + player.getWorld().getName() + ChatColor.DARK_AQUA + " X: "+ChatColor.WHITE+x+ ChatColor.DARK_AQUA +" Y: "+ChatColor.WHITE+y+ ChatColor.DARK_AQUA +" Z: "+ChatColor.WHITE+z);
				return true;
			}else{
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
		}else{
			sender.sendMessage("no console use yet");
			return true;
		}
	}

}