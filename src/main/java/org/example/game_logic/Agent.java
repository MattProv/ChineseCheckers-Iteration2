package org.example.game_logic;

import org.example.Player;

import java.awt.*;
import java.util.List;

public class Agent {
    int id;
    private Player owner;
    private List<Pawn> pawns;
    Color color;
    private int startBaseIndex;
    private int finishBaseIndex;

    private boolean isPlayer = false;

    public Agent(int id, int startBaseIndex, int finishBaseIndex, boolean isPlayer) {
        this.id = id;
        this.isPlayer = isPlayer;
    }

    public Agent(int id, int startBaseIndex, int finishBaseIndex) {
        this.id = id;
        this.owner = owner;
        this.startBaseIndex = startBaseIndex;
        this.finishBaseIndex = finishBaseIndex;
    }

    List<Pawn> getPawns() {
        return pawns;
    }

    void assignBases(int startBaseIndex, int finishBaseIndex) {
        this.startBaseIndex = startBaseIndex;
        this.finishBaseIndex = finishBaseIndex;
    }

    int getStartBaseIndex() {
        return this.startBaseIndex;
    }

    int getFinishBaseIndex() {
        return this.finishBaseIndex;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public Integer getId() {
        return id;
    }
}
