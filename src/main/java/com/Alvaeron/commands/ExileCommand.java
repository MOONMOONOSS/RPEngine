package com.Alvaeron.commands;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.utils.Lang;

public class ExileCommand extends AbstractCommand {

	public ExileCommand(Engine plugin) {
		super(plugin, Senders.values());
	}

    @Override
	public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel,
    String[] args) {
        if (args[0].toLowerCase() == 'approve' && args.length < 2)
            if(player.hasPermission('rpengine.exile.approve')){
                final RoleplayPlayer targetPlayer = Engine.manager.exileQueue.poll();

                if (targetPlayer ==  null)
                {
                    player.sendMessage(Lang.EXILE_APPROVAL.toString()
                    .replace("%n", targetPlayer.getName())
                }
                else
                {
                    player.sendMessage(Lang.EXILE_EMPTY.toString()
                    .replace("%n", targetPlayer.getName())
                }
            }
        else{
            if(player.hasPermission('rpengine.exile.set')){
                if (player.getServer().getPlayer(args[0]) != null) {
                    if (player.getWorld() == plugin.getServer().getPlayer(args[0]).getWorld()) { // Makes sure they are in the same world
                        final RoleplayPlayer targetPlayer = Engine.manager.getPlayer(player.getServer().getPlayer(args[0]).getUniqueId()); //gets the target player to be exiled
                        final String reason = ''
                        if (args.length < 2)
                            reason = "No Specific Reason"
                        else 
                            reason = args[1];

                        Engine.manager.exileQueue.offer(targetPlayer);
                        plugin.getServer().broadcastMessage(Lang.EXILE_USAGE.toString()
                                .replace("%n", targetPlayer.getName())
                                .replace("%r", reason)
                    } 
                }
            }
        }
    }
}