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
