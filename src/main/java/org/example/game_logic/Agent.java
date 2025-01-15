package org.example.game_logic;

import org.example.SerializableColor;

import java.io.Serializable;
import java.util.List;

public class Agent implements Serializable{
    int id;
    private List<Pawn> pawns;
    SerializableColor color;
    private int startBaseIndex;
    private int finishBaseIndex;
    private boolean stepLocked = false;
    private boolean hopLocked = false;
    private Pawn currentPawn = null;

    private boolean isPlayer = false;

    public Agent(int id, boolean isPlayer) {
        this.id = id;
        this.isPlayer = isPlayer;

        this.color = new SerializableColor((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
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

    public int getFinishBaseIndex() {
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

    public void setCurrentPawn(Pawn currentPawn) {
        this.currentPawn = currentPawn;
    }

    public Pawn getCurrentPawn() {
        return currentPawn;
    }

    public void liftLocks() {
        this.stepLocked = false;
        this.hopLocked = false;
        this.currentPawn = null;
    }

    public void promptMove(Board board) {

    }

    public void setColor(SerializableColor color) {
        this.color = color;
    }

    public SerializableColor getColor() {
        return color;
    }
}
