import java.util.LinkedList;

public interface DominoInterface {

    void initGame(LinkedList boardStart, LinkedList playerStart, LinkedList computerStart,
                  LinkedList boneyardStart, Dominoes d1);

    boolean isGameOver();

    String getBoard();

    void endGame();

}
