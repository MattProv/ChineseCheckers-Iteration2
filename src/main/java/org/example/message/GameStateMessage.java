package org.example.message;

import org.example.GameState;

public final class GameStateMessage extends Message {

    private final GameState gameState;
    //current turn

    public GameStateMessage(final GameState gameState) {
        super(MessageType.GAMESTATE);
        this.gameState = gameState;
    }

    @Override
    public String toString() {
        return "Game is " + (gameState.isRunning()?"running":"not running") + ".";
    }

    public GameState getGameState() {
        return gameState;
    }
}
