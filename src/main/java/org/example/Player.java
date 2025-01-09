package org.example;

import org.example.server.ServerConnection;

public final class Player {
    private final ServerConnection owner;

    public Player(final ServerConnection owner) {
        this.owner = owner;
    }

    public ServerConnection getOwner() {
        return owner;
    }
}
