/**
 * Sean Davies
 * CS351L
 * Project 3 Part A
 * Dominoes through command line
 */

import java.util.LinkedList;
import java.util.Scanner;

public class Project3a_seandavies {
    private static LinkedList<LinkedList> boardStart = new LinkedList<>();
    private static LinkedList<LinkedList> playerStart = new LinkedList<>();
    private static LinkedList<LinkedList> computerStart = new LinkedList<>();
    private static LinkedList<LinkedList> boneyardStart = new LinkedList<>();
    private static LinkedList<LinkedList> line1 = new LinkedList<>();
    private static LinkedList<LinkedList> line2 = new LinkedList<>();
    private static int computerPosition;
    private static int deadMove = 0;
    private static String humanChoice;
    private static String lOrR;
    private static String rotate;
    private static int dominoNumber;
    private static boolean gameOver = false;
    private static int left = 0;
    private static int right = 0;
    private static boolean playerForfeit = false;

    /**
     * This is where the main logic of the game is. Updating the board
     * Building the objects.
     * @param args
     */
    public static void main(String[] args) {

        //To initialize the game
        DominoManager dm1 = new DominoManager();
        Dominoes d1 = new Dominoes();
        dm1.initGame(boardStart,playerStart,computerStart,boneyardStart, d1);
        Player p1 = new Player(playerStart);
        Computer c1 = new Computer(computerStart);
        Boneyard b1 = new Boneyard(boneyardStart);
        Board board1 = new Board(boardStart);

        while (!dm1.isGameOver(gameOver,p1,c1)){
            originalDisplay(c1,b1);
            display(line1,line2);
            humanTurnDisplay(p1);
            playerChoices(p1,board1);
            playerInput(p1, board1, b1);
            originalDisplay(c1,b1);
            if (!humanChoice.matches("q")) {
                computerMove(c1, board1, b1);
                display(line1, line2);
                humanTurnDisplay(p1);
            }
            display(line1, line2);
        }
        System.out.println(dm1.endGame(p1,c1,b1,playerForfeit));
    }


    /**
     * This is to make the computer move. It checks for all valid moves, and will perform
     * the move if it finds a valid move. If no valid move it draws, or does a dead draw.
     * @param c1 This is the Computer Object
     * @param board1 This is the Board Object
     * @param b1 This is the Boneyard Object
     */
    static void computerMove(Computer c1, Board board1, Boneyard b1){
        computerPosition = c1.computerMove(board1.boardDominoes.getFirst(),board1.boardDominoes.getLast());
        if (computerPosition == -1){
            if (b1.boneyardDominoes.size() != 0) {
                System.out.println("Computer drawing from BoneYard");
                c1.draw(b1.getBoneyard());
            }
            else {
                System.out.println("DeadMove");
                deadMove++;
            }
        }
        else {
            if (!board1.checkMove(c1.play(computerPosition), "l")) {
                c1.computerDominoes.add(computerPosition, board1.falseMove.get(0));
                board1.falseMove.clear();
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

    /**
     * This is to receive in the Player Choices through the command line
     * @param p1 This is the Player Object
     * @param board1 This is the Board Object
     */
    static void playerChoices(Player p1, Board board1){
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
        else if (!humanChoice.matches("p") && !humanChoice.matches("q")
                && !humanChoice.matches("d")){
            System.out.println("Please enter a valid  (p/d/q)");
            playerChoices(p1,board1);
        }
        else if (humanChoice.matches("d") && board1.boardDominoes.size() == 0){
            System.out.println("You cannot draw you have a valid move");
            playerChoices(p1,board1);
        }
    }

    /**
     * This is to take the Player Input and make the move that the player chose. Checks
     * if it is valid, or if there is a valid move and makes the player perform
     * the valid move or keep trying until they do.
     * @param p1 Player Object
     * @param board1 Board Object
     * @param b1 Boneyard Object
     */
    static void playerInput(Player p1, Board board1, Boneyard b1){
        Scanner sc = new Scanner(System.in);
        if ((left != 0 || right != 0) && humanChoice.matches("p")){
            if (lOrR.matches("r")){
                right++;
            }
            else if (lOrR.matches("l")){
                left++;
            }
        }
        switch (humanChoice) {
            case "p" -> {
                System.out.println("Player chose to play");
                if (rotate.matches("y")) {
                    p1.flip(dominoNumber);
                    if (!board1.checkMove(p1.play(dominoNumber), lOrR)){
                        System.out.println("Player did not do a valid move!");
                        p1.playerDominoes.add(dominoNumber, board1.falseMove.get(0));
                        board1.falseMove.clear();
                        System.out.println(p1.playerDominoes);
                        humanChoice = "";
                        playerChoices(p1,board1);
                    }
                    if (lOrR.matches("l")){
                        addToLines(line1,line2,right,left,board1.boardDominoes.getFirst(),'l');
                    }
                    else if (lOrR.matches("r")){
                        addToLines(line1,line2,right,left,board1.boardDominoes.getLast(),'r');
                    }
                } else if (rotate.matches("n")) {
                    if (!board1.checkMove(p1.play(dominoNumber), lOrR)){
                        System.out.println("Player did not do a valid move!!!");
                        p1.playerDominoes.add(dominoNumber, board1.falseMove.get(0));
                        board1.falseMove.clear();
                        System.out.println(p1.playerDominoes);
                        humanChoice = "";
                        playerChoices(p1,board1);
                    }
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
                    else {
                        while (humanChoice.matches("d")) {
                            System.out.println("Cannot draw you have a valid move possible");
                            playerChoices(p1, board1);
                        }
                    }
                } else {
                    System.out.println("Cannot draw, you have a valid move possible");
                }
            }
            case "q" -> {
                System.out.println("Player chose to quit");
                playerForfeit = true;
                gameOver = true;
            }
            default -> {
            }
        }
    }

    /**
     * This is where I add the dominoes to specific lines for drawing to the
     * command line.
     * @param line1 This is the top line of the dominoes to be displayed
     * @param line2 This is the bottom line of the dominoes to be displayed
     * @param right This is the right count
     * @param left This is the left count
     * @param domino This is the domino being added
     * @param lOrR This is if the user moved left or right
     */
    static void addToLines(LinkedList line1, LinkedList line2, int right,
                           int left, LinkedList domino, char lOrR){
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

    /**
     * This is to display the board lines to the command line
     * @param line1 This is the top line
     * @param line2 This is the bottom line
     */
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

    /**
     * This is to display the basic information before the players turn
     * @param p1 This is the Player Object
     */
    static void humanTurnDisplay(Player p1){
        System.out.println("Tray: " + p1.playerDominoes);
        System.out.println("Human’s turn");
        System.out.println("[p] Play Domino");
        System.out.println("[d] Draw from boneyard");
        System.out.println("[q] Quit");
    }

    /**
     * This is the display the Boneyard and Computer domino counts
     * @param c1 Computer Object
     * @param b1 Boneyard Object
     */
    static void originalDisplay(Computer c1, Boneyard b1){
        System.out.println("Computer has "+ c1.dominoCount() + " dominoes");
        System.out.println("Boneyard contains " + b1.boneyardDominoes.size() + " dominoes");
    }

}
