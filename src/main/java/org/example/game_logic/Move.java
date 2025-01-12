package org.example.game_logic;

public class Move {
    Node startPosition;
    Node endPosition;
    Pawn pawn;

    Move(Node startPosition, Node endPosition, Pawn pawn) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.pawn = pawn;
    }

    Node getStart(){
        return startPosition;
    }

    Node getEnd(){
        return endPosition;
    }
}
