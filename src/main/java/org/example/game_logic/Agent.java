package org.example.game_logic;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class Agent implements Serializable{
    int id;
    private List<Pawn> pawns;
    Color color;
    private int startBaseIndex;
    private int finishBaseIndex;

    private boolean isPlayer = false;

    public Agent(int id, boolean isPlayer) {
        this.id = id;
        this.isPlayer = isPlayer;
    }

    public Agent(int id, int startBaseIndex, int finishBaseIndex) {
        this.id = id;
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

    public void promptMove(Board board) {

    }
}
