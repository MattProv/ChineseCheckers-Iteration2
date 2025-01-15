package org.example.message;

import org.example.server.Server;
import org.example.server.ServerConnection;

/**
 * Message handler for echoing any string messages back to the sender.
 */
public final class EchoMessageHandler extends MessageHandler {
    public EchoMessageHandler() {
        super(MessageType.STRING);
    }

    @Override
    public void handle(final MessageSenderPair message) {
        StringMessage stringMessage = (StringMessage) message.getMessage();
        ServerConnection sc = message.getConnection();

        System.out.println(stringMessage.getMessage());
        Server.getServer().Send(stringMessage, sc);
    }
}
