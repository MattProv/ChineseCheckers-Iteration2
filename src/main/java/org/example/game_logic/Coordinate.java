
package org.example.game_logic;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class used to represent a coordinate on the board
 */
public class Coordinate implements Serializable {
    // the x and y coordinates
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Coordinate that = (Coordinate)o;
            return this.x == that.x && this.y == that.y;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.x, this.y});
    }
}
