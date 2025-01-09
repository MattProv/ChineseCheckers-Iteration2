package org.example;

import java.util.ArrayList;

public final class TestBoard extends Board {
    private static final long serialVersionUID = 1L; // Opcjonalne, ale zalecane

    private ArrayList<String> moves = new ArrayList<>();
    private String lastMove = null;

    @Override
    public void generateBoard() {
        moves.clear();
    }

    @Override
    public void move(final String start, final String end) {
        String mv = start + " -> " + end;
        moves.add(mv);
        lastMove = mv;
        System.out.println("Move " + start + " to " + end);
    }

    @Override
    public void showBoard() {
        System.out.println("Last move: " + lastMove);
        System.out.println("MOVES:");
        for (String move : moves) {
            System.out.println(move);
        }
    }

    @Override
    public TestBoard clone() throws CloneNotSupportedException {
        TestBoard cloned = (TestBoard) super.clone();
        cloned.moves = new ArrayList<>(this.moves); // kopiujemy listę
        cloned.lastMove = this.lastMove; // kopiujemy ostatni ruch
        return cloned;
    }

}
