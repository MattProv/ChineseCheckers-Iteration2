package org.example;

import java.io.Serializable;

public abstract class Board implements Serializable, Cloneable {
    public abstract void generateBoard();
    public abstract void move(String start, String end);
    public abstract void showBoard();

    @Override
    public Board clone() throws CloneNotSupportedException {
        return (Board) super.clone();
    }
}
