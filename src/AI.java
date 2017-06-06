/**
 * Created by miles on 6/6/17.
 */
public class AI {
    static Move find_move(Board original) {
        Board board = new Board(original);
        Move best_move = null;
        int best_score = 0;
        for (int i = 0; i < board.length(); i++) {
            for (int j = 0; j < board.length(); j++) {
                if (board.get(i, j) == board.whoseturn()) {
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if (board.get(i, j) == board.whoseturn()) {

                            }
                        }
                    }
                }
            }
        }
        /**
         * for each square on the board:
         *  if the square has a piece of color whoseturn():
         *    for each possible (extend) move of that piece:
         *      current_move = new move(square with current piece, current extend move)
         *      board.update(current_move)
         *      int current_move_score = int find_move_score(board)
         *      if (current_move_score is better than best_score): //this isn't necessarily greater or less than
         *        best_move = current_move
         *        best_score = current_move_score
         *      board.undo()
         *
         * return best_move
         *
         */
        return new Move(0, 0, 0, 0);
    }

    private static int find_move_score(Board board, int negate_if_blue) {
        /**
         * for each square on the board:
         *  if the square has a piece of color whoseturn():
         *    for each possible (extend) move of that piece:
         *      current_move = new move(square with current piece, current extend move)
         *      board.update(current_move)
         *      int current_move_score = int find_move_score(board)
         *      if (current_move_score is better than best_score): //this isn't necessarily greater or less than
         *        best_move = current_move
         *        best_score = current_move_score
         *      board.undo()
         * return best_score
         */
        return 0;
    }
}
