package com.Alvaeron.commands;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class AbstractCommand implements CommandExecutor {
    protected Engine plugin;
    protected Player player;
    protected RoleplayPlayer rpp;
    private final Senders[] definedSenders;

    /**
     * An AbstractCommand is a command with extra checks.
     *
     * @param plugin         - An instance of RPEngine
     * @param definedSenders - What kind of CommandSenders should this command work for?
     */
    public AbstractCommand(Engine plugin, Senders... definedSenders) {
        this.plugin = plugin;
        this.definedSenders = definedSenders;
    }

    /**
     * handleCommand is onCommand, after checks are done.
     *
     * @param sender       - the commandSender
     * @param cmd          - the command
     * @param Commandlabel - the command label
     * @param args         - the arguments
     * @return did this command succeed?
     */
    public abstract boolean handleCommand(CommandSender sender, Command cmd, String Commandlabel,
                                          String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Commandlabel,
                             String[] args) {
        boolean error = false;
        if (sender instanceof BlockCommandSender) {
            error = !Arrays.asList(definedSenders).contains(Senders.COMMANDBLOCK);
        }
        if (sender instanceof ConsoleCommandSender) {
            error = !Arrays.asList(definedSenders).contains(Senders.CONSOLE);
        }
        if (sender instanceof Player) {
            error = !Arrays.asList(definedSenders).contains(Senders.PLAYER);
            player = (Player) sender;
            rpp = Engine.manager.getPlayer(player.getUniqueId());
        }
        if (error) {
            sender.sendMessage("This command can only be run by:" + definedSenders.toString());
            return false;
        }
        return handleCommand(sender, cmd, Commandlabel, args);
    }

    public enum Senders {
        CONSOLE, PLAYER, COMMANDBLOCK
    }
}
