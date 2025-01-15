package org.example;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Serializable class representing a color.
 */
public class SerializableColor implements Serializable {

    public int red;
    public int green;
    public int blue;

    /**
     * Constructor for the SerializableColor class.
     * @param red The red component of the color.
     * @param green The green component of the color.
     * @param blue The blue component of the color.
     */
    public SerializableColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;

    }

    /**
     * Returns the color represented by the SerializableColor object in JavaFX format.
     * @return The color represented by the SerializableColor object in JavaFX format.
     */
    public Color getColor() {
        return Color.rgb(red, green, blue);
    }
}
