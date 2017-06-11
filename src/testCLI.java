/**
 * Created by miles on 5/24/17.
 */

import java.util.Arrays;
import java.util.Stack;

public class testCLI extends CLI {
    static void test1() {
        String[] board1 = {"RR",
        "R-"};
        if (!test_square(board1)) {
            System.out.println("SCREEEEEEEEEEECH");
        }
        CLI g = new CLI(board1, false);
    }

    static void test2() {
        String[] board2 = {"R---",
        "XXX-",
        "XXX-",
        "BXX-"};
        if (!test_square(board2)) {
            System.out.println("SCREEEEEEEEEEECH");
        }
        CLI cli = new CLI(board2, false);
    }

    static void test3() {
        String[] board3 = {
                "-------R",
                "--------",
                "--------",
                "--------",
                "--------",
                "RRR-----",
                "--R-----",
                "B-R-----"};

        CLI cli = new CLI(board3, true);
    }

    static void randomtest() {
        int[] cool = new int[1];
        cool[0] = 4343623;
        Stack<int[]> asdf = new Stack<>();
        asdf.push(cool);
        cool = Arrays.copyOf(cool, cool.length);
        cool[0] = 1;
        System.out.println(asdf.pop()[0]);
    }

    public static void main(String[] args) {
        test3();
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
