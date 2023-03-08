import java.util.LinkedList;

public class Board {
    protected LinkedList<LinkedList> boardDominoes = new LinkedList<>();
    protected LinkedList<LinkedList> falseMove = new LinkedList<>();
    protected boolean topLast = false;
    Board(LinkedList dominoes){
        this.boardDominoes = (LinkedList<LinkedList>) dominoes.clone();
        this.falseMove = (LinkedList<LinkedList>) dominoes.clone();
    }

    void add(LinkedList domino, String leftOrRight){
        if (leftOrRight.matches("l") || leftOrRight.matches("L")){
            this.boardDominoes.addFirst(domino);
        }
        else if (leftOrRight.matches("r") || leftOrRight.matches("R")){
            this.boardDominoes.addLast(domino);
        }
    }

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

    int total(){
        return (this.boardDominoes.size());
    }

    void clear(){
        this.boardDominoes.clear();
    }
}