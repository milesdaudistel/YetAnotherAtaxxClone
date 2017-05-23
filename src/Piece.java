/**
 * Created by miles on 5/20/17.
 */
public enum Piece {
    RED,
    BLUE,
    BLOCK,
    EMPTY;

    public Piece opposite() {
        //if this is red, return blue
        //if this is blue, return red
        //else, return null
        if (this == RED) {
            return BLUE;
        } else if (this == BLUE) {
            return RED;
        } else {
            return null;
        }
    }
}
