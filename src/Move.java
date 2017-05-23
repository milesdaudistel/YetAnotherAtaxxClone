/**
 * Created by miles on 5/20/17.
 */
public class Move {
    public Coordinate to;
    public Coordinate from;

    public Move(int x1, int y1, int x2, int y2) {
        this.to = new Coordinate(x1, y1);
        this.from = new Coordinate(x2, y2);
    }

    public Move(Coordinate to, Coordinate from) {
        this.to = to;
        this.from = from;
    }

    public Coordinate dxdy() {
        return new Coordinate(this.to.x - this.from.x, this.to.y - this.from.y);
    }
}
