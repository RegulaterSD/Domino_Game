import java.util.LinkedList;

public class Project3a_seandavies {
    static LinkedList<LinkedList> boardStart = new LinkedList<>();
    static LinkedList<LinkedList> playerStart = new LinkedList<>();
    static LinkedList<LinkedList> computerStart = new LinkedList<>();
    static LinkedList<LinkedList> boneyardStart = new LinkedList<>();
    static boolean playerMoved = false;
    static boolean computerMoved = true;
    static int computerPosition;
    static int deadMove = 0;
    static char humanChoice;
    static char lOrR;
    static char rotate;
    static int domino;

    public static void main(String[] args) {

        //To initialize the game
        DominoManager dm1 = new DominoManager();
        Dominoes d1 = new Dominoes();
        dm1.initGame(boardStart,playerStart,computerStart,boneyardStart, d1);
        Player p1 = new Player(playerStart);
        Computer c1 = new Computer(computerStart);
        Boneyard b1 = new Boneyard(boneyardStart);
        Board board1 = new Board(boardStart);

        originalDisplay(c1,b1);
        display(board1);
        humanTurnDisplay(p1);


//        p1.flip(0);
//        System.out.println("Player: " + p1.playerDominoes);
//        c1.flip(1);
//        System.out.println("Computer: " + c1.computerDominoes);
//        System.out.println("Board: " + board1.boardDominoes);
//        board1.checkMove(p1.play(0),'l');
//        System.out.println("Board: " + board1.boardDominoes);
//        System.out.println("Player: " + p1.playerDominoes);
//        System.out.println("Computer: " + c1.computerDominoes);
//        if (!board1.checkMove(c1.play(0),'l')) {
//            System.out.println("False Moves: " + board1.falseMove);
//            c1.computerDominoes.add(0,board1.falseMove.get(0));
//            board1.falseMove.clear();
//        }
//        System.out.println("Board: " + board1.boardDominoes);
//        System.out.println("Player: " + p1.playerDominoes);
//        System.out.println("Computer: " + c1.computerDominoes);
//        System.out.println("False Moves: " + board1.falseMove);
    }

    static void displayerDebug(Player p1, Computer c1, Boneyard b1, Board board1, Dominoes d1){
        System.out.println("All Dominoes: " + d1.currentDominoes);
        System.out.println("All Dominoes size: " + d1.currentDominoes.size());
        System.out.println("Player: " + p1.playerDominoes);
        System.out.println("Player size: " + p1.dominoCount());
        System.out.println("Computer: " + c1.computerDominoes);
        System.out.println("Computer size: " + c1.dominoCount());
        System.out.println("Boneyard: " + b1.boneyardDominoes);
        System.out.println("Boneyard size: " + b1.dominoCount());
        System.out.println("Board: " + board1.boardDominoes);
        System.out.println("Board size: " + board1.boardDominoes.size());
    }

    static void computerMove(Computer c1, Board board1, Boneyard b1){
        System.out.println("Computer move: " + c1.computerMove(board1.boardDominoes.getFirst(),board1.boardDominoes.getLast()));
        computerPosition = c1.computerMove(board1.boardDominoes.getFirst(),board1.boardDominoes.getLast());
        System.out.println("Computer Position: " + computerPosition);
        if (computerPosition == -1){
            if (b1.boneyardDominoes.size() != 0) {
                System.out.println("Drawing from BoneYard");
                c1.draw(b1.getBoneyard());
            }
            else {
                System.out.println("DeadMove");
                deadMove++;
            }
        }
        else {
            if (!board1.checkMove(c1.play(computerPosition), 'l')) {
                System.out.println("False Moves: " + board1.falseMove);
                c1.computerDominoes.add(computerPosition, board1.falseMove.get(0));
                board1.falseMove.clear();
                System.out.println("Moved right");
                board1.checkMove(c1.play(computerPosition), 'r');
                computerPosition = -1;
                deadMove = 0;
            }
        }
    }

    static void playerMove(){

    }

    static void display(Board board1){
        LinkedList<LinkedList> line1 = new LinkedList<>();
        LinkedList<LinkedList> line2 = new LinkedList<>();
        for (int i = 0; i < board1.boardDominoes.size(); i++){
            if (i%2 == 0){
                line1.add(board1.boardDominoes.get(i));
            }
            else {
                line2.add(board1.boardDominoes.get(i));
            }
        }
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (Object object : line1)
        {
            sb1.append(object.toString());
        }
        for (Object object : line2)
        {
            sb2.append(object.toString());
        }
        String temp1 = sb1.toString().replaceAll(","," ");
        String temp2 = sb2.toString().replaceAll(","," ");
        System.out.println(temp1);
        System.out.println("   " + temp2);
    }

    static void humanTurnDisplay(Player p1){
        System.out.println("Tray: " + p1.playerDominoes);
        System.out.println("Human’s turn");
        System.out.println("[p] Play Domino");
        System.out.println("[d] Draw from boneyard");
        System.out.println("[q] Quit");
    }

    static void originalDisplay(Computer c1, Boneyard b1){
        System.out.println("Computer has "+ c1.dominoCount() + " dominoes");
        System.out.println("Boneyard contains " + b1.boneyardDominoes.size() + " dominoes");
    }

}
