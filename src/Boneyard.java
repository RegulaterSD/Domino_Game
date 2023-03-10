import java.util.Collection;
import java.util.LinkedList;

public class Boneyard {
    protected LinkedList<LinkedList> boneyardDominoes = new LinkedList<>();

    /**
     * This is the main Boneyard Constructor
     * @param dominoes Takes in the dominoes being added to the boneyard
     */
    Boneyard(LinkedList dominoes){
        this.boneyardDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    /**
     * This is the total dominoes in the boneyard
     * @return the total dominoes in the boneyard in int form
     */
    int dominoCount(){
        return boneyardDominoes.size();
    }

    /**
     * This is to get a domino from the boneyard
     * @return A domino from the boneyard
     */
    LinkedList getBoneyard(){
        if (boneyardDominoes.size() != 0) {
            LinkedList<Collection> temp = new LinkedList<>();
            temp = boneyardDominoes.getFirst();
            boneyardDominoes.removeFirst();
            return temp;
        }
        else {
            return null;
        }
    }

    /**
     * This is to clear the boneyard
     */
    void clear(){
        this.boneyardDominoes.clear();
    }

    /**
     * This is to restart the boneyard
     * @param dominoes The dominoes being added tot he boneyard
     */
    void restart(LinkedList dominoes){
        this.boneyardDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }
}
