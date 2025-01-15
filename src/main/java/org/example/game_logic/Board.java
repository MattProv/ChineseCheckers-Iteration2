package org.example.game_logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Board implements Serializable, Cloneable {
    public abstract void generateBoard();
    public abstract void defineBases();
    public abstract void defineNeighbours();
    public abstract void move(Move move) ;
    public abstract void showBoard();
    private Map<Coordinate, Node> Nodes = new HashMap<>();
    private Map<Integer, Set<Node>> Bases = new HashMap<>();
    private Map<Node, Pawn> Pawns = new HashMap<>();


    public Map<Integer, Set<Node>> getBases() {
        return Bases;
    }

    public Map<Coordinate, Node> getNodes() {
        return Nodes;
    }

    public Node getNode(Coordinate coordinate) {
        if (Nodes.containsKey(coordinate)) {
            return Nodes.get(coordinate);
        }
        return null;
    }

    public void addNode(Coordinate coordinate) {
        Nodes.put(coordinate, new Node(coordinate.getX(), coordinate.getY()));
    }

    public void addPawn(Coordinate coordinate, Agent owner) {
        Node node = getNode(coordinate); // Ensure this retrieves the correct node
        Pawn pawn = new Pawn(Pawns.size() + 1, owner, node);
        Pawns.put(node, pawn);           // Add pawn to the map
        node.setOccupied(pawn);              // Mark the node as occupied
    }

    public void addPawn(Node node, Agent owner) {
        Pawns.put(node, new Pawn(Pawns.size()+1 ,owner, node));
        node.setOccupied(Pawns.get(node));
    }

    public Pawn getPawn(Node node) {
        return Pawns.get(node);
    }

    public void updatePawnPosition(Node start, Node end) {
        Pawn pawn = Pawns.get(start);
        start.setUnoccupied(pawn);
        Pawns.remove(start);
        Pawns.put(end, pawn);
        end.setOccupied(pawn);
    }

    protected void assignBaseToNode(Coordinate coordinate, int baseId) {
        Nodes.get(coordinate).assignBase(baseId);
        if (Bases.containsKey(baseId)) {
            Bases.get(baseId).add(Nodes.get(coordinate));
        } else {
            Set<Node> nodes = new HashSet<>();
            nodes.add(Nodes.get(coordinate)); // Use the existing node
            Bases.put(baseId, nodes);
        }
    }


    @Override
    public Board clone() throws CloneNotSupportedException {
        Board cloned = (Board) super.clone();
        cloned.Pawns = new HashMap<>();
        for (Map.Entry<Node, Pawn> entry : this.Pawns.entrySet()) {
            cloned.Pawns.put(entry.getKey().clone(), entry.getValue().clone());
        }

        // Deep copy of Nodes map
        cloned.Nodes = new HashMap<>();
        for (Map.Entry<Coordinate, Node> entry : this.Nodes.entrySet()) {
            cloned.Nodes.put(entry.getKey(), entry.getValue().clone());
        }

        // Deep copy of Bases map
        cloned.Bases = new HashMap<>();
        for (Map.Entry<Integer, Set<Node>> entry : this.Bases.entrySet()) {
            Set<Node> clonedSet = new HashSet<>();
            for (Node node : entry.getValue()) {
                clonedSet.add(node.clone());
            }
            cloned.Bases.put(entry.getKey(), clonedSet);
        }
        return cloned;
    }
}