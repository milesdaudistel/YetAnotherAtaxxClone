/**
 * Created by miles on 5/24/17.
 */
public class GameTest extends Game {
    static void test1() {
        String[] board1 = {"RR",
        "R-"};
        if (!test_square(board1)) {
            System.out.println("SCREEEEEEEEEEECH");
        }
        Game g = new Game(board1);
    }

    static void test2() {
        String[] board2 = {"R---",
        "XXX-",
        "XXX-",
        "BXX-"};
        if (!test_square(board2)) {
            System.out.println("SCREEEEEEEEEEECH");
        }
        Game g = new Game(board2);
    }

    public static void main(String[] args) {
        test2();
    }

    static boolean test_square(String[] board) {
        int len = board.length;
        for (String s : board) {
            if (s.length() != len) {
                return false;
            }
        }
        return true;
    }
}
