package org.example.server;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private ServerConnection connection;

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
