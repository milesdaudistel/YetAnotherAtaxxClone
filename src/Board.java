/**
 * Created by miles on 5/24/17.
 */
public class Board {
    Piece[][] board; //contains the pieces
    int dim; //how large the board is
    int start; //first row index not part of left border
    int end; //row index where right border starts
    Piece current_player;

    public Board() {
        //create new board with default size and default border
        //init border to block, all else to empty, put 4 pieces in each corner

        dim = 10;
        start = 2;
        end = dim - start;
        board = new Piece[dim][dim];
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                if (i < start ||
                        j < start ||
                        i >= end ||
                        j >= end ) {
                    board[i][j] = Piece.BLOCK;
                } else {
                    board[i][j] = Piece.EMPTY;
                }
            }
        }

        board[start][start] = Piece.RED;
        board[start][end-1] = Piece.BLUE;
        board[end-1][start] = Piece.BLUE;
        board[end-1][end-1] = Piece.RED;

        current_player = Piece.RED;
    }

    public Board(String[] b) {
        //set everything to blocks, then reset the other ones later
        start = 2;
        //need to get rid of newline char?
        dim = b[0].length() + start * 2;
        end = dim - start;
        board = new Piece[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i][j] = Piece.BLOCK;
            }
        }

        //System.out.println("first row length: " + b[0].length());

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length(); j++) {
                if (b[i].charAt(j) == '-') {
                    set(j, i, Piece.EMPTY);
                } else if (b[i].charAt(j) == 'X') {
                    set(j, i, Piece.BLOCK);
                } else if (b[i].charAt(j) == 'R') {
                    set(j, i, Piece.RED);
                } else if (b[i].charAt(j) == 'B') {
                    set(j, i, Piece.BLUE);
                }
            }
        }

        current_player = Piece.RED;
    }

    public Board(Board original) {
        dim = original.dim;
        board = new Piece[dim][dim];
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                board[i][j] = original.board[i][j];
            }
        }
        start = original.start;
        end = original.end;
        current_player = original.current_player;
    }

    public Piece get(int x, int y) {
        //player sees board as nxn, when really its (n+2*border)(n+2*border)
        //return board[x+border][y+border] unless out of bounds
        return board[y+start][x+start];

    }

    private void set(int x, int y, Piece piece) {
        //player sees board as nxn, when really its (n+2*border)(n+2*border)
        //return board[x+border][y+border] unless out of bounds
        board[y+start][x+start] = piece;
    }

    public void print() {

        System.out.println("~~~~~~~");
        for (int i = start; i < end; i++) {
            String row = "";
            for (int j = start; j < end; j++) {
                if (board[i][j] == Piece.RED) {
                    row += "R";
                } else if (board[i][j] == Piece.BLUE) {
                    row += "B";
                } else if (board[i][j] == Piece.EMPTY) {
                    row += "-";
                } else {
                    row += "X";
                }
            }
            System.out.println(row);
        }
        System.out.println("~~~~~~~");

    }

    void update(Move move) {
        can_move(move);
        System.out.println("yes it can move");
        int tx = move.to.x;
        int ty = move.to.y;

        int fx = move.from.x;
        int fy = move.from.y;

        set(tx, ty, get(fx, fy));
        if (is_jump(move)) {
            set(fx, fy, Piece.EMPTY);
        }
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (get(tx+x, ty+y) == current_player.opposite()) {
                    set(tx+x, ty+y, current_player);
                }
            }
        }

        current_player = current_player.opposite();
    }

    boolean is_jump(Move move) {
        Coordinate dxdy = move.dxdy();
        if (Math.abs(dxdy.x) == 2 || Math.abs(dxdy.y) == 2) {
            return true;
        } else {
            return false;
        }
    }

    void can_move(Move move) {
        /**
         * piece must be the current players
         * both coordinates must be within bounds of board (taken care of in get method)
         * move.to must be empty
         * move cant be too long or too short
         */
        Coordinate dxdy = move.dxdy();
        int x = Math.abs(dxdy.x);
        int y = Math.abs(dxdy.y);
        if (!is_in_bounds(move.from.x, move.from.y)) {
            throw error("from index out of bounds");
        } else if (!is_in_bounds(move.to.x, move.to.y)) {
            throw error("to index out of bounds");
        } else if (get(move.from.x, move.from.y) != current_player) {
            throw error("that is not your piece");
        } else if (!valid_move_distance(move)) {
            throw error("move distance invalid");
        } else if (!(get(move.to.x, move.to.y) == Piece.EMPTY)) {
            throw error("that move destination is not empty");
        }
    }

    boolean is_in_bounds(int x, int y) {
        x = x + start;
        y = y + start;
        return x >= start && y >= start && x < end && y < end;
    }

    boolean valid_move_distance(Move move) {
        Coordinate dxdy = move.dxdy();
        int x = Math.abs(dxdy.x);
        int y = Math.abs(dxdy.y);
        return x >= 0 &&
                x <= 2 &&
                y >= 0 &&
                y <= 2 &&
                x + y > 0;
    }

    boolean game_over() {
        for (int i = start; i < end; i++) {
            for (int j = start; j < end; j++) {
                if (get(i, j) == Piece.EMPTY) {
                    for (int x = -2; x <= 2; x++) {
                        for (int y = -2; y <= 2; y++) {
                            if (get(i+x, j+y) == current_player) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
        //check if either player is unable to move
        //  meaning enclosed in a box, or there are no pieces of that color on the board
        //check that there are no empty squares
        //if any of this is true, tally total of red and blue, declare winner, return true
        //else return false
    }

    void declare_winner() {
        int red_count = 0;
        int blue_count = 0;
        for (int i = start; i < end; i++) {
            for (int j = start; j < end; j++) {
                if (board[i][j] == Piece.RED) {
                    red_count++;
                } else if (board[i][j] == Piece.BLUE) {
                    blue_count++;
                }
            }
        }
        System.out.println("Red count: " + red_count);
        System.out.println("Blue count: " + blue_count);
        if (red_count > blue_count) {
            System.out.println("Red wins");
        } else if (blue_count > red_count) {
            System.out.println("Blue wins");
        } else {
            System.out.println("Tie");
        }
    }

    MyException error(String message) {
        return new MyException(message);
    }

}
