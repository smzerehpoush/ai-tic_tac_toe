import java.util.HashSet;

public class Board {

    static final int BOARD_SIZE = 3;

    private State[][] board;
    private State currentPlayer;
    private State winner;
    private HashSet<Integer> movesAvailable;

    private int moveCount;
    private boolean gameOver;

    Board() {
        board = new State[BOARD_SIZE][BOARD_SIZE];
        movesAvailable = new HashSet<>();
        reset();
    }


    private void reset() {
        moveCount = 0;
        gameOver = false;
        currentPlayer = State.X;
        winner = State.Blank;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = State.Blank;
            }
        }

        movesAvailable = new HashSet<>();

        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            movesAvailable.add(i);
        }
    }

    boolean move(int index) {

        if (gameOver) {
            throw new IllegalStateException("Game is over. No moves can be played.");
        }
        int x = index % BOARD_SIZE;
        int y = index / BOARD_SIZE;

        if (board[y][x] == State.Blank) {
            board[y][x] = currentPlayer;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(index);

        // The game is a draw.
        if (moveCount == BOARD_SIZE * BOARD_SIZE) {
            winner = State.Blank;
            gameOver = true;
        }

        checkForWinner(x, y);

        currentPlayer = (currentPlayer == State.X) ? State.O : State.X;
        return true;
    }

    boolean isGameOver() {
        return gameOver;
    }

    State getCurrentPlayer() {
        return currentPlayer;
    }

    State getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("Game is not over yet.");
        }
        return winner;
    }

    HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    private void checkForWinner(int x, int y) {
        checkRow(y);
        checkColumn(x);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);
    }

    private void checkRow(int row) {
        for (int i = 1; i < BOARD_SIZE; i++) {
            if (board[row][i] != board[row][i - 1]) {
                break;
            }
            if (i == BOARD_SIZE - 1) {
                winner = currentPlayer;
                gameOver = true;
            }
        }
    }

    private void checkColumn(int column) {
        for (int i = 1; i < BOARD_SIZE; i++) {
            if (board[i][column] != board[i - 1][column]) {
                break;
            }
            if (i == BOARD_SIZE - 1) {
                winner = currentPlayer;
                gameOver = true;
            }
        }
    }

    private void checkDiagonalFromTopLeft(int x, int y) {
        if (x == y) {
            for (int i = 1; i < BOARD_SIZE; i++) {
                if (board[i][i] != board[i - 1][i - 1]) {
                    break;
                }
                if (i == BOARD_SIZE - 1) {
                    winner = currentPlayer;
                    gameOver = true;
                }
            }
        }
    }

    private void checkDiagonalFromTopRight(int x, int y) {
        if (BOARD_SIZE - 1 - x == y) {
            for (int i = 1; i < BOARD_SIZE; i++) {
                if (board[BOARD_SIZE - 1 - i][i] != board[BOARD_SIZE - i][i - 1]) {
                    break;
                }
                if (i == BOARD_SIZE - 1) {
                    winner = currentPlayer;
                    gameOver = true;
                }
            }
        }
    }

    Board getDeepClone() {
        Board board = new Board();

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.currentPlayer = this.currentPlayer;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount = this.moveCount;
        board.gameOver = this.gameOver;
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {

                if (board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != BOARD_SIZE - 1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }

}
