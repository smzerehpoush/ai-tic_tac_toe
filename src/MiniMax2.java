/**
 * Uses the MiniMax2 algorithm to play a move in a game of Tic Tac Toe.
 */
class MiniMax2 {

    private static double MAX_DEPTH;

    private MiniMax2() {
    }

    static void run(State player, Board board, double maxDepth) {
        MiniMax2.MAX_DEPTH = maxDepth;
        miniMax(player, board, 0);
    }

    private static int miniMax(State player, Board board, int currentDepth) {
        if (currentDepth++ == MAX_DEPTH || board.isGameOver()) {
            return computeScore(player, board);
        }

        if (board.getPlayer() == player) {
            return getMax(player, board, currentDepth);
        } else {
            return getMin(player, board, currentDepth);
        }

    }

    /**
     * Play the move with the highest computeScore.
     *
     * @param player     the player that the AI will identify as
     * @param board      the Tic Tac Toe board to play on
     * @param currentPly the current depth
     * @return the computeScore of the board
     */
    private static int getMax(State player, Board board, int currentPly) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.deepClone();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentPly);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int) bestScore;
    }

    /**
     * Play the move with the lowest computeScore.
     *
     * @param player     the player that the AI will identify as
     * @param board      the Tic Tac Toe board to play on
     * @param currentPly the current depth
     * @return the computeScore of the board
     */
    private static int getMin(State player, Board board, int currentPly) {
        double bestScore = Double.POSITIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.deepClone();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentPly);

            if (score <= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int) bestScore;
    }

    private static int computeScore(State player, Board board) {
        if (player == State.Blank) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        State opponent = (player == State.X) ? State.O : State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return 10;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }


}
