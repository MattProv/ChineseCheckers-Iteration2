package org.example.game_logic;

import java.io.Serializable;

public class Pawn implements Serializable, Cloneable {
    int id;
    private Agent owner;
    private boolean isBaseLocked = false;
    private Node location;

    public Pawn(int id, Agent owner, Node location) {
        this.id = id;
        this.owner = owner;
        this.owner.addPawn(this);
        this.location = location;
    }

    public void updatePosition(Node newLocation){

        this.location = newLocation;
        this.location.setOccupied(this);
        if (this.location.getBaseId() == this.getOwner().getFinishBaseIndex()){
            this.isBaseLocked = true;
        }

    }

    public void makeBaseLocked(){
        this.isBaseLocked = true;
    }

    public boolean isBaseLocked() {
        return isBaseLocked;
    }

    public Agent getOwner() {
        return owner;
    }

    @Override
    public Pawn clone() throws CloneNotSupportedException {
        Pawn pawn = (Pawn) super.clone();
        pawn.isBaseLocked = this.isBaseLocked;
        return pawn;
    }

}