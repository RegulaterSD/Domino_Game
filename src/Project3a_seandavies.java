import java.util.LinkedList;
import java.util.Scanner;

public class Project3a_seandavies {
    static LinkedList<LinkedList> boardStart = new LinkedList<>();
    static LinkedList<LinkedList> playerStart = new LinkedList<>();
    static LinkedList<LinkedList> computerStart = new LinkedList<>();
    static LinkedList<LinkedList> boneyardStart = new LinkedList<>();
    static LinkedList<LinkedList> line1 = new LinkedList<>();
    static LinkedList<LinkedList> line2 = new LinkedList<>();
    static boolean playerMoved = false;
    static boolean computerMoved = true;
    static int computerPosition;
    static int deadMove = 0;
    static String humanChoice;
    static String lOrR;
    static String rotate;
    static int dominoNumber;
    static boolean gameOver = false;
    static int left = 0;
    static int right = 0;
    static boolean playerForfeit = false;

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
        display(line1,line2);
        humanTurnDisplay(p1);
        while (!dm1.isGameOver(gameOver,p1,c1)){
            playerChoices(p1);
            System.out.println(humanChoice);
            System.out.println(dominoNumber);
            System.out.println(lOrR);
            System.out.println(rotate);
            playerInput(p1,board1,b1);
            computerMove(c1,board1,b1);
            display(line1,line2);
            humanTurnDisplay(p1);
        }
//        playerInput(p1,board1,b1);
//        computerMove(c1,board1,b1);
//        display(line1,line2);
//        humanTurnDisplay(p1);
//        playerChoices();
//        playerInput(p1,board1,b1);
//        display(line1,line2);
//        humanTurnDisplay(p1);
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
            if (!board1.checkMove(c1.play(computerPosition), "l")) {
                System.out.println("False Moves: " + board1.falseMove);
                c1.computerDominoes.add(computerPosition, board1.falseMove.get(0));
                board1.falseMove.clear();
                System.out.println("Moved right");
                board1.checkMove(c1.play(computerPosition), "r");
                computerPosition = -1;
                deadMove = 0;
                right++;
                addToLines(line1,line2,right,left,board1.boardDominoes.getLast(),'r');
            }
            else {
                left++;
                addToLines(line1,line2,right,left,board1.boardDominoes.getFirst(),'l');
            }
        }
    }

    static void playerChoices(Player p1){
        Scanner sc = new Scanner(System.in);
        humanChoice = sc.nextLine();
        if (humanChoice.matches("p")) {
            System.out.println("Which domino?");
            dominoNumber = Integer.parseInt(sc.nextLine());
            while (dominoNumber >= p1.dominoCount() || dominoNumber < 0){
                System.out.println("Please enter a valid Domino number 0 - " + (p1.dominoCount() - 1));
                dominoNumber = Integer.parseInt(sc.nextLine());
            }
            System.out.println("Left or Right? (l/r)");
            lOrR = sc.nextLine();
            while (!lOrR.matches("l") && !lOrR.matches("r")){
                System.out.println("Please enter a valid option (l/r)");
                lOrR = sc.nextLine();
            }
            System.out.println("Rotate first? (y/n)");
            rotate = sc.nextLine();
            while (!rotate.matches("y") && !rotate.matches("n")){
                System.out.println("Please enter a valid option (y/n)");
                rotate = sc.nextLine();
            }
        }
        else if (!humanChoice.matches("p") && !humanChoice.matches("q") && !humanChoice.matches("d")){
            System.out.println("Please enter a valid  (p/d/q)");
            playerChoices(p1);
        }
    }

    static void playerInput(Player p1, Board board1, Boneyard b1){
        Scanner sc = new Scanner(System.in);
        if (left != 0 || right != 0){
            if (lOrR.matches("r")){
                right++;
                System.out.println("Right increased to: " + right);
            }
            else if (lOrR.matches("l")){
                left++;
                System.out.println("Left increased to: " + left);
            }
        }
        switch (humanChoice) {
            case "p" -> {
                System.out.println("Player chose to play");
                if (rotate.matches("y")) {
                    p1.flip(dominoNumber);
                    board1.checkMove(p1.play(dominoNumber), lOrR);
                    if (lOrR.matches("l")){
                        addToLines(line1,line2,right,left,board1.boardDominoes.getFirst(),'l');
                    }
                    else if (lOrR.matches("r")){
                        addToLines(line1,line2,right,left,board1.boardDominoes.getLast(),'r');
                    }
                } else if (rotate.matches("n")) {
                    board1.checkMove(p1.play(dominoNumber), lOrR);
                    if (lOrR.matches("l")){
                        addToLines(line1,line2,right,left,board1.boardDominoes.getFirst(),'l');
                    }
                    else if (lOrR.matches("r")){
                        addToLines(line1,line2,right,left,board1.boardDominoes.getLast(),'r');
                    }
                }
            }
            case "d" -> {
                System.out.println("Player chose to draw from boneyard");
                if (board1.boardDominoes.size() != 0) {
                    if (!p1.playerMove(board1.boardDominoes.getFirst(), board1.boardDominoes.getLast())) {
                        if (b1.boneyardDominoes.size() != 0) {
                            System.out.println("Drawing from BoneYard");
                            p1.draw(b1.getBoneyard());
                        } else {
                            System.out.println("DeadMove");
                            deadMove++;
                        }
                    }
                } else {
                    System.out.println("Cannot draw, you have a valid move possible");
                }
            }
            case "q" -> {
                playerForfeit = true;
                gameOver = true;
            }
            default -> {
            }
        }
    }

    static void addToLines(LinkedList line1, LinkedList line2, int right, int left, LinkedList domino, char lOrR){
        System.out.println("Right: " + right + " Left: " + left);
        if (right%2 != 0 && lOrR == 'r'){
            line2.addLast(domino);
        }
        else if (right%2 == 0 && lOrR == 'r'){
            line1.addLast(domino);
        }
        else if (left%2 != 0 && lOrR == 'l'){
            line2.addFirst(domino);
        }
        else if (left%2 == 0 && lOrR == 'l'){
            line1.addFirst(domino);
        }
    }

    static void display(LinkedList line1, LinkedList line2){
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
        String indent = "   ";
        String temp1 = sb1.toString().replaceAll(","," ");
        String temp2 = sb2.toString().replaceAll(","," ");
        if (left%2 == 0) {
            System.out.println(temp1);
            System.out.println(indent + temp2);
        }
        else {
            System.out.println(indent + temp1);
            System.out.println(temp2);
        }
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
