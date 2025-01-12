package org.example.game_logic;

import java.io.Serializable;
import java.util.*;

public abstract class Board implements Serializable, Cloneable {
    public abstract void generateBoard();
    public abstract void defineBases();
    public abstract void move(String start, String end) ;
    public abstract void showBoard();
    private Map<int[], Node> Nodes = new HashMap<>();
    private Map<Integer, Set<Node>> Bases;
    private Map<Node, Pawn> Pawns;

    public Map<Integer, Set<Node>> getBases() {
        return Bases;
    }

    public Map<int[], Node> getNodes() {
        return Nodes;
    }

    public Node getNode(int x, int y) {
        return Nodes.get(new int[] {x, y});
    }

    public void addNode(int x, int y) {
        Nodes.put(new int[] {x,y}, new Node(x, y));
    }

    public void addPawn(int x, int y, Agent owner) {
        Pawns.put(getNode(x, y), new Pawn(owner, getNode(x, y)));
        getNode(x,y).setOccupied();
    }

    protected void assignBaseToNode(int x, int y, int baseId) {
        Nodes.get(new int[] {x, y}).assignBase(baseId);
        if (Bases.containsKey(baseId)) {
            Bases.get(baseId).add(Nodes.get(new int[] {x, y}));
        }
        else {
            Set<Node> nodes = new HashSet<>();
            nodes.add(new Node(x, y));
            Bases.put(baseId, nodes);
        }
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        return (Board) super.clone();
    }
}
