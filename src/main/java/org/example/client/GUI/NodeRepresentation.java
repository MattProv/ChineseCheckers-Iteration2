//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.client.GUI;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import org.example.game_logic.Node;

public class NodeRepresentation extends Ellipse {
    public NodeRepresentation() {
        this.setRadiusX((double)5.0F);
        this.setRadiusY((double)5.0F);
        this.setStrokeWidth((double)3.0F);
        this.setStroke(Paint.valueOf("white"));
        this.setOnMouseClicked((e) -> System.out.println("Node clicked"));
    }

    public void updateNode(Node node) {
        if (!node.getIsOccupied()) {
            this.setFill((Paint)null);
        }

        if (node.getBaseId() != -1) {
            switch (node.getBaseId()) {
                case 0 -> this.setStroke(Paint.valueOf("red"));
                case 1 -> this.setStroke(Paint.valueOf("blue"));
                case 2 -> this.setStroke(Paint.valueOf("green"));
                case 3 -> this.setStroke(Paint.valueOf("yellow"));
                case 4 -> this.setStroke(Paint.valueOf("purple"));
                case 5 -> this.setStroke(Paint.valueOf("orange"));
                default -> this.setStroke(Paint.valueOf("white"));
            }
        }

    }

    public void setPos(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void setRadius(double r) {
        this.setRadiusX(r);
        this.setRadiusY(r);
        this.setStrokeWidth(r / (double)5.0F);
    }

    public boolean isHit(double x, double y) {
        return this.getBoundsInParent().contains(x, y);
    }
}
