package org.example.client.GUI;

import org.example.game_logic.Board;
import org.example.game_logic.Coordinate;
import org.example.game_logic.Node;

/**
 * Class used to draw the board on the GUI. Can be inherited to create custom board drawing tools
 */
public class BoardDrawingTool {
    public BoardDrawingTool() {
    }

    /**
     * Draws the board on the boardPane
     * @param board the board to draw
     * @param boardPane the boardPane to draw the board on
     */
    public void drawBoard(Board board, BoardPane boardPane) {
        this.validateBoard(board, boardPane);
        this.updateBoard(board, boardPane);
    }

    /**
     * Validates that the boardPane contains all the nodes in the board
     * @param board the board to validate
     * @param boardPane the boardPane to validate
     */
    private void validateBoard(Board board, BoardPane boardPane) {
        for(Coordinate key : board.getNodes().keySet()) {
            if (!boardPane.getNodes().containsKey(key)) {
                this.initializeBoard(board, boardPane);
                break;
            }
        }

    }

    /**
     * Updates the board on the boardPane
     * @param board the board to update
     * @param boardPane the boardPane to update
     */
    private void updateBoard(Board board, BoardPane boardPane) {
        // Calculate the size of the board
        double size = Math.min(boardPane.getWidth(), boardPane.getHeight()) - (double)20.0F;
        // Calculate the offset of the pane to the window to get perfect centering
        double xOffset = (boardPane.getWidth() - size) / (double)2.0F;
        if( boardPane.getLayoutX() / 2 < xOffset) {
            xOffset -= boardPane.getLayoutX() / 2;
        }
        else {
            xOffset = 0;
        }
        double yOffset = (boardPane.getHeight() - size) / (double)2.0F;

        for(Coordinate key : board.getNodes().keySet()) {
            // Calculate the position of the node
            Node node = board.getNodes().get(key);
            NodeRepresentation nodeRepresentation = boardPane.getNodes().get(key);
            int elementsX = 25;
            int elementsY = 17;
            double x = (double)key.getX() * size / (double)elementsX;
            double y = (double)key.getY() * size / (double)elementsY;
            // Calculate the radius of the node
            double radius = Math.sqrt(Math.pow(size / (double)elementsX / 2.0F, 2.0F) + Math.pow(size / (double)elementsY / 2.0F, 2.0F)) - 4.0F;
            // Set the position and radius of the node
            nodeRepresentation.setPos(x + radius/2 + xOffset, y + radius/2 + yOffset);
            nodeRepresentation.updateNode(node);
            nodeRepresentation.setRadius(radius);
        }

    }

    /**
     * Initializes the board on the boardPane
     * @param board the board to initialize
     * @param boardPane the boardPane to initialize
     */
    private void initializeBoard(Board board, BoardPane boardPane) {
        boardPane.getChildren().clear();
        boardPane.getNodes().clear();

        for(Coordinate key : board.getNodes().keySet()) {
            NodeRepresentation node = new NodeRepresentation();
            boardPane.getNodes().put(key, node);
            boardPane.getChildren().add(node);
        }

    }
}
