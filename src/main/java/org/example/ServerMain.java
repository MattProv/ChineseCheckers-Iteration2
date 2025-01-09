package org.example;

import org.example.message.serverHandlers.CommandMessageHandler;
import org.example.message.serverHandlers.MoveMessageHandler;
import org.example.server.GameManager;
import org.example.server.Server;

public class ServerMain {
    public static void main(final String[] args) {

        System.out.println("Hello World from Server!");

        GameManager gameManager = GameManager.create();
        gameManager.setBoard(new TestBoard());

        Server server = Server.create();

        server.AddHandler(new MoveMessageHandler(gameManager));
        server.AddHandler(new CommandMessageHandler(gameManager));
        server.Bind(Config.PORT);
        server.Listen();

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
