package org.example.server;

/**
 * Class representing a user connected and verified by the server.
 */
public class User {
    private final String username;
    private final ServerConnection connection;

    /**
     * Constructor for the User class.
     * @param username The username of the user.
     * @param connection The connection of the user.
     */
    public User(String username, ServerConnection connection) {
        this.username = username;
        this.connection = connection;
    }
    
    public String getUsername() {
        return username;
    }

    public ServerConnection getConnection() {
        return connection;
    }
}
