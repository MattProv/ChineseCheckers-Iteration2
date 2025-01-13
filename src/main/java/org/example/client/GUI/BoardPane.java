//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.client.GUI;

import javafx.scene.layout.Pane;
import org.example.game_logic.Board;
import org.example.game_logic.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class BoardPane extends Pane {
    private Map<Coordinate, NodeRepresentation> nodes = new HashMap();
    private final BoardDrawingTool boardDrawingTool = new BoardDrawingTool();

    public BoardPane() {
    }

    public void updateBoard(Board board) {
        this.boardDrawingTool.drawBoard(board, this);
    }

    public Map<Coordinate, NodeRepresentation> getNodes() {
        return this.nodes;
    }
}
