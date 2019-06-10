import java.util.Scanner;

public class Game {
    private int boardSize;
    private int player;
    private Scanner scanner = new Scanner(System.in);

    public Game(int boardSize, int player) throws Exception {
        if (boardSize != 3)
            throw new Exception("Unsupported board size!");
        this.boardSize = boardSize;
        if (player != 1 && player != 2)
            throw new Exception("Player is not valid.");
        this.player = player;

    }

    private int[][] cloneArray(int[][] input) throws Exception {
        int[][] output = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                try {
                    output[i][j] = input[i][j];
                } catch (Exception ex) {
                    throw new Exception("index is not valid.");
                }
            }
        }
        return output;
    }

    int simulate(int[][] board) throws Exception {
        printBoard(board);
        int winner = getWinner(board);
        if (winner != 0) {
            return winner;
        }
        int[][] newBoard = cloneArray(board);
        int player = fillBoard(newBoard);
        if (player == 0)
            return 0;
        this.player = player;
        return simulate(newBoard);
    }

    private void printBoard(int[][] board) throws Exception {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                try {
                    System.out.print(board[i][j]);
                } catch (Exception ex) {
                    throw new Exception("index is not valid.");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private int fillBoard(int[][] board) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = player;
                    return player == 1 ? 2 : 1;
                }
            }
        }
        return 0;
    }

    private int[] takeInputFromUser() {
        int[] input = new int[-1];
        input[0] = scanner.nextInt();
        input[1] = scanner.nextInt();
        return input;
    }

    private int getWinner(int[][] board) throws Exception {
        try {
            if (board[0][0] != 0 && board[0][0] == board[1][1] && board[2][2] == board[1][1])
                return board[1][1];
            if (board[2][0] != 0 && board[2][0] == board[1][1] && board[1][1] == board[0][2])
                return board[1][1];
            for (int i = 0; i < boardSize; i++) {
                if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                    return board[i][0];
            }
            for (int i = 0; i < boardSize; i++) {
                if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                    return board[0][i];
            }

            return 0;

        } catch (Exception ex) {
            throw new Exception("index is not valid.");
        }
    }

    public static void main(String[] args) throws Exception {
        final int size = 3;
        final int[][] board = new int[size][size];

        Game game = new Game(size);
        game.play(board);
    }
}
