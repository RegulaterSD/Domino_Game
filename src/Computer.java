import java.util.Collection;
import java.util.LinkedList;

public class Computer {
    protected LinkedList<LinkedList> computerDominoes = new LinkedList<>();

    Computer(LinkedList dominoes){
        this.computerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    void draw(LinkedList dominoDrawn){
        this.computerDominoes.add(dominoDrawn);
    }

    int dominoCount(){
        return this.computerDominoes.size();
    }

    LinkedList play(int computerChoice){
        LinkedList<Collection> temp = new LinkedList<>();
        temp = this.computerDominoes.get(computerChoice);
        this.computerDominoes.remove(computerChoice);
        return temp;
    }

    void clear(){
        this.computerDominoes.clear();
    }

    void flip(int position){
        LinkedList<LinkedList> temp1 = new LinkedList<>();
        LinkedList<Integer> temp2 = new LinkedList<>();
        temp1 = play(position);
        System.out.println("Flipping");
        System.out.println(temp1);
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(1))));
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(0))));
        System.out.println(temp2);
        computerDominoes.add(position,temp2);
        System.out.println("Done Flipping");
    }

    void restart(LinkedList dominoes){
        this.computerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    int dominoesTotal(){
        int total = 0;
        for (int i = 0; i < dominoCount(); i++){
            total += (int) this.computerDominoes.get(i).getFirst();
            total += (int) this.computerDominoes.get(i).getLast();
        }
        return total;
    }

    int computerMove(LinkedList boardFirst, LinkedList boardLast){
        int position = -1;
        for (int i = 0; i < this.computerDominoes.size(); i++) {
            if (canComputerMove(boardFirst,boardLast,this.computerDominoes.get(i),i)){
                return i;
            }
        }
        return position;
    }

    boolean canComputerMove(LinkedList boardFirst, LinkedList boardLast, LinkedList domino, int position){
        for (int i = 0; i < this.computerDominoes.size(); i++){
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
