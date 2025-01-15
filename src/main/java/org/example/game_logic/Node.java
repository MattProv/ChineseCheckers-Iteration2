package org.example.game_logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Serializable {
    private List<Node> neighbours = new ArrayList<>();
    private Coordinate coordinate;
    private boolean isOccupied;
    private Pawn occupant = null;
    private int baseId = -1;

    public Node(int xCoordinate, int yCoordinate) {
        this.coordinate = new Coordinate(xCoordinate, yCoordinate);
        isOccupied = false;
    }

    public Node(int xCoordinate, int yCoordinate, int baseId) {
        this.coordinate = new Coordinate(xCoordinate, yCoordinate);
        this.isOccupied = false;
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

    public void setOccupied(Pawn pawn) {
        this.isOccupied = true;
        this.occupant = pawn;
    }

    public void setUnoccupied(Pawn occupant) {
        if (this.occupant != occupant) {
           return;
        }
        this.isOccupied = false;
        this.occupant = null;
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

    public Pawn getOccupant() {
        return occupant;
    }

    public int getBaseId() {
        return baseId;
    }

    public int getXCoordinate() {
        return coordinate.getX();
    }
    public int getYCoordinate() {
        return coordinate.getY();
    }

    public String printCoordinates() {
        return "(" + coordinate.getX() + "," + coordinate.getY() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(coordinate, node.coordinate); // Compare using coordinate object
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate); // Hash using the coordinate object
    }


}
