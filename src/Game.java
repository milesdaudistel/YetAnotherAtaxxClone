/**
 * Created by miles on 5/20/17.
 */
import java.util.Scanner;

public class Game {
    private Board b;
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
                    try {
                        //b.can_move(move);
                        //update should call can_move
                        //if can_move throws an error, update should throw that error up here
                        b.update(move);
                        b.print();
                        if (b.game_over()) {
                            b.declare_winner();
                            mode = Mode.MENU;
                        }
                    } catch (MyException e) {
                        System.out.println(e);
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

    Command get_command() {
        System.out.println("Input a command: ");
        Command c = Command.parse_command(sc.nextLine());
        while (c.type == "invalid") {
            System.out.println("command not recognized");
            c = Command.parse_command(sc.nextLine());
        }
        return c;
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