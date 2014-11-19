package com.tebakgambar.model;

/**
 * Model Player
 * 
 * @author Ade Fruandta
 */
public class Player {
    
    private User user;
    private int score;
    private Boolean turn;

    public Player() {
        this.score = 0;
        this.turn = false;
    }

    public Player(User user) {
        this();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
