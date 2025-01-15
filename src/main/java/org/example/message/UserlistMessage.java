package org.example.message;

/**
 * Message class used to send the userlist message between the server and the client
 */
public class UserlistMessage extends Message {
    // the list of users
    private final String[] message;

    public UserlistMessage(final String[] users)
    {
        super(MessageType.USERLIST);
        this.message = users;
    }

    public String[] getMessage()
    {
        return this.message;
    }

    public String toString()
    {
        return "Userlist: " + String.join(", ", message);
    }
}
