import java.util.LinkedList;

public class DominoManager implements DominoInterface{

    @Override
    public void initGame(LinkedList boardStart, LinkedList playerStart, LinkedList computerStart,
                         LinkedList boneyardStart, Dominoes d1) {
        int size = d1.size();
        int temp = 0;
        LinkedList<LinkedList> tempLL = new LinkedList<>();
        tempLL = (LinkedList<LinkedList>) d1.currentDominoes.clone();
        for (int i = 0; i < 14; i+=2){
            temp = (int) (Math.random() * size);
            playerStart.add(tempLL.remove(temp));
            size--;
            temp = (int) (Math.random() * size);
            computerStart.add(tempLL.remove(temp));
            size--;
        }
        boneyardStart.addAll(tempLL);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getBoard() {
        return null;
    }

    @Override
    public void endGame() {

    }
}
