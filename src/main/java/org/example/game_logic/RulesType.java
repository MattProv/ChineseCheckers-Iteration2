package org.example.game_logic;

public enum RulesType {
    STANDARD{
        @Override
        public Rules createRules() {
            return new StandardRules();
        }

        @Override
        public String toString() {
            return "Standard";
        }
    };

    public abstract Rules createRules();
}
