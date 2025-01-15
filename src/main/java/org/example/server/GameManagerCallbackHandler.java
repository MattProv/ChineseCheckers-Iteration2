package org.example.server;

import org.example.game_logic.Agent;
import org.example.game_logic.Move;
import org.example.message.StringMessage;

/**
 * Class representing the game manager callback handler.
 */
public class GameManagerCallbackHandler {
    /**
     * Function called when the game is started.
     */
    public void onGameStarted() {
        System.out.println("Game started");
        Server.getServer().Broadcast(new StringMessage("Game started!"));
    }

    /**
     * Function called when the game is ended.
     */
    public void onGameEnded() {
        System.out.println("Game ended");
        Server.getServer().Broadcast(new StringMessage("Game ended!"));
    }

    /**
     * Function called when the game is not started.
     * @param reason The reason why the game is not started.
     */
    public void onGameNotStarted(String reason)
    {
        System.out.println("Game not started: " + reason);
        Server.getServer().Broadcast(new StringMessage("Game not started: " + reason));
    }

    /**
     * Function called when the player count is changed.
     * @param oldCount The old player count.
     * @param newCount The new player count.
     */
    public void onPlayerCountChanged(int oldCount, int newCount)
    {
        System.out.println("Player count changed from " + oldCount + " to " + newCount);
        Server.getServer().Broadcast(new StringMessage("Player count changed from " + oldCount + " to " + newCount));
    }

    /**
     * Function called when the player count is not changed.
     * @param newCount The new player count.
     * @param reason The reason why the player count is not changed.
     */
    public void onPlayerCountNotChanged(int newCount, String reason)
    {
        System.out.println("Player count not changed to " + newCount + ": " + reason);
        Server.getServer().Broadcast(new StringMessage("Player count not changed to " + newCount + ": " + reason));
    }

    /**
     * Function called when the board is not changed.
     * @param reason The reason why the board is not changed.
     */
    public void onBoardNotChanged(String reason) {
        System.out.println("Board not changed: " + reason);
        Server.getServer().Broadcast(new StringMessage("Board not changed: " + reason));
    }

    /**
     * Function called when the board is changed.
     * @param info The information about the board change.
     */
    public void onBoardChanged(String info) {
        System.out.println("Board changed: " + info);
        Server.getServer().Broadcast(new StringMessage("Board changed: " + info));
    }

    /**
     * Function called when the rules are not changed.
     * @param reason The reason why the rules are not changed.
     */
    public void onRulesNotChanged(String reason) {
        System.out.println("Rules not changed: " + reason);
        Server.getServer().Broadcast(new StringMessage("Rules not changed: " + reason));
    }

    /**
     * Function called when the rules are changed.
     * @param info The information about the rules change.
     */
    public void onRulesChanged(String info) {
        System.out.println("Rules changed: " + info);
        Server.getServer().Broadcast(new StringMessage("Rules changed: " + info));
    }

    /**
     * Function called when an invalid move is made.
     * @param agent The agent making the move.
     * @param move The move made.
     * @param reason The reason why the move is invalid.
     */
    public void onInvalidMove(Agent agent, Move move, String reason) {
        System.out.println("Invalid move by " + agent + ": " + reason);
        Server.getServer().Broadcast(new StringMessage("Invalid move by " + agent + ": " + reason));
    }

    /**
     * Function called when a valid move is made.
     * @param agent The agent making the move.
     * @param move The move made.
     * @param s The information about the move.
     */
    public void onValidMove(Agent agent, Move move, String s) {
        System.out.println("Valid move by " + agent + ": " + s);
        Server.getServer().Broadcast(new StringMessage("Valid move by " + agent + ": " + s));
    }
}
