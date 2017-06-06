/**
 * Created by miles on 6/6/17.
 */
public class AI {
    private static final int max = Integer.MAX_VALUE;
    private static final int min = Integer.MIN_VALUE;
    private static final int max_depth = 20;
    
    static Move find_move(Board original) {
        Board board = new Board(original);
        Move best_move = null;
        int best_score;
        if (board.whoseturn() == Piece.RED) {
            best_score = min;
        } else {
            best_score = max;
        }
        for (int i = 0; i < board.length(); i++) {
            for (int j = 0; j < board.length(); j++) {
                if (board.get(i, j) == board.whoseturn()) {
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            Move current_move = new Move(i, j, i+x, j+y);
                            if (board.valid_move(current_move)) {
                                board.update_AI(current_move);
                                int current_move_score = find_move_score(board, max_depth);
                                if (current_move_score < best_score) {
                                    best_move = current_move;
                                    best_score = current_move_score;
                                }
                                board.undo();
                            }
                        }
                    }
                }
            }
        }

        return best_move;
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
    }

    private static int find_move_score(Board board, int depth) {
        if (depth == 0) {
            return board.get_score();
        }
        int best_score;
        if (board.whoseturn() == Piece.RED) {
            best_score = min;
        } else {
            best_score = max;
        }
        for (int i = 0; i < board.length(); i++) {
            for (int j = 0; j < board.length(); j++) {
                if (board.get(i, j) == board.whoseturn()) {
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            Move current_move = new Move(i, j, i+x, j+y);
                            if (board.valid_move(current_move)) {
                                board.update_AI(current_move);
                                int current_move_score = find_move_score(board, depth - 1);
                                //if reds turn, choose the biggest score
                                if (board.whoseturn() == Piece.RED && current_move_score > best_score) {
                                    best_score = current_move_score;
                                } else if (board.whoseturn() == Piece.BLUE && current_move_score < best_score) {
                                    best_score = current_move_score;
                                }

                                board.undo();
                            }
                        }
                    }
                }
            }
        }
        
        return best_score;
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
    }
}
