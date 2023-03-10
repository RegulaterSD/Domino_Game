import java.util.Collection;
import java.util.LinkedList;

public class Computer {
    protected LinkedList<LinkedList> computerDominoes = new LinkedList<>();

    /**
     * This is the main computer constructor
     * @param dominoes This is the list of dominoes to initate the object with
     */
    Computer(LinkedList dominoes){
        this.computerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    /**
     * This is to add to the computer dominoes after it has drawn from the boneyard
     * @param dominoDrawn This is the domino drawn from the boneyard
     */
    void draw(LinkedList dominoDrawn){
        this.computerDominoes.add(dominoDrawn);
    }

    /**
     * This is to get the total dominos held by the computer
     * @return The int count of dominoes held by the computer
     */
    int dominoCount(){
        return this.computerDominoes.size();
    }

    /**
     * This is to play a domino from the computer.
     * @param computerChoice The domino being chosen
     * @return the domino being chosen
     */
    LinkedList play(int computerChoice){
        LinkedList<Collection> temp = new LinkedList<>();
        temp = this.computerDominoes.get(computerChoice);
        this.computerDominoes.remove(computerChoice);
        return temp;
    }

    /**
     * To clear the computers dominoes held
     */
    void clear(){
        this.computerDominoes.clear();
    }

    /**
     * To flip the computers domino
     * @param position THis is the position of the domino wished to be flipped
     */
    void flip(int position){
        LinkedList<LinkedList> temp1 = new LinkedList<>();
        LinkedList<Integer> temp2 = new LinkedList<>();
        temp1 = play(position);
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(1))));
        temp2.add(Integer.valueOf(String.valueOf(temp1.get(0))));
        computerDominoes.add(position,temp2);
    }

    /**
     * To restart the game, currently unused, but will add in future as it wasn't required in the pdf.
     * @param dominoes The dominoes being added back to the computer when a new game occurs
     */
    void restart(LinkedList dominoes){
        this.computerDominoes = (LinkedList<LinkedList>) dominoes.clone();
    }

    /**
     * This is to get the total count of all dominoes remaining held
     * @return the total count of all remaining dominoes held
     */
    int dominoesTotal(){
        int total = 0;
        for (int i = 0; i < dominoCount(); i++){
            total += (int) this.computerDominoes.get(i).getFirst();
            total += (int) this.computerDominoes.get(i).getLast();
        }
        return total;
    }

    /**
     * This is the position that can be moved to
     * @param boardFirst The board first domino
     * @param boardLast The boards last domino
     * @return The position that can be played.
     */
    int computerMove(LinkedList boardFirst, LinkedList boardLast){
        int position = -1;
        for (int i = 0; i < this.computerDominoes.size(); i++) {
            if (canComputerMove(boardFirst,boardLast,this.computerDominoes.get(i),i)){
                return i;
            }
        }
        return position;
    }

    /**
     * This is to check if the computer has a valid move
     * @param boardFirst The first domino on the board
     * @param boardLast The last domino on the board
     * @param domino The domino being checked
     * @param position The position being checked
     * @return True if it is a valid move, false if otherwise.
     */
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
