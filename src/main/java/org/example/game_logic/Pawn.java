package org.example.game_logic;

public class Pawn {
    int id;
    private Agent owner;
    private boolean isBaseLocked = false;
    private Node location;

    public Pawn(int id, Agent owner, Node location) {
        this.id = id;
        this.owner = owner;
        this.location = location;
    }

    public void updatePosition(Node newLocation){
        this.location = newLocation;
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

}