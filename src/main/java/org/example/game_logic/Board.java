package org.example.game_logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Board implements Serializable, Cloneable {
    public abstract void generateBoard();
    public abstract void defineBases();
    public abstract void move(String start, String end) ;
    public abstract void showBoard();
    private Map<Agent, Set<Node>> Bases = new HashMap<>();
    private String[] nodes;
    public Map<Agent, Set<Node>> getBases() {
        return Bases;
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        return (Board) super.clone();
    }
}
