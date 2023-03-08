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
    public boolean isGameOver(boolean gameOver, Player p1, Computer c1) {
        if (gameOver) {
            return true;
        }
        else if (p1.dominoCount() == 0 || c1.dominoCount() == 0){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String endGame(Player p1, Computer c1, Boneyard b1, boolean playerForfeit) {
        String winner;
        if (p1.dominoCount() == 0){
            return "Player wins";
        }
        else if (c1.dominoCount() == 0 || playerForfeit){
            return "Computer wins";
        }
        else if (p1.dominoesTotal() > c1.dominoesTotal()) {
            return "Computer wins";
        }
        else{
            return "Player wins";
        }
    }
}
