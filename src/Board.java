/**
 * Created by miles on 5/24/17.
 */
import java.util.Stack;

public class Board {
    private Piece[][] board; //contains the pieces
    private Stack<Piece[][]> boards;
    private int side; //the length of the playable board
    private Piece whoseturn;

    Board() {
        init_board(8);

        whoseturn = Piece.RED;
    }

    Board(int board_length) {
        init_board(board_length);

        whoseturn = Piece.RED;
    }

    Board(String[] b) {
        side = b[0].length();

        board = new Piece[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                board[i][j] = Piece.BLOCK;
            }
        }

        for (int j = 0; j < b.length; j++) {
            for (int i = 0; i < b[j].length(); i++) {
                if (b[j].charAt(i) == '-') {
                    set(i, j, Piece.EMPTY);
                } else if (b[j].charAt(i) == 'X') {
                    set(i, j, Piece.BLOCK);
                } else if (b[j].charAt(i) == 'R') {
                    set(i, j, Piece.RED);
                } else if (b[j].charAt(i) == 'B') {
                    set(i, j, Piece.BLUE);
                }
            }
        }

        whoseturn = Piece.RED;
    }

    Board(Board original) {
        side = original.side;
        whoseturn = original.whoseturn;
        board = matrix_copy(original.board);
        boards = new Stack<Piece[][]>();
    }

    private void init_board(int side_len) {
        side = side_len;

        board = new Piece[side][side];
        for(int i = 0; i < side; i++) {
            for(int j = 0; j < side; j++) {
                board[i][j] = Piece.EMPTY;
            }
        }

        board[0][0] = Piece.RED;
        board[0][side-1] = Piece.BLUE;
        board[side-1][0] = Piece.BLUE;
        board[side-1][side-1] = Piece.RED;
    }

    Piece get(int x, int y) {
        return board[x][y];
    }

    Piece get(Coordinate index) {
        return board[index.x][index.y];
    }

    private void set(int x, int y, Piece piece) {
        board[x][y] = piece;
    }

    void print() {

        System.out.println("~~~~~~~");
        String x_axis = "  ";

        for (int i = 0; i < side; i++) {
            x_axis += i;
        }

        System.out.println(x_axis);

        for (int j = 0; j < side; j++) {
            String row = "";
            for (int i = 0; i < side; i++) {
                if (i == 0) {
                    row += j + "|";
                }
                if (get(i, j) == Piece.RED) {
                    row += "R";
                } else if (get(i, j) == Piece.BLUE) {
                    row += "B";
                } else if (get(i, j) == Piece.EMPTY) {
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
        if (!valid_move(move)) {
            return;
        }

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
                if (in_bounds(tx+x, ty+y) &&
                        get(tx+x, ty+y) == whoseturn.opposite()) {
                    set(tx+x, ty+y, whoseturn);
                }
            }
        }

        whoseturn = whoseturn.opposite();

        if (game_over()) {
            declare_winner();
        }
    }

    void update_AI(Move move) {
        boards.push(board);
        board = java.util.Arrays.copyOf(board, board.length);
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
                if (in_bounds(tx+x, ty+y) &&
                        get(tx+x, ty+y) == whoseturn.opposite()) {
                    set(tx+x, ty+y, whoseturn);
                }
            }
        }

        whoseturn = whoseturn.opposite();
    }

    void call_AI() {
        update_AI(AI.find_move(this));
    }

    void undo() {
        board = boards.pop();
        whoseturn = whoseturn.opposite();
    }

    private boolean in_bounds(int x, int y) {
        return x >= 0 && x < side && y >= 0 && y < side;
    }

    private boolean is_jump(Move move) {
        Coordinate dxdy = move.dxdy();
        return Math.abs(dxdy.x) == 2 || Math.abs(dxdy.y) == 2;
    }

    boolean valid_move(Move move) {
        return in_bounds(move.from.x, move.from.y) &&
                in_bounds(move.to.x, move.to.y) &&
                get(move.from.x, move.from.y) == whoseturn &&
                get(move.to.x, move.to.y) == Piece.EMPTY &&
                valid_move_distance(move);
    }

    private boolean valid_move_distance(Move move) {
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
        for (int i = 2; i < side-2; i++) {
            for (int j = 2; j < side-2; j++) {
                if (get(i, j) == Piece.EMPTY) {
                    for (int x = -2; x <= 2; x++) {
                        for (int y = -2; y <= 2; y++) {
                            if (get(i+x, j+y) == whoseturn) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    void declare_winner() {
        int red_count = 0;
        int blue_count = 0;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (get(i, j) == Piece.RED) {
                    red_count++;
                } else if (get(i, j) == Piece.BLUE) {
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

    Piece whoseturn() {
        return whoseturn;
    }

    int length() {
        return side;
    }

    private RuntimeException error(String message) {
        return new RuntimeException(message);
    }

    int get_score() {
        int current_score = 0;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (get(i, j) == Piece.RED) {
                    current_score++;
                } else if (get(i, j) == Piece.BLUE) {
                    current_score--;
                }
            }
        }
        return current_score;
    }

    private Piece[][] matrix_copy(Piece[][] original) {
        Piece[][] b = new Piece[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                b[i][j] = original[i][j];
            }
        }
        return b;
    }

}
