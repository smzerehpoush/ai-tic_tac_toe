import java.util.Scanner;

public class Console {

    private Board board;
    private Scanner scanner = new Scanner(System.in);

    private Console() {
        board = new Board();
    }

    public static void main(String[] args) {
        Console ticTacToe = new Console();
        ticTacToe.play();
    }

    private void play() {

        System.out.println("Starting a new game.");
        while (true) {
            printGameStatus();
            playGame();

            if (board.isGameOver()) {
                printWinner();
                break;
            }
        }

    }

    private void playGame() {
        if (board.getPlayer() == Board.State.X) {
            int[] point = takeInputFromUser();
            board.setState(point[0], point[1], Board.State.X);
        } else {
            Algorithms.miniMax(board, 10);
        }
    }

    private void printGameStatus() {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getPlayer().name() + "'s turn.");
    }

    private int[] takeInputFromUser() {

        while (true) {
            try {
                System.out.print("Index of x : ");
                int x = scanner.nextInt();
                if (x < 0 || x > Board.SIZE) {
                    System.out.println("\nInvalid num.");
                    continue;
                }
                System.out.print("Index of y : ");
                int y = scanner.nextInt();
                if (y < 0 || y > Board.SIZE) {
                    System.out.println("\nInvalid num.");
                    continue;
                }
                int num = x * Board.SIZE + y;
                if (num < 0 || num >= Board.SIZE * Board.SIZE) {
                    System.out.println("\nInvalid num.");
                } else if (!board.move(num)) {
                    System.out.println("\nInvalid num.");
                    System.out.println("The selected index must be blank.");
                }
                int[] result = new int[2];
                result[0] = x;
                result[1] = y;
                return result;
            } catch (Exception ignored) {
            }
        }

    }

    private void printWinner() {
        Board.State winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == Board.State.Blank) {
            System.out.println("Game is Draw.");
        } else {
            System.out.println("Player " + winner.name() + " wins!");
        }
    }

}
