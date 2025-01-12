package org.example.game_logic;

public interface Rules<T extends Board> {
    boolean validateMove(T board, Pawn pawn, Move move);
    default boolean checkWinCondition(Agent agent) {
        for (Pawn pawn : agent.getPawns()) {
            if (!pawn.isBaseLocked())
                return false;
        }
        return true;
    }
    T setupBoard(T board);

    StandardBoard setupBoard(StandardBoard board);
}
