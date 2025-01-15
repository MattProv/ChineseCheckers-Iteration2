package org.example.server;

import org.example.message.Message;

/**
 * Class representing the server callbacks handler.
 */
public class ServerCallbacksHandler {

    /**
     * Function called when a new connection is established.
     * @param connection The connection.
     */
    public void onNewConnection(final ServerConnection connection) {
        System.out.println("User connected: " + connection);
        GameManager.getInstance().synchronizeGameState();
    }

    /**
     * Function called when a connection is closed.
     * @param connection The connection.
     */
    public void onConnectionClosed(final ServerConnection connection) {
        System.out.println("User disconnected: " + connection);
    }

    /**
     * Function called when a message is received.
     * @param connection The connection.
     * @param message The message.
     */
    public void onMessageReceived(final ServerConnection connection, final Message message) {
        System.out.println("Message received: [" + connection + "] " + message.getType().name() + " " + message);
        synchronized ( Server.getServer()) {
            Server.getServer().HandleMessages();
        }
    }

    /**
     * Function called when a message is sent.
     * @param connection The connection.
     * @param message The message.
     */
    public void onMessageSent(final ServerConnection connection, final Message message) {
        System.out.println("Message sent: [" + connection + "] " + message.getType().name() + " " + message);
    }
}
