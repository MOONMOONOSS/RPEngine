package com.Alvaeron.api;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.player.RoleplayPlayer.Channel;
import com.Alvaeron.player.RoleplayPlayer.Gender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class RPEngineAPI {

    private RPEngineAPI() {
    }

    /**
     * Returns a RolePlayer data class
     *
     * @param p Bukkit Player
     * @return RolePlayer data class
     */
    //Returns a RoleplayPlayer data class
    public static RoleplayPlayer getRoleplayPlayer(Player p) {
        return Engine.manager.getPlayer(p.getUniqueId());
    }

    /**
     * Returns a RolePlayer data class
     *
     * @param uuid UUID of a Bukkit Player
     * @return RolePlayer data class
     */
    public static RoleplayPlayer getRoleplayPlayer(UUID uuid) {
        return Engine.manager.getPlayer(uuid);
    }

    /**
     * Returns a RolePlayer data class
     *
     * @param playerName Name of a Bukkit Player
     * @return RolePlayer data class
     */
    @SuppressWarnings("deprecation")
    public static RoleplayPlayer getRoleplayPlayer(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            return Engine.manager.getPlayer(player.getUniqueId());
        }

        OfflinePlayer p = Bukkit.getOfflinePlayer(playerName);
        if (p == null || !p.hasPlayedBefore()) {
            return null;
        }

        RoleplayPlayer rpPlayer = Engine.manager.getPlayer(p.getUniqueId());
        if (rpPlayer != null) {
            return rpPlayer;
        } else {
            Engine.mm.createRoleplayPlayer(p);
            return Engine.manager.getPlayer(p.getUniqueId());
        }
    }

    public static String getRpName(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::getName)
            .orElse("NONE");
    }

    public static int getRpAge(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::getAge)
            .orElse(0);
    }

    public static Gender getRpGender(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::getGender)
            .orElse(null);
    }

    public static String getRpRace(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::getRace)
            .orElse("NONE");
    }

    public static String getRpNation(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::getNation)
            .orElse("NONE");
    }

    public static String getRpDesc(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::getDesc)
            .orElse("NONE");
    }

    public static Channel getChannel(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::getChannel)
            .orElse(null);
    }

    public static boolean getOOC(String playerName) {
        return Optional.ofNullable(getRoleplayPlayer(playerName))
            .map(RoleplayPlayer::isOOC)
            .orElse(false);
    }

    public static void setRpName(String playerName, String name) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setName(name));
    }

    public static void setRpAge(String playerName, int age) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setAge(age));
    }

    public static void setRpGender(String playerName, Gender gender) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setGender(gender));
    }

    public static void setRpRace(String playerName, String race) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setRace(race));
    }

    public static void setRpNation(String playerName, String nation) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setNation(nation));
    }

    public static void setRpDesc(String playerName, String desc) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setDesc(desc));
    }

    public static void setChannel(String playerName, Channel channel) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setChannel(channel));
    }

    public static void setOOC(String playerName, boolean ooc) {
        Optional.ofNullable(getRoleplayPlayer(playerName))
            .ifPresent(p -> p.setOOC(ooc));
    }

    public static Set<String> getRaces() {
        return Engine.rpEngine.getConfig().getConfigurationSection("Races").getKeys(false);
    }

    public static Set<String> getNations() {
        return Engine.rpEngine.getConfig().getConfigurationSection("Nations").getKeys(false);
    }

    public static ChatColor getRaceColor(String race) {
        return Engine.mu.getRaceColour(race);
    }
}
