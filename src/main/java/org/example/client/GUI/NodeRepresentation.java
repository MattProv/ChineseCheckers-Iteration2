package org.example.client.GUI;

import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import org.example.game_logic.Node;

public class NodeRepresentation extends Ellipse {

    private double x = 0;
    private double y = 0;

    boolean hovered = false;
    boolean occupied = false;

    Paint baseColor;

    public NodeRepresentation() {
        this.setRadiusX(5.0F);
        this.setRadiusY(5.0F);
        this.setStrokeWidth(3.0F);
        this.setStroke(Paint.valueOf("white"));
    }

    private void redrawNode() {
        Platform.runLater(() -> {
            if (this.hovered) {
                this.setStroke(Paint.valueOf("yellow"));
            } else {
                this.setStroke(baseColor);
            }

            if (!occupied) {
                this.setFill(null);
            } else {
                // TODO: set fill color when node is occupied
            }
        });

    }

    public void updateNode(Node node) {
        occupied = node.getIsOccupied();

        baseColor = this.getBaseColor(node.getBaseId());
        redrawNode();
    }

    private Paint getBaseColor(int baseId) {
        switch (baseId) {
            case 0 -> {
                return Paint.valueOf("red");
            }
            case 1 -> {
                return Paint.valueOf("blue");
            }
            case 2 -> {
                return Paint.valueOf("green");
            }
            case 3 -> {
                return Paint.valueOf("yellow");
            }
            case 4 -> {
                return Paint.valueOf("purple");
            }
            case 5 -> {
                return Paint.valueOf("orange");
            }
            default -> {
                return Paint.valueOf("white");
            }
        }
    }

    public void setPos(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);

        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setRadius(double r) {
        double strokeWidth = r / (double)5.0F;
        this.setRadiusX(r - strokeWidth);
        this.setRadiusY(r - strokeWidth);
        this.setStrokeWidth(strokeWidth);
    }

    public boolean isHit(double x, double y) {
        return this.getBoundsInParent().contains(x, y);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
        redrawNode();
    }
}
