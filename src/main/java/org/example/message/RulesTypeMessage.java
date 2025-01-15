package org.example.message;

import org.example.game_logic.RulesType;

/**
 * Message class used to send the ruleset type between the server and the client
 */
public class RulesTypeMessage extends Message {
    private final RulesType rulesetType;

    public RulesTypeMessage(RulesType rulesetType) {
        super(MessageType.RULES_TYPE);
        this.rulesetType = rulesetType;
    }

    public RulesType getRulesetType() {
        return rulesetType;
    }

    @Override
    public String toString() {
        return "rulesetType='" + rulesetType;
    }
}
