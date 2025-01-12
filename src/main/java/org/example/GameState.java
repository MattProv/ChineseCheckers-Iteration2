package org.example;

import org.example.game_logic.Board;
import org.example.game_logic.Rules;

import java.io.Serializable;

public final class GameState implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private Board board;
    private boolean isRunning;
    public GameState(final Board board, final boolean isRunning) {
        this.board = board;
        this.isRunning = isRunning;
    }

    public GameState() {
        this.board = null;
        this.isRunning = false;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setState(final GameState state) {
        this.isRunning = state.isRunning;
        this.board = state.board;
    }

    public void setRunning(final boolean b) {
        this.isRunning = b;
    }

    public void setBoard(final Board board) {
        this.board = board;
    }

    @Override
    public GameState clone() {
        try {
            GameState cloned = (GameState) super.clone();
            if (this.board != null) {
                cloned.board = (Board) this.board.clone(); // Ensure Board implements Cloneable
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
