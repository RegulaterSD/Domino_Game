import java.util.LinkedList;

public interface DominoInterface {

    /**
     * This is to initiate the game. It takes in all 28 possible dominoes and spreads them across
     * the computer player, boneyard, and human player.
     * @param boardStart This is the current board with 0 dominoes
     * @param playerStart This is the Player Starting dominoes
     * @param computerStart This is the Computer Starting Dominoes
     * @param boneyardStart This is the Boneyard Starting Dominoes
     * @param d1 This is the 28 possible dominoes
     */
    void initGame(LinkedList boardStart, LinkedList playerStart, LinkedList computerStart,
                  LinkedList boneyardStart, Dominoes d1);

    /**
     * This is to check if the game is over
     * @param gameOver If the user initiated a game Over
     * @param p1 The Player Object
     * @param c1 The Computer Object
     * @return Returns true if game is over, false if otherwise
     */
    boolean isGameOver(boolean gameOver, Player p1, Computer c1);

    /**
     * This is for the end of the game. To clear all linked lists and declare the winner.
     * @param p1 Player Object
     * @param c1 Computer Object
     * @param b1 Boneyard Object
     * @param playerForfeit If Player Forfeited boolean
     * @return Returns a String of who won.
     */
    String endGame(Player p1, Computer c1, Boneyard b1, boolean playerForfeit);

}
