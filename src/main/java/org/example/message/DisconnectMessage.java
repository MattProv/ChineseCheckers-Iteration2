package org.example.message;

/**
 * Message class used to send the disconnect message between the server and the client
 */
public final class DisconnectMessage extends Message{
    public DisconnectMessage(){
        super(MessageType.DISCONNECT);
    }

    public String toString(){
        return "DISCONNECT";
    }
}
