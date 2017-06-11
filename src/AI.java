/**
 * Created by miles on 6/6/17.
 */
public class AI {
    private static final int max = 10000;
    private static final int min = -10000;
    private static final int max_depth = 5;
    
    static Move find_move(Board original) {
        Board board = new Board(original);
        Move best_move = null;
        int best_score;
        if (board.whoseturn() == Piece.RED) {
            System.out.println("ASDFASFOIAHEGFOIAHFAOSHFD");
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
                                int current_move_score = best_red_score(board, max_depth);
                                //System.out.println(current_move_score);
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
    }

    private static int find_move_score(Board board, int depth) {
        //IT NEVER GETS TO DEPTH 0
        //board.print();
        if (depth == 0) {
            //System.out.println(board.get_score());
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
    }

    private static int best_blue_score(Board board, int depth) {
        if (depth == 0) {
            //System.out.println(board.get_score());
            return board.get_score();
        }

        int best_score = max;

        for (int i = 0; i < board.length(); i++) {
            for (int j = 0; j < board.length(); j++) {
                if (board.get(i, j) == Piece.BLUE) {
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            Move current_move = new Move(i, j, i+x, j+y);
                            if (board.valid_move(current_move)) {
                                board.update_AI(current_move);
                                int current_move_score = best_red_score(board, depth - 1);
                                if (current_move_score < best_score) {
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
    }

    private static int best_red_score(Board board, int depth) {
        if (depth == 0) {
            //System.out.println(board.get_score());
            return board.get_score();
        }

        int best_score = min;

        for (int i = 0; i < board.length(); i++) {
            for (int j = 0; j < board.length(); j++) {
                if (board.get(i, j) == Piece.RED) {
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            Move current_move = new Move(i, j, i+x, j+y);
                            if (board.valid_move(current_move)) {
                                board.update_AI(current_move);
                                int current_move_score = best_blue_score(board, depth - 1);
                                if (current_move_score > best_score) {
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
    }
}
