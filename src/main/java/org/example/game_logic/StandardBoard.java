package org.example.game_logic;

import java.io.Serializable;
import java.util.ArrayList;

public final class StandardBoard extends Board implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L; // Opcjonalne, ale zalecane

    private ArrayList<Move> moves = new ArrayList<>();
    private String lastMove = null;

    @Override
    public void generateBoard() {
        moves.clear();
        // Creating the field
        this.addNode(12, 0);
        this.addNode(11, 1); this.addNode(13, 1);
        for (int i = 10; i<=14; i+=2)
            this.addNode(i, 2);
        for (int i = 9; i<=15; i+=2)
            this.addNode(i, 3);
        for (int i = 0; i <= 24; i+=2)
            this.addNode(i, 4);
        for (int i = 1; i<=23; i+=2)
            this.addNode(i, 5);
        for (int i = 2; i<=22; i+=2)
            this.addNode(i, 6);
        for (int i = 3; i<=21; i+=2)
            this.addNode(i, 7);
        for (int i = 4; i<=20; i+=2)
            this.addNode(i, 8);
        for (int i = 3; i<=21; i+=2)
            this.addNode(i, 9);
        for (int i = 2; i<=22; i+=2)
            this.addNode(i, 10);
        for (int i = 1; i<=23; i+=2)
            this.addNode(i, 11);
        for (int i = 0; i <= 24; i+=2)
            this.addNode(i, 12);
        for (int i = 9; i<=15; i+=2)
            this.addNode(i, 13);
        for (int i = 10; i<=14; i+=2)
            this.addNode(i, 14);
        this.addNode(11, 15); this.addNode(13, 16);
        this.addNode(12, 16);
    }

    @Override
    public void defineBases() {
        // BASE 0, counting clockwise from the bottom
        assignBaseToNode(12,0,0);
        assignBaseToNode(11,1,0);
        assignBaseToNode(13,1,0);
        assignBaseToNode(10,2,0);
        assignBaseToNode(12,2,0);
        assignBaseToNode(14,2,0);
        assignBaseToNode(9,3,0);
        assignBaseToNode(11,3,0);
        assignBaseToNode(13,3,0);
        assignBaseToNode(15,3,0);

        // BASE 1
        assignBaseToNode(0,4,1);
        assignBaseToNode(2,4,1);
        assignBaseToNode(4,4,1);
        assignBaseToNode(6,4,1);
        assignBaseToNode(1,5,1);
        assignBaseToNode(3,5,1);
        assignBaseToNode(5,5,1);
        assignBaseToNode(2,6,1);
        assignBaseToNode(4,6,1);
        assignBaseToNode(3,7,1);

        // BASE 2
        assignBaseToNode(0,12,2);
        assignBaseToNode(2,12,2);
        assignBaseToNode(4,12,2);
        assignBaseToNode(6,12,2);
        assignBaseToNode(1,11,2);
        assignBaseToNode(3,11,2);
        assignBaseToNode(5,11,2);
        assignBaseToNode(2,10,2);
        assignBaseToNode(4,10,2);
        assignBaseToNode(3,9,2);

        // BASE 3, counting clockwise from the bottom
        assignBaseToNode(12,16,3);
        assignBaseToNode(11,15,3);
        assignBaseToNode(13,15,3);
        assignBaseToNode(10,14,3);
        assignBaseToNode(12,14,3);
        assignBaseToNode(14,14,3);
        assignBaseToNode(9,13,3);
        assignBaseToNode(11,13,3);
        assignBaseToNode(13,13,3);
        assignBaseToNode(15,13,3);

        // BASE 4
        assignBaseToNode(18,12,4);
        assignBaseToNode(20,12,4);
        assignBaseToNode(22,12,4);
        assignBaseToNode(24,12,4);
        assignBaseToNode(19,11,4);
        assignBaseToNode(21,11,4);
        assignBaseToNode(23,11,4);
        assignBaseToNode(20,10,4);
        assignBaseToNode(22,10,4);
        assignBaseToNode(21,9,4);

        // BASE 5
        assignBaseToNode(18,4,5);
        assignBaseToNode(20,4,5);
        assignBaseToNode(22,4,5);
        assignBaseToNode(24,4,5);
        assignBaseToNode(19,5,5);
        assignBaseToNode(21,5,5);
        assignBaseToNode(23,5,5);
        assignBaseToNode(20,6,5);
        assignBaseToNode(22,6,5);
        assignBaseToNode(21,7,5);
    }

    @Override
    public void move(final Move move) {
        String mv = move.getStart() + " -> " + move.getEnd();
        moves.add(move);
        lastMove = mv;
        System.out.println("Move " + move.getStart() + " to " + move.getEnd());
    }

    @Override
    public void showBoard() {
        System.out.println("Last move: " + lastMove);
        System.out.println("MOVES:");
        for (Move move : moves) {
            System.out.println("Move " + move.getStart() + " to " + move.getEnd());
        }
    }

    @Override
    public StandardBoard clone() throws CloneNotSupportedException {
        StandardBoard cloned = (StandardBoard) super.clone();
        cloned.moves = new ArrayList<>(this.moves); // kopiujemy listę
        cloned.lastMove = this.lastMove; // kopiujemy ostatni ruch
        return cloned;
    }

}
