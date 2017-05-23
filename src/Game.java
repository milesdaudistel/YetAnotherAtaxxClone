/**
 * Created by miles on 5/20/17.
 */
import java.util.Scanner;

public class Game {
    Piece[][] board;
    int dim;
    int border;
    Piece current_player;
    Mode mode;
    Scanner sc;

    enum Mode {
        MENU,
        PLAY,
        //CLOSE;
    }

    public Game() {
        //init mode to MENU
        //call init_game method
        //set current_player to red
        //call process
        this.sc = new Scanner(System.in);
        this.mode = Mode.MENU;
        process();
    }

    private void init_game() {
        //set board to have n rows/cols
        //fill with empty
        //set blocks at locations defined in blocks array
        //set 2 corners to red pieces, 2 corners to blue pieces
        //  blocks can't go in corners, or be at position not on the board

        dim = 10;
        border = 2;
        //board = new Piece[dim+border*2][dim+border*2];
        board = new Piece[dim][dim];
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                if (dim - i > dim - border ||
                        dim - i <= border ||
                        dim - j > dim - border ||
                        dim - j <= border) {
                    board[i][j] = Piece.BLOCK;
                } else {
                    board[i][j] = Piece.EMPTY;
                }
            }
        }

        board[border][border] = Piece.RED;
        board[border][dim-border-1] = Piece.BLUE;
        board[dim-border-1][border] = Piece.BLUE;
        board[dim-border-1][dim-border-1] = Piece.RED;

        current_player = Piece.RED;

    }

    public void process() {
        //while mode is MENU, valid commands are play, help, exit
            //calls init_game method, sets current_player to red
        
        while (mode == Mode.MENU) {
            //do nothing really.  just wait for player to call play method
            //play method will init_game + set current_player to red
            get_move();
        }

        while (mode == Mode.PLAY) {
            print_board();
            Move move = get_move();
            while (!is_valid_move(move)) {
                move = get_move();
            }
            update_board(move);
            current_player = current_player.opposite();
            if (game_is_over()) {
                print_board();
                declare_winner();
                mode = Mode.MENU;
            }
        }

        process();
        
        //while mode is PLAY, valid commands are move, help, etc
        
            //print board
            //print 'player x's turn'
    
            //update_board_board(move)
    
            //current_player = current_player.opposite()
        
            //if game_is_over, print out winner, mode == MENU
        
        //if mode != CLOSE
        //  process()
    }

    void update_board(Move move) {
        //board[move.to.x][move.to.y] = board[move.from.x][move.from.y]
        //if move is jump: board[move.from.x][move.from.y] = empty
        //for the 8 spaces around move.to, if space == opposite, space = current_player
        //just use 2 for loops, make your life easy
        int tx = move.to.x;
        int ty = move.to.y;

        int fx = move.from.x;
        int fy = move.from.y;

        board[tx][ty] = board[fx][fy];
        if (is_jump(move)) {
            board[fx][fy] = Piece.EMPTY;
        }
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (board[tx+x][ty+y] == current_player.opposite()) {
                    board[tx+x][ty+y] = current_player;
                }
            }
        }
    }

    boolean is_jump(Move move) {
        Coordinate dxdy = move.dxdy();
        if (Math.abs(dxdy.x) == 2 || Math.abs(dxdy.y) == 2) {
            return true;
        } else {
            return false;
        }
        //if dx is 2 or dy is 2, its a jump
    }

    boolean game_is_over() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == Piece.EMPTY) {
                    for (int x = -2; x <= 2; x++) {
                        for (int y = -2; y <= 2; y++) {
                            if (board[i+x][j+y] == current_player) {
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
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
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

    void print_board() {
        //print border ~~~~~
        //for square on board print RBX-
        //print border ~~~~~
        System.out.println("~~~~~~~");
        for (int i = 0; i < dim; i++) {
            String row = "";
            for (int j = 0; j < dim; j++) {
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

    Move get_move() {
        //prompt player for command
        //if Commands.valid_command(
        //if the command has no possibility of changing game state:
        //  check that it's a valid command for the current state
        //else, it does attempt to change the game state (this includes all actions, which could
        //  potentially end the game
        //  check that its a valid state change
        //  if is_valid_move, return move
        //  else, recurse

        System.out.println("Input a command: ");
        String[] commstr = sc.nextLine().toLowerCase().split(" ");
        String commtype = commstr[0];
        if (mode == Mode.MENU) {
            switch (commtype) {
                case "play":
                    //change mode to play and other stuff
                    init_game();
                    mode = Mode.PLAY;
                    return new Move(0, 0, 0, 0);
                case "help":
                    System.out.println("lol im not help you");
                    break;
                case "quit":
                    quit();
                    return new Move(0, 0, 0, 0);
                default:
                    System.out.println("command not understood");
                    break;
            }
        } else if (mode == Mode.PLAY) {
            switch (commtype) {
                case "move":
                    //do the move stuff
                    return new Move(Integer.parseInt(commstr[1]),
                            Integer.parseInt(commstr[2]),
                            Integer.parseInt(commstr[3]),
                            Integer.parseInt(commstr[4]));
                case "print":
                    print_board();
                    break;
                case "help":
                    System.out.println("still not going to help");
                    break;
                case "quit":
                    quit();
                    System.out.println("leaving current game");
                    return new Move(0, 0, 0, 0);
                default:
                    System.out.println("command not understood");
                    break;
            }
        }

        return get_move();

        //command = null
        //move = move(0, 0, 0, 0)
        //while command is not a valid move:
        //  prompt user for command
        //  if command is a move, check validity.  if valid, move = new valid move
        //  if command is help, print help
        //  if command is print board, print the board again
        //  else, print 'unrecognized command'
    }

    boolean is_valid_move(Move move) {
        Coordinate dxdy = move.dxdy();
        int x = Math.abs(dxdy.x);
        int y = Math.abs(dxdy.y);
        if (board[move.from.x][move.from.y] == current_player &&
                board[move.to.x][move.to.y] == Piece.EMPTY &&
                x >=0 &&
                x <= 2 &&
                y >= 0 &&
                y <= 2 &&
                x + y > 0 &&
                move.from.x >= 0 &&
                move.from.x <= dim - 1 &&
                move.to.x >= 0 &&
                move.to.x <= dim - 1 &&
                move.from.y >= 0 &&
                move.from.y <= dim - 1 &&
                move.to.y >= 0 &&
                move.to.y <= dim - 1) {
            return true;
        } else {
            System.out.println("Invalid move");
            return false;
        }
        //move.from.color ?= current_player
        //move.to.color ?= empty
        //|move.from - move.to| <= 2
        //  just use coordinates dxdy function
        //move is not out of bounds
        //  return true;
    }
    
    void quit() {
        /*
        if (mode == Mode.PLAY) {
            mode = Mode.MENU;
        } else {
            mode = Mode.CLOSE;
        }
        */
        System.exit(0);
        //else, gamestate = close
        //what this will do is quit the game if in a game, and quit the application if not in a game
    }
}
