package org.example.message.clientHandlers;

import org.example.client.GUI.LobbyScreen;
import org.example.message.MessageHandler;
import org.example.message.MessageSenderPair;
import org.example.message.MessageType;

public class StringMessageGUIHandler extends MessageHandler {

    private final LobbyScreen lobbyScreen;

    public StringMessageGUIHandler(final LobbyScreen lobbyScreen) {
        super(MessageType.STRING);
        this.lobbyScreen = lobbyScreen;
    }

    @Override
    public void handle(MessageSenderPair message) {
        lobbyScreen.showServerMessage(message.getMessage().toString());
    }
}
