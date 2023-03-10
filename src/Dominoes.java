import java.util.LinkedList;

public class Dominoes {
    LinkedList<LinkedList> currentDominoes = new LinkedList<>();

    /**
     * The main dominoes constructor to build all possible dominoes
     */
    Dominoes() {
        this.currentDominoes = build();
    }

    /**
     * To build all 28 possible dominoes
     * @return A linked list with 28 dominoes
     */
    LinkedList build(){
        LinkedList<LinkedList> allDominoes = new LinkedList<>();
        for (int i = 0; i <= 6; i++){
            for (int j = i; j <= 6; j++ ){
                LinkedList<Integer> domino = new LinkedList<>();
                domino.add(Integer.valueOf(i));
                domino.add(Integer.valueOf(j));
                allDominoes.add((LinkedList) domino.clone());
                domino.clear();
            }
        }
        return allDominoes;
    }

    /**
     * This is to remove dominoes from the linked list if certain ones not allowed
     * @param position Position of domino being removed
     * @return The domino removed
     */
    LinkedList remove(int position){
        LinkedList<LinkedList> temp = new LinkedList<>();
        temp.add(this.currentDominoes.get(position));
        this.currentDominoes.remove(position);
        return temp.get(0);
    }

    /**
     * The size of the dominoes in total
     * @return The size of the dominoes in total
     */
    int size(){
        return this.currentDominoes.size();
    }

    /**
     * to clear the list in case of possible restart
     */
    void clear(){
        this.currentDominoes.clear();
    }
}
