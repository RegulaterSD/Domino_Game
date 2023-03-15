import java.util.LinkedList;

public class Board {
    protected LinkedList<LinkedList> boardDominoes = new LinkedList<>();
    protected LinkedList<LinkedList> falseMove = new LinkedList<>();

    /**
     * This is the main board constructor
     * @param dominoes Takes in the dominoes being assigned to the board.
     */
    Board(LinkedList dominoes){
        this.boardDominoes = (LinkedList<LinkedList>) dominoes.clone();
        this.falseMove = (LinkedList<LinkedList>) dominoes.clone();
    }

    /**
     * This is to add to the board linked list
     * @param domino The domino being added
     * @param leftOrRight Which direction the player played it
     */
    void add(LinkedList domino, String leftOrRight){
        if (leftOrRight.matches("l") || leftOrRight.matches("L")){
            this.boardDominoes.addFirst(domino);
        }
        else if (leftOrRight.matches("r") || leftOrRight.matches("R")){
            this.boardDominoes.addLast(domino);
        }
    }

    /**
     * This is to check if the move is valid on the board
     * @param domino The domino being played
     * @param leftOrRight The direction being played
     * @return True if valid, false if otherwise
     */
    boolean checkMove(LinkedList domino, String leftOrRight){
        if (leftOrRight.matches("l") || leftOrRight.matches("L")){
            if (total() == 0){
                add(domino,leftOrRight);
                return true;
            }
            else if (boardDominoes.getFirst().get(0).equals(domino.get(1))){
                add(domino,leftOrRight);
                return true;
            }
            else if (domino.get(1).equals(0)){
                add(domino,leftOrRight);
                return true;
            }
            else {
                falseMove.add(domino);
                return false;
            }
        }
        else if (leftOrRight.matches("r") || leftOrRight.matches("R")){
            if (total() == 0){
                add(domino,leftOrRight);
                return true;
            }
            else if (boardDominoes.getLast().get(1).equals(domino.get(0))){
                add(domino,leftOrRight);
                return true;
            }
            else if (domino.get(0).equals(0)){
                add(domino,leftOrRight);
                return true;
            }
            else {
                falseMove.add(domino);
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Returns the total dominoes on the board
     * @return Total dominoes on the board in int form
     */
    int total(){
        return (this.boardDominoes.size());
    }

    /**
     * This is to clear the board.
     */
    void clear(){
        this.boardDominoes.clear();
    }
}