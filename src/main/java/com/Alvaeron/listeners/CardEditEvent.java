package com.Alvaeron.listeners;

import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.player.RoleplayPlayer.Gender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CardEditEvent extends Event {
    //Bukkit Part
    private static final HandlerList handlers = new HandlerList();
    private CardField cardField = null;
    private String stringValue = null;
    private int intValue = 0;
    private Gender gender = null;
    private Player p = null;
    private RoleplayPlayer rpp = null;
    private boolean cancelled = false;

    public CardEditEvent(CardField cardField, String value, Player p, RoleplayPlayer rpp) {
        this.cardField = cardField;
        stringValue = value;
        this.p = p;
        this.rpp = rpp;
    }

    public CardEditEvent(CardField cardField, int value, Player p, RoleplayPlayer rpp) {
        this.cardField = cardField;
        intValue = value;
        this.p = p;
        this.rpp = rpp;
    }

    public CardEditEvent(CardField cardField, Gender value, Player p, RoleplayPlayer rpp) {
        this.cardField = cardField;
        this.gender = value;
        this.p = p;
        this.rpp = rpp;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    //Getters
    public String getStringValue() {
        return stringValue;
    }

    //Setters
    public void setStringValue(String value) {
        this.stringValue = value;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int value) {
        this.intValue = value;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender value) {
        this.gender = value;
    }

    public CardField getCardField() {
        return cardField;
    }

    public Player getPlayer() {
        return p;
    }

    public RoleplayPlayer getRoleplayPlayer() {
        return rpp;
    }

    //Cancel stuff
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public enum CardField {
        NAME, AGE, GENDER, RACE, NATION, DESC
    }
}
