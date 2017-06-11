/**
 * Created by miles on 5/20/17.
 */
import java.util.regex.Pattern;
public class Command {
    String type;
    String[] args;

    private static final Pattern play = Pattern.compile("play$");
    private static final Pattern help = Pattern.compile("help$");
    private static final Pattern move = Pattern.compile("(move)((\\s)([0-9]+)){4}$");
    private static final Pattern get = Pattern.compile("(get)((\\s)([0-9]+)){2}$");
    private static final Pattern turn = Pattern.compile("turn$");
    private static final Pattern print = Pattern.compile("print$");
    private static final Pattern quit = Pattern.compile("quit$");
    private static final Pattern gameover = Pattern.compile("gameover$");

    public Command(String t, String[] a) {
        //parse the string, separating it into TYPE and args
        type = t;
        args = a;
    }

    public static Command parse_command(String c) {
        c = c.trim();
        c= c.toLowerCase();
        if (play.matcher(c).matches()) {
            return new Command("play", null);
        } else if (help.matcher(c).matches()) {
            return new Command("help", null);
        } else if (move.matcher(c).matches()) {
            String[] coords = c.split(" ");
            return new Command("move", coords);
        } else if (get.matcher(c).matches()) {
            String[] coords = c.split(" ");
            return new Command("get", coords);
        } else if (turn.matcher(c).matches()) {
            return new Command("turn", null);
        } else if (print.matcher(c).matches()) {
            return new Command("print", null);
        } else if (quit.matcher(c).matches()) {
            return new Command("quit", null);
        } else if (gameover.matcher(c).matches()) {
            return new Command("gameover", null);
        } else {
            return new Command("invalid", null);
        }
    }
}
