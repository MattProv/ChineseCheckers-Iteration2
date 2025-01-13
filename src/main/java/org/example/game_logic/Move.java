package org.example.game_logic;

public class Move {
    private Node startPosition;
    private Node endPosition;

    public Move(Node startPosition, Node endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;

    }

    public Node getStart(){
        return startPosition;
    }

    public Node getEnd(){
        return endPosition;
    }
}
