
package com.mrspd.dotsandboxes.Models;

public abstract class Player {
    protected final String name;
    private Graph game;

    public Player(String name) {
        this.name = name;
    }

    public static int indexIn(Player player, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (player == players[i])
                return i;
        }
        return -1;
    }

    public abstract Line move();

    public Graph getGame() {
        return game;
    }

    public void addToGame(Graph game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }
}
