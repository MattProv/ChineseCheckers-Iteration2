package org.example.game_logic;

import org.example.message.PromptMoveMessage;
import org.example.server.Server;
import org.example.server.User;

public final class Player extends Agent {
    private transient final User owner;

    public Player(final User owner, int id) {
        super(id, true);
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public void promptMove(Board board) {
        Server.getServer().Send(new PromptMoveMessage(), owner.getConnection());
    }
}
