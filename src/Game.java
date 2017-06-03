/**
 * Created by miles on 5/20/17.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    private Board start_board;
    private Board b;
    private Mode mode;
    private Scanner sc;
    private BufferedReader r;


    enum Mode {
        MENU,
        PLAY,
        quit
    }

    Game() {
        this.sc = new Scanner(System.in);
        this.mode = Mode.MENU;
        start_board = new Board();
        process();
    }

    Game(boolean useGUI, BufferedReader r, int n) {
        this.mode = Mode.PLAY;
        this.r = r;
        start_board = new Board(n);
    }

    Game(String[] sb) {
        this.sc = new Scanner(System.in);
        this.mode = Mode.MENU;
        start_board = new Board(sb);
        process();
    }

    private void process() {
        Command comm = get_command();
        String commtype = comm.type;
        if (mode == Mode.MENU) {
            switch (commtype) {
                case "play":
                    init_play();
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
                    } catch (RuntimeException e) {
                        System.out.println(e);
                    }
                    break;
                case "get":
                    int x = Integer.parseInt(comm.args[1]);
                    int y = Integer.parseInt(comm.args[2]);
                    System.out.println(b.get(x, y));
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

    void process_gui(GUI gui, String command) {
        Command comm = Command.parse_command(command);
        String commtype = comm.type;
        if (mode == Mode.MENU) {
            switch (commtype) {
                case "play":
                    init_play();
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
                        gui.update(b);
                        if (b.game_over()) {
                            b.declare_winner();
                            mode = Mode.MENU;
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e);
                    }
                    break;
                case "get":
                    int x = Integer.parseInt(comm.args[1]);
                    int y = Integer.parseInt(comm.args[2]);
                    System.out.println(b.get(x, y));
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

    }

    void init_play() {
        b = new Board(start_board);
        mode = Mode.PLAY;
    }

    public Board board() {
        return b;
    }

}