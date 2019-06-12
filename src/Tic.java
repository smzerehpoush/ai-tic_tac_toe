/**
 * Created by mhd.zerehpoosh on 6/11/2019.
 */
public class Tic {
    /**
     * Play using the MiniMax Algorithm.
     *
     * @param board the Tic Tac Toe board to play on
     */
    public static void miniMax(Board board) {
        MiniMax.run(board.getPlayer(), board, Double.POSITIVE_INFINITY);
    }

    /**
     * Play using the MiniMax algorithm. Include a depth limit.
     *
     * @param board the Tic Tac Toe board to play on
     * @param ply   the maximum depth
     */
    public static void miniMax(Board board, int ply) {
        MiniMax.run(board.getPlayer(), board, ply);
    }

    public static void main(String[] args) {

    }
}
