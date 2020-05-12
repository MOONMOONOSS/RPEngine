package com.Alvaeron.commands;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.utils.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BirdCommand extends AbstractCommand {

    public BirdCommand(Engine plugin) {
        super(plugin, Senders.PLAYER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel, final String[] args) {
        final Player player = (Player) sender;
        final RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId()); // Sender player

        if (args.length >= 2) {
            if (player.getServer().getPlayer(args[0]) != null) {
                if (player.getWorld() == plugin.getServer().getPlayer(args[0]).getWorld()) { // Makes sure they are in the same world
                    if (player.getInventory().contains(Material.PAPER)) {
                        player.getInventory().removeItem(new ItemStack(Material.PAPER, 1));
                        final RoleplayPlayer targetPlayer = Engine.manager.getPlayer(player.getServer().getPlayer(args[0]).getUniqueId());
                        // Create a player obj with the target player instead of fucking calling getPlayer every time
                        final Player tpg = targetPlayer.getPlayer();
                        final String targetPlayerName = targetPlayer.getName();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }

                        final String message = sb.toString().trim();
                        double distance = tpg.getLocation().distance(player.getLocation());
                        double time = (distance / plugin.getConfig().getDouble("speed")) * 20;
                        player.sendMessage(Lang.BIRD_SENT.toString()
                                .replace("%n", Engine.mu.getRaceColour(targetPlayer.getRace()) + targetPlayerName)
                                .replace("%p", tpg.getName()));
                        // Play sound to player that sent the bird to simulate the bird leaving
                        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);
                        // Create runnable to trigger the delivered message after the required time has been reached
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                if (player.getServer().getPlayer(args[0]) != null) {
                                    // Play takeoff sound to receiving player to simulate bird arriving
                                    tpg.playSound(tpg.getLocation(), Sound.ENTITY_BAT_LOOP, 1f, 1f);
                                    player.sendMessage(Lang.BIRD_DELIVER.toString()
                                            .replace("%n", Engine.mu.getRaceColour(targetPlayer.getRace()) + targetPlayerName)
                                            .replace("%p", tpg.getName()));
                                    tpg.sendMessage(Lang.BIRD_LAND.toString()
                                            .replace("%n", Engine.mu.getRaceColour(rpp.getRace()) + rpp.getName())
                                            .replace("%p", player.getName()));
                                    tpg.sendMessage(ChatColor.WHITE + message);
                                    tpg.sendMessage(ChatColor.AQUA + "-----------------------------");
                                } else {
                                    player.sendMessage(Lang.BIRD_LOST.toString());
                                }
                            }
                        }, (long) time);
                    } else {
                        player.sendMessage(Lang.BIRD_PAPER.toString());
                    }
                } else {
                    player.sendMessage(Lang.BIRD_DIFFERENT_WORLD.toString());
                }
            } else {
                player.sendMessage(Lang.BIRD_OFFLINE.toString().replace("%p", args[0]));
            }
        } else {
            player.sendMessage(Lang.BIRD_USAGE.toString());
        }
        return true;
    }
}
