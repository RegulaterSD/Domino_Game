import java.util.Collection;
import java.util.LinkedList;

public class Player {
    protected LinkedList<LinkedList> playerDominoes = new LinkedList<>();

    Player(LinkedList dominoes){
        this.playerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    void draw(LinkedList dominoDrawn){
        this.playerDominoes.add(dominoDrawn);
    }

    int dominoCount(){
        return this.playerDominoes.size();
    }

    LinkedList play(int playerChoice){
        LinkedList<Collection> temp = new LinkedList<>();
        temp = this.playerDominoes.get(playerChoice);
        this.playerDominoes.remove(playerChoice);
        return temp;
    }

    void clear(){
        this.playerDominoes.clear();
    }

    void flip(int position){
        LinkedList<LinkedList> temp1 = new LinkedList<>();
        LinkedList<Integer> temp2 = new LinkedList<>();
        temp1 = play(position);
        System.out.println(temp1);
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(1))));
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(0))));
        System.out.println(temp2);
        playerDominoes.add(position,temp2);
    }

    void restart(LinkedList dominoes){
        this.playerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    int dominoesTotal(){
        int total = 0;
        for (int i = 0; i < dominoCount(); i++){
            total += (int) this.playerDominoes.get(i).getFirst();
            total += (int) this.playerDominoes.get(i).getLast();
        }
        return total;
    }

}
