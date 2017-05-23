/**
 * Created by miles on 5/20/17.
 */
public class Command {
    public enum Type {
        PLAY,
        HELP,
        MOVE,
        TURN,
        BOARD,
        EXIT;
    }

    Type type;
    String args;

    public Command(String comm) {
        //parse the string, separating it into TYPE and args
    }
}
