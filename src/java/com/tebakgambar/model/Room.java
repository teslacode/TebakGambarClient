package com.tebakgambar.model;

import com.tebakgambar.service.RfTema;
import com.tebakgambar.service.Soal;
import java.util.ArrayList;
import java.util.List;

/**
 * Model Room
 * 
 * @author Ade Fruandta
 */
public class Room {
    
    private String id;
    private RfTema rfTema;
    private int maxPlayer;
    private List<Player> players;
    private List<Soal> soals;

    public Room() {
        this.players = new ArrayList();
        this.soals = new ArrayList();
    }

    public Room(String id, RfTema rfTema, int maxPlayer) {
        this();
        this.id = id;
        this.rfTema = rfTema;
        this.maxPlayer = maxPlayer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RfTema getRfTema() {
        return rfTema;
    }

    public void setRfTema(RfTema rfTema) {
        this.rfTema = rfTema;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Soal> getSoals() {
        return soals;
    }

    public void setSoals(List<Soal> soals) {
        this.soals = soals;
    }
    
    public void addPlayer(Player player){
        this.players.add(player);
    }
    
    public void removePlayer(Player player){
        this.players.remove(player);
    }
    
}
