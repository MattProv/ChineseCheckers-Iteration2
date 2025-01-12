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
    private int endBaseIndex;

    List<Pawn> getPawns() {
        return pawns;
    }
}
