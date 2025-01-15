package org.example.client;

import org.example.message.Message;

/**
 * Class used to handle the callbacks from the client
 */
public class ClientCallbacksHandler {

    /**
     * Method called when the client is disconnected
     */
    public void onDisconnect() {
        System.out.println("Disconnected");
    }

    /**
     * Method called when the client connects successfully
     */
    public void onConnect() {
        System.out.println("Connected");
    }

    /**
     * Method called when a message is received
     * @param message The message received
     * @see Message
     */
    public void onMessageReceived(Message message) {
        System.out.println("Message received: " + message.getType().name() + " " + message);
        synchronized (Client.getClient()) {
            Client.getClient().HandleMessages();
        }
    }

    /**
     * Method called when a message is sent
     * @param message The message sent
     * @see Message
     */
    public void onMessageSent(Message message) {
        System.out.println("Message sent: " + message.getType().name() + " " + message);
    }

    /**
     * Method called when an error occurs
     */
    public void onSocketError() {
        System.out.println("Error: Received a null message.");
    }
}
