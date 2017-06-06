/**
 * Created by miles on 5/20/17.
 */
public class Coordinate {
    int x;
    int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static Coordinate dxdy(Coordinate to, Coordinate from) {
        return new Coordinate(to.x - from.x, to.y - from.y);
    }

    boolean equals(Coordinate other) {
        return x == other.x && y == other.y;
    }
}
