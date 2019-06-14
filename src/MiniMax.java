class MiniMax {

    static int counter;
    //counter for number of answers...

    private MiniMax() {
    }

    static void run(Board board) {
        counter = 0;
        miniMax(board.getCurrentPlayer(), board);
        System.out.println("best answer from " + counter + " results!");

    }

    private static int miniMax(final State player, final Board board) {
        if (board.isGameOver()) {
            return computeScore(player, board);
        }

        if (board.getCurrentPlayer() == player) {
            return computeBestScore(player, board, false);
        } else {
            return computeBestScore(player, board, true);
        }

    }

    private static int computeBestScore(final State player, final Board board, final boolean isMin) {
        double bestScore;
        if (isMin)
            bestScore = Double.POSITIVE_INFINITY;
        else
            bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {
            counter++;
            Board modifiedBoard = board.getDeepClone();
            modifiedBoard.move(move);
            int score = miniMax(player, modifiedBoard);
            if (isMin) {

                if (score <= bestScore) {
                    bestScore = score;
                    indexOfBestMove = move;
                }

            } else {

                if (score >= bestScore) {
                    bestScore = score;
                    indexOfBestMove = move;
                }
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

        if (board.getWinner() == player)
            return 10;
        else if (board.getWinner() == opponent)
            return -10;
        return 0;
    }
}
