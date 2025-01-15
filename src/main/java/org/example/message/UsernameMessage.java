package org.example.message;

/**
 * Message class used to verify the username of the client with the server
 */
public class UsernameMessage extends Message {
    private final String username;

    public UsernameMessage(final String username) {
        super(MessageType.USERNAME);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return username;
    }
}
