package org.example.server;

import org.example.message.StringMessage;

public class GameManagerCallbackHandler {
    public void onGameStarted() {
        System.out.println("Game started");
        Server.getServer().Broadcast(new StringMessage("Game started!"));
    }
    public void onGameEnded() {
        System.out.println("Game ended");
        Server.getServer().Broadcast(new StringMessage("Game ended!"));
    }

    public void onGameNotStarted(String reason)
    {
        System.out.println("Game not started: " + reason);
        Server.getServer().Broadcast(new StringMessage("Game not started: " + reason));
    }

    public void onPlayerCountChanged(int oldCount, int newCount)
    {
        System.out.println("Player count changed from " + oldCount + " to " + newCount);
        Server.getServer().Broadcast(new StringMessage("Player count changed from " + oldCount + " to " + newCount));
    }

    public void onPlayerCountNotChanged(int newCount, String reason)
    {
        System.out.println("Player count not changed to " + newCount + ": " + reason);
        Server.getServer().Broadcast(new StringMessage("Player count not changed to " + newCount + ": " + reason));
    }
}
