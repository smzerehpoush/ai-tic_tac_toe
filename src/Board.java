import java.util.HashSet;

public class Board {
    static final int BOARD_SIZE = 3;

    private State[][] board;
    private State currentPlayer;
    private State winner;
    private HashSet<Integer> availableStates;

    private int moveCount;
    private boolean gameOver;

    Board() {
        board = new State[BOARD_SIZE][BOARD_SIZE];
        availableStates = new HashSet<>();
        moveCount = 0;
        gameOver = false;
        currentPlayer = State.X;
        winner = State.Blank;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = State.Blank;
            }
        }

        availableStates = new HashSet<>();

        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            availableStates.add(i);
        }
    }


    boolean move(int index) {

        if (gameOver || moveCount > BOARD_SIZE * BOARD_SIZE) {
            throw new IllegalStateException("State is over. No moves can be played.");
        }
//         x
//        y01
//         10
        int x = index % BOARD_SIZE;
        int y = index / BOARD_SIZE;

        if (board[y][x] == State.Blank) {
            board[y][x] = currentPlayer;
        } else {
//            to say user state must be blank
            return false;
        }

        moveCount++;
        availableStates.remove(index);

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
            throw new IllegalStateException("State is not over yet.");
        }
        return winner;
    }

    HashSet<Integer> getAvailableMoves() {
        return availableStates;
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
        board.availableStates = new HashSet<>();
        board.availableStates.addAll(this.availableStates);
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
