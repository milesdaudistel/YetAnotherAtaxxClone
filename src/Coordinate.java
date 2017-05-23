/**
 * Created by miles on 5/20/17.
 */
public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate dxdy(Coordinate to, Coordinate from) {
        return new Coordinate(to.x - from.x, to.y - from.y);
    }
}
