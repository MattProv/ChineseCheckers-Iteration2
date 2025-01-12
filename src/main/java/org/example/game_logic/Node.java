package org.example.game_logic;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable {
    private List<Node> neighbours;
    private int[] coordinates = new int[2];
    private boolean isOccupied;
    //private Pawn occupant;
    private int baseId = -1;

    public Node(int xCoordinate, int yCoordinate) {
        this.coordinates[0] = xCoordinate;
        this.coordinates[1] = yCoordinate;
        isOccupied = false;
    }

    public Node(int xCoordinate, int yCoordinate, int baseId) {
        this.coordinates[0] = xCoordinate;
        this.coordinates[1] = yCoordinate;
        this.baseId = baseId;
    }

    public void assignBase(int baseId) {
        if (this.baseId != -1) {
            System.out.println("Attempted to reassign a base ID: node already assigned base id: " + this.baseId);
            return;
        }
        else
            this.baseId = baseId;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setOccupied() {
        this.isOccupied = true;
        //this.occupant = occupant;
    }

    public void setUnoccupied(Pawn occupant) {
        //if (this.occupant != occupant) {
        //    System.out.println("Pawn tried to free a node that wasn't occupied by it!");
        //}
        this.isOccupied = false;
        //this.occupant = null;
    }

    public void addNeighbour(Node neighbour) {
        if (neighbour != null) {
            neighbours.add(neighbour);
        }
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

    public int getBaseId() {
        return baseId;
    }

    public int getXCoordinate() {
        return coordinates[0];
    }
    public int getYCoordinate() {
        return coordinates[1];
    }

    public String printCoordinates() {
        return "(" + coordinates[0] + "," + coordinates[1] + ")";
    }
}
