import java.util.LinkedList;

public class DominoManager implements DominoInterface{

    /**
     * This is to initiate the game. It takes in all 28 possible dominoes and spreads them across
     * the computer player, boneyard, and human player.
     * @param boardStart This is the current board with 0 dominoes
     * @param playerStart This is the Player Starting dominoes
     * @param computerStart This is the Computer Starting Dominoes
     * @param boneyardStart This is the Boneyard Starting Dominoes
     * @param d1 This is the 28 possible dominoes
     */
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

    /**
     * This is to check if the game is over
     * @param gameOver If the user initiated a game Over
     * @param p1 The Player Object
     * @param c1 The Computer Object
     * @return Returns true if game is over, false if otherwise
     */
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

    /**
     * This is for the end of the game. To clear all linked lists and declare the winner.
     * @param p1 Player Object
     * @param c1 Computer Object
     * @param b1 Boneyard Object
     * @param playerForfeit If Player Forfeited boolean
     * @return Returns a String of who won.
     */
    @Override
    public String endGame(Player p1, Computer c1, Boneyard b1, boolean playerForfeit) {
        if (playerForfeit){
            p1.clear();
            c1.clear();
            b1.clear();
            return "Player forfeit Computer Wins";
        }
        else if (p1.dominoCount() == 0){
            p1.clear();
            c1.clear();
            b1.clear();
            return "Player wins with 0 Dominoes left";
        }
        else if (c1.dominoCount() == 0){
            p1.clear();
            c1.clear();
            b1.clear();
            return "Computer wins with 0 Dominoes left";
        }
        else if (p1.dominoesTotal() > c1.dominoesTotal()) {
            p1.clear();
            c1.clear();
            b1.clear();
            return "Computer wins by having more dominoes total";
        }
        else{
            p1.clear();
            c1.clear();
            b1.clear();
            return "Player wins by having more dominoes total";
        }
    }
}
