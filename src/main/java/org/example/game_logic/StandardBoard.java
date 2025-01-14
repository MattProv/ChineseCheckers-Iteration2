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
        this.addNode(new Coordinate(12, 0));
        this.addNode(new Coordinate(11, 1)); this.addNode(new Coordinate(13, 1));
        for (int i = 10; i<=14; i+=2)
            this.addNode(new Coordinate(i, 2));
        for (int i = 9; i<=15; i+=2)
            this.addNode(new Coordinate(i, 3));
        for (int i = 0; i <= 24; i+=2)
            this.addNode(new Coordinate(i, 4));
        for (int i = 1; i<=23; i+=2)
            this.addNode(new Coordinate(i, 5));
        for (int i = 2; i<=22; i+=2)
            this.addNode(new Coordinate(i, 6));
        for (int i = 3; i<=21; i+=2)
            this.addNode(new Coordinate(i, 7));
        for (int i = 4; i<=20; i+=2)
            this.addNode(new Coordinate(i, 8));
        for (int i = 3; i<=21; i+=2)
            this.addNode(new Coordinate(i, 9));
        for (int i = 2; i<=22; i+=2)
            this.addNode(new Coordinate(i, 10));
        for (int i = 1; i<=23; i+=2)
            this.addNode(new Coordinate(i, 11));
        for (int i = 0; i <= 24; i+=2)
            this.addNode(new Coordinate(i, 12));
        for (int i = 9; i<=15; i+=2)
            this.addNode(new Coordinate(i, 13));
        for (int i = 10; i<=14; i+=2)
            this.addNode(new Coordinate(i, 14));
        this.addNode(new Coordinate(11, 15)); this.addNode(new Coordinate(13, 15));
        this.addNode(new Coordinate(12, 16));
    }

    @Override
    public void defineBases() {
        // BASE 0, counting clockwise from the bottom
        assignBaseToNode(new Coordinate(12,0),0);
        assignBaseToNode(new Coordinate(11,1),0);
        assignBaseToNode(new Coordinate(13,1),0);
        assignBaseToNode(new Coordinate(10,2),0);
        assignBaseToNode(new Coordinate(12,2),0);
        assignBaseToNode(new Coordinate(14,2),0);
        assignBaseToNode(new Coordinate(9,3),0);
        assignBaseToNode(new Coordinate(11,3),0);
        assignBaseToNode(new Coordinate(13,3),0);
        assignBaseToNode(new Coordinate(15,3),0);

        // BASE 1
        assignBaseToNode(new Coordinate(0,4),1);
        assignBaseToNode(new Coordinate(2,4),1);
        assignBaseToNode(new Coordinate(4,4),1);
        assignBaseToNode(new Coordinate(6,4),1);
        assignBaseToNode(new Coordinate(1,5),1);
        assignBaseToNode(new Coordinate(3,5),1);
        assignBaseToNode(new Coordinate(5,5),1);
        assignBaseToNode(new Coordinate(2,6),1);
        assignBaseToNode(new Coordinate(4,6),1);
        assignBaseToNode(new Coordinate(3,7),1);

        // BASE 2
        assignBaseToNode(new Coordinate(0,12),2);
        assignBaseToNode(new Coordinate(2,12),2);
        assignBaseToNode(new Coordinate(4,12),2);
        assignBaseToNode(new Coordinate(6,12),2);
        assignBaseToNode(new Coordinate(1,11),2);
        assignBaseToNode(new Coordinate(3,11),2);
        assignBaseToNode(new Coordinate(5,11),2);
        assignBaseToNode(new Coordinate(2,10),2);
        assignBaseToNode(new Coordinate(4,10),2);
        assignBaseToNode(new Coordinate(3,9),2);

        // BASE 3, counting clockwise from the bottom
        assignBaseToNode(new Coordinate(12,16),3);
        assignBaseToNode(new Coordinate(11,15),3);
        assignBaseToNode(new Coordinate(13,15),3);
        assignBaseToNode(new Coordinate(10,14),3);
        assignBaseToNode(new Coordinate(12,14),3);
        assignBaseToNode(new Coordinate(14,14),3);
        assignBaseToNode(new Coordinate(9,13),3);
        assignBaseToNode(new Coordinate(11,13),3);
        assignBaseToNode(new Coordinate(13,13),3);
        assignBaseToNode(new Coordinate(15,13),3);

        // BASE 4
        assignBaseToNode(new Coordinate(18,12),4);
        assignBaseToNode(new Coordinate(20,12),4);
        assignBaseToNode(new Coordinate(22,12),4);
        assignBaseToNode(new Coordinate(24,12),4);
        assignBaseToNode(new Coordinate(19,11),4);
        assignBaseToNode(new Coordinate(21,11),4);
        assignBaseToNode(new Coordinate(23,11),4);
        assignBaseToNode(new Coordinate(20,10),4);
        assignBaseToNode(new Coordinate(22,10),4);
        assignBaseToNode(new Coordinate(21,9),4);

        // BASE 5
        assignBaseToNode(new Coordinate(18,4),5);
        assignBaseToNode(new Coordinate(20,4),5);
        assignBaseToNode(new Coordinate(22,4),5);
        assignBaseToNode(new Coordinate(24,4),5);
        assignBaseToNode(new Coordinate(19,5),5);
        assignBaseToNode(new Coordinate(21,5),5);
        assignBaseToNode(new Coordinate(23,5),5);
        assignBaseToNode(new Coordinate(20,6),5);
        assignBaseToNode(new Coordinate(22,6),5);
        assignBaseToNode(new Coordinate(21,7),5);
    }

    @Override
    public void defineNeighbours() {
        for (Node node : getNodes().values()) {
            int x = node.getXCoordinate();
            int y = node.getYCoordinate();
            node.addNeighbour(getNode(new Coordinate(x+2, y)));
            node.addNeighbour(getNode(new Coordinate(x-2, y)));
            node.addNeighbour(getNode(new Coordinate(x+1, y+1)));
            node.addNeighbour(getNode(new Coordinate(x-1, y+1)));
            node.addNeighbour(getNode(new Coordinate(x+1, y-1)));
            node.addNeighbour(getNode(new Coordinate(x-1, y-1)));
        }
    }

    @Override
    public void move(final Move move) {
        if (this.getPawn(this.getNodes().get(move.getStart())) == null) {
            throw new IllegalStateException("No pawn at the starting node!");
        }
        this.getPawn(this.getNodes().get(move.getStart())).updatePosition(move.getEnd());
        moves.add(move);
        lastMove = move.getStart() + " -> " + move.getEnd();
        System.out.println("Move " + lastMove);
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