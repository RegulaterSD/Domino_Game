import java.util.Collection;
import java.util.LinkedList;

public class Player {
    protected LinkedList<LinkedList> playerDominoes = new LinkedList<>();

    /**
     * This is the main Player Constructor
     * @param dominoes the dominoes being added to the players tray
     */
    Player(LinkedList dominoes){
        this.playerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    /**
     * This is to add a domino from the boneyard to the tray
     * @param dominoDrawn The domino drawn from the boneyard
     */
    void draw(LinkedList dominoDrawn){
        this.playerDominoes.add(dominoDrawn);
    }

    /**
     * This is the amount of dominoes held by the player
     * @return
     */
    int dominoCount(){
        return this.playerDominoes.size();
    }

    /**
     * This is to play one of the players dominoes
     * @param playerChoice The domino chosen
     * @return The domino chosen
     */
    LinkedList play(int playerChoice){
        LinkedList<Collection> temp = new LinkedList<>();
        temp = this.playerDominoes.get(playerChoice);
        this.playerDominoes.remove(playerChoice);
        return temp;
    }

    /**
     * To clear the players tray
     */
    void clear(){
        this.playerDominoes.clear();
    }

    /**
     * To flip a domino in the players tray
     * @param position the position of the domino wished to be flipped
     */
    void flip(int position){
        LinkedList<LinkedList> temp1 = new LinkedList<>();
        LinkedList<Integer> temp2 = new LinkedList<>();
        temp1 = play(position);
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(1))));
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(0))));
        playerDominoes.add(position,temp2);
    }

    /**
     * To restart the game and reset the tray with new dominoes. Currently not finished, but
     * plan on added after assignment as it wasn't part of the assignment pdf
     * @param dominoes The dominoes being added to the tray after a restart
     */
    void restart(LinkedList dominoes){
        this.playerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    /**
     * The total count of values of the dominoes held by the player
     * @return The total count of values of the dominoes held by the player
     */
    int dominoesTotal(){
        int total = 0;
        for (int i = 0; i < dominoCount(); i++){
            total += (int) this.playerDominoes.get(i).getFirst();
            total += (int) this.playerDominoes.get(i).getLast();
        }
        return total;
    }

    /**
     * This is to make the Player Move. It checks if the player can move then moves.
     * @param boardFirst The first domino on the board
     * @param boardLast The last domino on the board
     * @return True if player can move, false if otherwise
     */
    boolean playerMove(LinkedList boardFirst, LinkedList boardLast){
        int position = -1;
        if (boardFirst == null){
            return true;
        }
        for (int i = 0; i < this.playerDominoes.size(); i++) {
            if (canPlayerMove(boardFirst,boardLast,this.playerDominoes.get(i),i)){
                return true;
            }
        }
        return false;
    }

    /**
     * This checks to see if the player can move. Just checks
     * @param boardFirst First domino on the board
     * @param boardLast Last domino on the board
     * @param domino The domino being checked
     * @param position The position of the domino
     * @return True if it can move, false if otherwise
     */
    boolean canPlayerMove(LinkedList boardFirst, LinkedList boardLast, LinkedList domino, int position){
        for (int i = 0; i < this.playerDominoes.size(); i++){
            if (domino.getFirst().equals(boardFirst.getFirst())){
                flip(position);
                return true;
            }
            else if (domino.getLast().equals(boardFirst.getFirst())){
                return true;
            }
            else if (domino.getFirst().equals(boardLast.getLast())){
                return true;
            }
            else if (domino.getLast().equals(boardLast.getLast())){
                flip(position);
                return true;
            }
            else if(domino.getFirst().equals(0)){
                return true;
            }
            else if (domino.getLast().equals(0)){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }



}
