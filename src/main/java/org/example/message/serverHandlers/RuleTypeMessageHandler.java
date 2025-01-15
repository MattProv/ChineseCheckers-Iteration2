package org.example.message.serverHandlers;

import org.example.message.MessageHandler;
import org.example.message.MessageSenderPair;
import org.example.message.MessageType;
import org.example.message.RulesTypeMessage;
import org.example.server.GameManager;

/**
 * Message handler used to change the ruleset of the game
 */
public class RuleTypeMessageHandler extends MessageHandler {
    final GameManager gameManager;
    public RuleTypeMessageHandler(final GameManager gm){
        super(MessageType.RULES_TYPE);

        gameManager = gm;
    }

    @Override
    public void handle(MessageSenderPair message) {
        RulesTypeMessage rulesTypeMessage = (RulesTypeMessage) message.getMessage();
        gameManager.setRuleset(rulesTypeMessage.getRulesetType().createRules());
    }
}
