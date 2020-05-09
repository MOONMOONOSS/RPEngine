package com.Alvaeron.player;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Alvaeron.Engine;

public class PlayerManager implements Listener {
	public Engine plugin;
	public ArrayList<RoleplayPlayer> players = new ArrayList<RoleplayPlayer>();
	public Queue<ExilePlayer> exileQueue = new LinkedList<ExilePlayer>();
	public Location exileLocation;
	public PlayerManager(Engine plugin) {
		this.plugin = plugin;
	}

	public RoleplayPlayer getPlayer(UUID uuid) {
		for (RoleplayPlayer p : players) {
			if (p == null) continue;
			if (p.uuid.equals(uuid)) {
				return p;
			}
		}
		return null;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		clearPlayer(event.getPlayer().getUniqueId());
		Engine.nametags.reset(event.getPlayer().getName());
	}

	public void clearPlayer(UUID u) {
		if (getPlayer(u) != null) {
			players.remove(getPlayer(u));
		}
	}

	public void addPlayer(RoleplayPlayer rpp) {
		clearPlayer(rpp.uuid);
		players.add(rpp);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
        Engine.nametags.sendTeams(player);
        Engine.nametags.reset(player.getName());
		Engine.mm.createRoleplayPlayer(player);
	}
}
