import java.util.LinkedList;

public class Dominoes {
    LinkedList<LinkedList> currentDominoes = new LinkedList<>();

    Dominoes() {
        this.currentDominoes = build();
    }

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

    LinkedList remove(int position){
        LinkedList<LinkedList> temp = new LinkedList<>();
        temp.add(this.currentDominoes.get(position));
        this.currentDominoes.remove(position);
        return temp.get(0);
    }

    int size(){
        return this.currentDominoes.size();
    }

    void clear(){
        this.currentDominoes.clear();
    }
}
