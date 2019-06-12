import java.util.Scanner;

public class Game {
    private int boardSize;
    private int player;
    private Scanner scanner = new Scanner(System.in);

    public Game(int boardSize, int player) throws Exception {
        if (boardSize != 3)
            throw new Exception("Unsupported board size!");
        this.boardSize = boardSize;
        if (player != 1 && player != -1)
            throw new Exception("Player is not valid.");
        this.player = player;

    }

    public static void main(String[] args) throws Exception {
        final int size = 3;
        final int[][] board = new int[size][size];
        final int player = 1;
        //AI : 1, Human : -1
        int[][] board2 = new int[][]{
                {1, -1, 1},
                {-1, -1, 1},
                {0, 0, 0}};
        Game game = new Game(size, player);
        int winner = game.simulate(board2, 1, 0);
        System.out.println("winner is " + (winner == 1 ? "AI" : winner == -1 ? "Human" : "NO-ONE"));

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

//    int simulateDepth(int[][] board, int player) throws Exception {
//        int winner = getWinner(board);
//        if (winner != 0) {
//            return winner;
//        }
//        printBoard(board);
//        for (int i = 0; i < boardSize; i++) {
//            for (int j = 0; j < boardSize; j++) {
//                if (board[i][j] == 0) {
//                    int[][] newBoard = cloneArray(board);
//                    newBoard[i][j] = player;
//                    return simulate(newBoard, player == 1 ? -1 : 1);
//
//                }
//            }
//        }
//        return 0;
//    }

    int simulate(int[][] board, int player, int score) throws Exception {
        System.out.println("score : " + score);
        printBoard(board);
        int winner = getWinner(board);
        if (winner != 0) {
            System.out.println("winner : " + (player == 1 ? 'o' : player == -1 ? 'x' : '-'));
            return winner;
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    int[][] newBoard = cloneArray(board);
                    newBoard[i][j] = player;
                    score += simulate(newBoard, player == 1 ? -1 : 1, score);

                }
            }
        }
        return score;

    }

    private void printBoard(int[][] board) throws Exception {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                try {
                    System.out.print(board[i][j] == 1 ? 'x' : board[i][j] == -1 ? 'o' : '-');
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
                    return player == 1 ? -1 : 1;
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

//    int play(int[][] board) {
//        int[] point;
//        if (player == -1)
//            point = takeInputFromUser();
//        else
//            point = takeInputFromComputer();
//        board[point[0]][point[1]] = player;
//    }

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

    private int[] takeInputFromComputer() {
        return new int[0];
    }

    public static void main(String[] args) throws Exception {
        final int size = 3;
        final int[][] board = new int[size][size];
        final int player = 1;
        //1 : AI, -1 : Human
        Game game = new Game(size, player);
        int winner = game.simulate(board, 1);
        System.out.println("winner is " + (winner == 1 ? "AI" : "Human"));

    }

}
