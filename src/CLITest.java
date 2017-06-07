/**
 * Created by miles on 5/24/17.
 */

public class CLITest extends CLI {
    static void test1() {
        String[] board1 = {"RR",
        "R-"};
        if (!test_square(board1)) {
            System.out.println("SCREEEEEEEEEEECH");
        }
        CLI g = new CLI(board1);
    }

    static void test2() {
        String[] board2 = {"R---",
        "XXX-",
        "XXX-",
        "BXX-"};
        if (!test_square(board2)) {
            System.out.println("SCREEEEEEEEEEECH");
        }
        CLI g = new CLI(board2);
    }

    static void randomtest() {
        System.out.println(5/4);
    }

    public static void main(String[] args) {
        test1();
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

    /**
     *
     *
     *
     * next up:  error handling
     * then maybe fix your game implementation
     * leave it as a 2d array
     * like you thought, its just mapped to a 1d array anyway
     * so theres no difference in implementation
     *
     *
     */
}
