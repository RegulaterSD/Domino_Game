import java.util.LinkedList;

public interface DominoInterface {

    void initGame(LinkedList boardStart, LinkedList playerStart, LinkedList computerStart,
                  LinkedList boneyardStart, Dominoes d1);

    boolean isGameOver(boolean gameOver, Player p1, Computer c1);

    String endGame(Player p1, Computer c1, Boneyard b1, boolean playerForfeit);

}
