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

    public Agent(int id, Player owner) {
        this.id = id;
        this.owner = owner;
    }

    public Agent(int id, Player owner, int startBaseIndex, int finishBaseIndex) {
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
}
