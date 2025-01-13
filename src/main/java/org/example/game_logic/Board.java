package org.example.game_logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Board implements Serializable, Cloneable {
    public abstract void generateBoard();
    public abstract void defineBases();
    public abstract void move(String start, String end) ;
    public abstract void showBoard();
    private Map<Coordinate, Node> Nodes = new HashMap<>();
    private Map<Integer, Set<Node>> Bases;
    private Map<Node, Pawn> Pawns;

    public Map<Integer, Set<Node>> getBases() {
        return Bases;
    }

    public Map<Coordinate, Node> getNodes() {
        return Nodes;
    }

    public Node getNode(Coordinate coordinate) {
        return Nodes.get(coordinate);
    }

    public void addNode(Coordinate coordinate) {
        Nodes.put(coordinate, new Node(coordinate.getX(), coordinate.getY()));
    }

    public void addPawn(Coordinate coordinate, Agent owner) {
        Pawns.put(getNode(coordinate), new Pawn(owner, getNode(coordinate)));
        getNode(coordinate).setOccupied();
    }

    protected void assignBaseToNode(Coordinate coordinate, int baseId) {
        Nodes.get(coordinate).assignBase(baseId);
        if (Bases.containsKey(baseId)) {
            Bases.get(baseId).add(Nodes.get(coordinate));
        }
        else {
            Set<Node> nodes = new HashSet<>();
            nodes.add(new Node(coordinate.getX(), coordinate.getY()));
            Bases.put(baseId, nodes);
        }
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        return (Board) super.clone();
    }
}
