import java.util.Scanner;

public class Game {

    private Board board;
    private Scanner scanner;

    private Game() {
        board = new Board();
    }

    private void play() {

        System.out.println("Starting a new game.");

        while (true) {
            printGameStatus();
            playMove();

            if (board.isGameOver()) {
                printWinner();
                break;
            }
        }
    }

    private void playMove() {
        if (board.getCurrentPlayer() == State.X) {
            getPlayerMove();
        } else {
            MiniMax.run(board);
        }
    }

    private void printGameStatus() {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getCurrentPlayer().name() + "'s turn.");
    }

    private void getPlayerMove() {

        int move;
        while (true) {
            try {
                System.out.print("Index of move: ");
                scanner = new Scanner(System.in);
                move = scanner.nextInt();
                break;
            } catch (Exception ex) {
                System.out.println("\nInvalid move.");
                scanner.next();
            }
        }

        if (move < 0 || move >= Board.BOARD_SIZE * Board.BOARD_SIZE) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe index of the move must be between 0 and "
                    + (Board.BOARD_SIZE * Board.BOARD_SIZE - 1) + ", inclusive.");
        } else if (!board.move(move)) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe selected index must be blank.");
        }
    }

    private void printWinner() {
        State winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == State.Blank) {
            System.out.println("No Winner!");
        } else {
            String message = null;
            message = (winner == State.X) ? "You Win!" : "You Loose!";
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

}
