import java.util.HashSet;
import java.util.Set;

public class Board {

    static final int SIZE = 3;

    public enum State {Blank, X, O}

    private State[][] board;
    private State whichPlayer;
    private State winner;
    private Set<Integer> movesAvailable;
    private int moveCount;
    private boolean isGameOver;

    Board() {
        board = new State[SIZE][SIZE];
        movesAvailable = new HashSet<>();
        reset();
    }

    void setState(int x, int y, State state) {
        board[x][y] = state;

    }

    //    sat all entities to their default value
    void reset() {
        moveCount = 0;
        isGameOver = false;
        whichPlayer = State.X;
        winner = State.Blank;
        // set all of board states to blank
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = State.Blank;
            }
        }

        movesAvailable = new HashSet<>();

        for (int i = 0; i < SIZE * SIZE; i++) {
            movesAvailable.add(i);
        }
    }

    //  convert state num to 2d point  7->(2,1)
    boolean move(int index) {
        return move(index / SIZE, index % SIZE);
    }

    private boolean move(int x, int y) {

        if (isGameOver) {
            throw new IllegalStateException("Game is over.");
        }

        if (board[x][y] != State.Blank) {
            return false;
        }

        board[x][y] = whichPlayer;
        moveCount++;
        movesAvailable.remove(x * SIZE + y);

        // The game is a draw.
        if (moveCount == SIZE * SIZE) {
            winner = State.Blank;
            isGameOver = true;
        }

        // Check for a winner.
        State result = checkWinner(board, SIZE);
        if (winner != (State.Blank)) {
            isGameOver = true;
            winner = result;
        }
        whichPlayer = (whichPlayer == State.X) ? State.O : State.X;
        return true;
    }

    boolean isGameOver() {
        return isGameOver;
    }

    State getPlayer() {
        return whichPlayer;
    }

    State getWinner() {
        if (!isGameOver) {
            throw new IllegalStateException("Game is not over yet.");
        }
        return winner;
    }

    Set<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    private State checkWinner(State[][] board, int boardSize) {
        try {
            if (board[0][0] != State.Blank && board[0][0] == board[1][1] && board[2][2] == board[1][1])
                return board[1][1];
            if (board[2][0] != State.Blank && board[2][0] == board[1][1] && board[1][1] == board[0][2])
                return board[1][1];
            for (int i = 0; i < boardSize; i++) {
                if (board[i][0] != State.Blank && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                    return board[i][0];
            }
            for (int i = 0; i < boardSize; i++) {
                if (board[0][i] != State.Blank && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                    return board[0][i];
            }

            return State.Blank;

        } catch (Exception ex) {
            throw new IllegalArgumentException("index is not valid.");
        }
    }

    Board deepClone() {
        Board b = new Board();

        for (int i = 0; i < b.board.length; i++) {
            b.board[i] = this.board[i].clone();
        }

        b.whichPlayer = this.whichPlayer;
        b.winner = this.winner;
        b.movesAvailable.addAll(this.movesAvailable);
        b.moveCount = this.moveCount;
        b.isGameOver = this.isGameOver;
        return b;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {

                if (board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != SIZE - 1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }

}
