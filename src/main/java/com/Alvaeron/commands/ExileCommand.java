package com.Alvaeron.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.player.ExilePlayer;
import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.utils.Lang;

public class ExileCommand extends AbstractCommand {

	public ExileCommand(Engine plugin) {
		super(plugin, Senders.values());
	}

    @Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel,
    String[] args) {
    	
		final Player player = (Player) sender;

    	
    	if (args[0].equalsIgnoreCase("location"))
    	{
    		if(player.hasPermission("rpengine.exile.set")){
    			
    			StringBuilder sb = new StringBuilder();
    			sb.append("The current exile location is at: ");
    			sb.append(Engine.manager.exileLocation.getX()).append(", ");
    			sb.append(Engine.manager.exileLocation.getY()).append(", ");
    			sb.append(Engine.manager.exileLocation.getZ()).append(", ");
    			player.sendMessage(sb.toString());
    		}
    		else {
    			player.sendMessage("You do not have permission to do that.");
    		}
    	}
    	if (args[0].equalsIgnoreCase("set")) {
    		if(player.hasPermission("rpengine.exile.approve")){
    			
    			if (args.length >= 4) {    			
	    			plugin.getConfig().set("exile.x", Double.valueOf(args[1]));
	    			plugin.getConfig().set("exile.y", Double.valueOf(args[2]));
	    			plugin.getConfig().set("exile.z", Double.valueOf(args[3]));
	    			if (args.length >= 5) {
	    				plugin.getConfig().set("exile.world", String.valueOf(args[4]));
	    			}
    			
	    			plugin.saveConfig();
	    			
	    			ConfigurationSection location = plugin.getConfig().getConfigurationSection("exile");
	    			Double x = location.getDouble("x");
	    			Double y = location.getDouble("y");
	    			Double z = location.getDouble("z"); 
	    			String world = location.getString("world");
	    			
	    			Location l = new Location(Bukkit.getWorld(world), x, y, z);
	    			
	    			Engine.manager.exileLocation = l;	
	    			
	    			StringBuilder sb = new StringBuilder();
	    			sb.append("Exile location updated to: ");
	    			sb.append(Engine.manager.exileLocation.getX()).append(", ");
	    			sb.append(Engine.manager.exileLocation.getY()).append(", ");
	    			sb.append(Engine.manager.exileLocation.getZ()).append(", ");
	    			player.sendMessage(sb.toString());
    			}
    			else
    			{
    				player.sendMessage("You must have an x, y, and z coordinate. Format like /exile set x y z.");
    			}
    			
    		}
    		else {
    			player.sendMessage("You do not have permission to do that.");
    		}
    	}
        if (args[0].equalsIgnoreCase("approve") && args.length < 2) {
            if(player.hasPermission("rpengine.exile.approve")){
                final ExilePlayer targetPlayer = Engine.manager.exileQueue.poll();
                                
                if (targetPlayer !=  null)
                {
                	targetPlayer.player.getPlayer().teleport(Engine.manager.exileLocation);
                	plugin.getServer().broadcastMessage(Lang.EXILE_APPROVE.toString()
                    .replace("%n", targetPlayer.player.getName()));
                }
                else
                {
                	plugin.getServer().broadcastMessage(Lang.EXILE_EMPTY.toString());
                }
            }
            else {
            	player.sendMessage("You do not have permission to do that.");
            }
        }
        if (args[0].equalsIgnoreCase("deny") && args.length < 2) {
            if(player.hasPermission("rpengine.exile.approve")){
                final ExilePlayer targetPlayer = Engine.manager.exileQueue.poll();
                                
                if (targetPlayer !=  null)
                {
                	plugin.getServer().broadcastMessage(Lang.EXILE_DENY.toString()
                    .replace("%n", targetPlayer.player.getName()));
                }
                else
                {
                	plugin.getServer().broadcastMessage(Lang.EXILE_EMPTY.toString());
                }
            }
            else {
            	player.sendMessage("You do not have permission to do that.");
            }
        }
        if (args[0].equalsIgnoreCase("next") && args.length < 2) {
        	 if(player.hasPermission("rpengine.exile.set")){
        		 final ExilePlayer targetPlayer = Engine.manager.exileQueue.peek();
                                  
                 if (targetPlayer !=  null)
                 {
                 	plugin.getServer().broadcastMessage(Lang.EXILE_NEXT.toString()
                     .replace("%n", targetPlayer.player.getName())
                     .replace("%r", targetPlayer.reason));
                 }
                 else
                 {
                 	plugin.getServer().broadcastMessage(Lang.EXILE_EMPTY.toString());
                 }
        	 }
        	 else {
        		 player.sendMessage("You do not have permission to do that.");
        	 }
        }
        else{
        	if (args[0].equalsIgnoreCase("player")){
	            if(player.hasPermission("rpengine.exile.set")){
	                if (player.getServer().getPlayer(args[1]) != null) {
	                    if (player.getWorld() == plugin.getServer().getPlayer(args[1]).getWorld()) { // Makes sure they are in the same world
	                        final RoleplayPlayer targetPlayer = Engine.manager.getPlayer(player.getServer().getPlayer(args[1]).getUniqueId()); //gets the target player to be exiled
	                        

	                        if (Engine.manager.exileQueue.stream().filter(x -> x.player == targetPlayer).count() > 0)
	                        {
	                        	player.sendMessage("Player is already in queue for exile.");
	                        }
	                        else 
	                        {	                        
		                        StringBuilder reason = new StringBuilder();
		                        
		                        if (args.length == 2)
		                        {
		                            reason.append("No Specific Reason");
		                        }
		                        else
		                        {
		                        	for (int i = 2; i < args.length; i++) {
		                        		reason.append(args[i]);
		                        		
		                        		if (i < args.length -1 ) {
		                        			reason.append(" ");
		                        		}
		    						}	
		                        }
		                        
		                        Engine.manager.exileQueue.offer(new ExilePlayer(targetPlayer, reason.toString()));
		                        plugin.getServer().broadcastMessage(Lang.EXILE_USAGE.toString()
		                                .replace("%n", targetPlayer.getName())
		                                .replace("%r", reason.toString()));
	                        }
	                    } 
	                }
	            }
	            else {
	            	player.sendMessage("You do not have permission to do that.");
	            }
	        }
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (command.getName().equalsIgnoreCase("exile")) {

            List<String> mainArguments = Arrays.asList("player", "approve", "deny", "next", "set", "location");
            List<String> finalList = new ArrayList<>();

            if (args.length == 1) {

                if (!args[0].equals("")) {

                    for (String string : mainArguments) {
                       // This only works if the player has already started typing the subcommand
                        if (string.startsWith(args[0].toLowerCase())) {
                            finalList.add(string);
                        }
                    }

                } else {

                    // This does nothing
                    finalList = mainArguments;

                }

                Collections.sort(finalList);
                return finalList;
            }

            if (args.length == 0) {

                // This also does nothing
                finalList = mainArguments;
                Collections.sort(finalList);
                return finalList;

            }
        }

        return null;
    }
}