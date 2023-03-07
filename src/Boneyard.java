import java.util.Collection;
import java.util.LinkedList;

public class Boneyard {
    protected LinkedList<LinkedList> boneyardDominoes = new LinkedList<>();

    Boneyard(LinkedList dominoes){
        this.boneyardDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    int dominoCount(){
        return boneyardDominoes.size();
    }

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

    void clear(){
        this.boneyardDominoes.clear();
    }

    void restart(LinkedList dominoes){
        this.boneyardDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }
}
