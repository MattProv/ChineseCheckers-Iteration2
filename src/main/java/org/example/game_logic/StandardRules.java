package org.example.game_logic;

import java.util.Arrays;

public class StandardRules implements Rules<StandardBoard> {

    @Override
    public boolean validateMove(StandardBoard board, Pawn pawn, Move move) {
        // A pawn can't leave it's base after it enters it
        if (pawn.isBaseLocked())
            if (move.getEnd().getBaseOwner() != pawn.getOwner())
                return false;

        // A pawn can move to an empty neighbouring node
        if (move.getStart().getNeighbours().contains(move.getEnd()))
            if (move.getEnd().getIsOccupied())
                return false;
            else
                return true;

        // A pawn can hop over a neighbouring pawn
        //TODO(Wiktor): Implement after implementing the map

        // For everything else, discard as an invalid move
        return false;
    }

    @Override
    public StandardBoard setupBoard(StandardBoard board) {
        //TODO(Wiktor): Implement after implementing the map
        return board;
    }

}
