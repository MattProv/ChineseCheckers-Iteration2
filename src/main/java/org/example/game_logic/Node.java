package org.example.game_logic;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable {
    private List<Node> neighbours;
    private int[] coordinates = new int[2];
    private boolean isOccupied;
    private Pawn occupant;
    private Agent BaseOwner;

    public Node(int xCoordinate, int yCoordinate) {
        this.coordinates[0] = xCoordinate;
        this.coordinates[1] = yCoordinate;
        isOccupied = false;
    }

    public Node(int xCoordinate, int yCoordinate, Agent BaseOwner) {
        this.coordinates[0] = xCoordinate;
        this.coordinates[1] = yCoordinate;
        this.BaseOwner = BaseOwner;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setOccupied(Pawn occupant) {
        this.isOccupied = true;
        this.occupant = occupant;
    }

    public void setUnoccupied(Pawn occupant) {
        if (this.occupant != occupant) {
            System.out.println("Pawn tried to free a node that wasn't occupied by it!");
        }
        this.isOccupied = false;
        this.occupant = null;
    }

    public void addNeighbour(Node neighbour) {
        neighbours.add(neighbour);
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public boolean hasOccupiedNeighbours() {
        for (Node neighbour : neighbours) {
            if (neighbour.getIsOccupied()) {
                return true;
            }
        }
        return false;
    }

    public Agent getBaseOwner() {
        return BaseOwner;
    }
}
