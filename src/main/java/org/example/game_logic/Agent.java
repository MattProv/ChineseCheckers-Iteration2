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
    private boolean stepLocked = false;
    private boolean hopLocked = false;

    private boolean isPlayer = false;

    public Agent(int id, boolean isPlayer) {
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

    public boolean isStepLocked() {
        return stepLocked;
    }

    public void stepLock() {
        this.stepLocked = true;
    }

    public boolean isHopLocked() {
        return hopLocked;
    }

    public void hopLock() {
        this.hopLocked = true;
    }

    public void liftLocks() {
        this.stepLocked = false;
        this.hopLocked = false;
    }
}
