package org.example.game_logic;

import org.example.Player;

import java.util.List;

public interface Rules<T extends Board> {
    boolean validatePlayerCount(int playerCount);
    boolean validateMove(T board, Pawn pawn, Move move);
    default boolean checkWinCondition(Agent agent) {
        for (Pawn pawn : agent.getPawns()) {
            if (!pawn.isBaseLocked())
                return false;
        }
        return true;
    }
    void assignBasesToAgents(T board, List<Agent> agents);
    T setupBoard(T board);
}
