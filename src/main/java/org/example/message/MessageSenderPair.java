package org.example.message;

import org.example.server.ServerConnection;
/**
 * Class representing a message and the connection it came from.
 */
public class MessageSenderPair {
    // The message
    private final Message message;
    // The connection
    private final ServerConnection connection;

    public MessageSenderPair(final Message message, final ServerConnection connection) {
        this.message = message;
        this.connection = connection;
    }

    public Message getMessage() {
        return message;
    }

    public ServerConnection getConnection() {
        return connection;
    }

    public MessageType getMessageType() {
        return message.getType();
    }
}
