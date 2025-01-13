package org.example;

import org.example.game_logic.Agent;
import org.example.server.User;

public final class Player extends Agent {
    private final User owner;

    public Player(final User owner, int id, int startBaseIndex, int finishBaseIndex) {
        super(id, startBaseIndex, finishBaseIndex, true);
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
}
