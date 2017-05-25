/**
 * Created by miles on 5/20/17.
 */
import java.util.Scanner;

public class Game {
    private Board b;
    private int dim;
    private Piece current_player;
    private Mode mode;
    private Scanner sc;


    enum Mode {
        MENU,
        PLAY,
        quit
    }

    Game() {
        this.sc = new Scanner(System.in);
        this.mode = Mode.MENU;
        b = new Board();
        current_player = Piece.RED;
        process();
    }

    Game(String[] sb) {
        this.sc = new Scanner(System.in);
        this.mode = Mode.MENU;
        b = new Board(sb);
        current_player = Piece.RED;
        process();
    }

    private void process() {
        Command comm = get_command();
        String commtype = comm.type;
        if (mode == Mode.MENU) {
            switch (commtype) {
                case "play":
                    //change mode to play and other stuff
                    mode = Mode.PLAY;
                    break;
                case "help":
                    System.out.println("lol im not help you");
                    break;
                case "quit":
                    quit();
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }

        } else if (mode == Mode.PLAY) {
            switch (commtype) {
                case "move":

                    Move move = new Move(Integer.parseInt(comm.args[1]),
                            Integer.parseInt(comm.args[2]),
                            Integer.parseInt(comm.args[3]),
                            Integer.parseInt(comm.args[4]));
                    if (is_valid_move(move)) {
                        update_game(move);
                        if (game_over()) {
                            declare_winner();
                            mode = Mode.MENU;
                        }
                    } else {
                        System.out.println("invalid move");
                        b.print();
                    }
                    break;
                case "print":
                    b.print();
                    break;
                case "help":
                    System.out.println("still not going to help");
                    break;
                case "quit":
                    quit();
                    System.out.println("leaving current game");
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }

        if (mode != Mode.quit) {
            process();
        }
    }

    private void update_game(Move move) {
        b.update(move);
        current_player = current_player.opposite();
    }

    private boolean game_over() {
        return b.game_over(current_player);
    }

    void declare_winner() {
        b.declare_winner();
    }

    Command get_command() {
        System.out.println("Input a command: ");
        Command c = Command.parse_command(sc.nextLine());
        while (c.type == "invalid") {
            System.out.println("command not recognized");
            c = Command.parse_command(sc.nextLine());
        }
        return c;
    }


    // TODO
    // catch the out of bounds exception if from/to.x/y is not a valid index
    boolean is_valid_move(Move move) {
        Coordinate dxdy = move.dxdy();
        int x = Math.abs(dxdy.x);
        int y = Math.abs(dxdy.y);
        System.out.println(b.get(move.from.x, move.from.y));
        if (b.get(move.from.x, move.from.y) == current_player &&
                b.is_legal_move(move)) {
            return true;
        } else {
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

        if (mode == Mode.PLAY) {
            mode = Mode.MENU;
        } else {
            mode = Mode.quit;
        }

        //System.quit(0);
        //else, gamestate = close
        //what this will do is quit the game if in a game, and quit the application if not in a game
    }
}
