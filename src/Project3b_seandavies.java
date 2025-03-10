/**
 * Sean Davies
 * CS351L
 * Project 3 Part B
 * Dominoes with GUI
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.Duration;
import java.util.LinkedList;

public class Project3b_seandavies extends Application {
    private int xPosition;
    private int yPosition;
    private static String lOrR = "";
    private static String computerLOrR = "";
    private static LinkedList<LinkedList> boardStart = new LinkedList<>();
    private static LinkedList<LinkedList> playerStart = new LinkedList<>();
    private static LinkedList<LinkedList> computerStart = new LinkedList<>();
    private static LinkedList<LinkedList> boneyardStart = new LinkedList<>();
    private static LinkedList<LinkedList> line1 = new LinkedList<>();
    private static LinkedList<LinkedList> line2 = new LinkedList<>();
    private static int dominoNumber;
    private static boolean gameOver = false;
    private static int left = 0;
    private static int right = 0;
    private static boolean drawing = false;
    private static boolean dominoChosen = false;
    private static boolean lOrRightChosen = false;
    private static boolean rotateChosen = false;

    private static int deadMove = 0;
    private static int computerPosition;
    private static boolean didDraw = false;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //To initialize the game
        DominoManager dm1 = new DominoManager();
        Dominoes d1 = new Dominoes();
        dm1.initGame(boardStart,playerStart,computerStart,boneyardStart, d1);
        Player p1 = new Player(playerStart);
        Computer c1 = new Computer(computerStart);
        Boneyard b1 = new Boneyard(boneyardStart);
        Board board1 = new Board(boardStart);

        stage.setTitle("Dominoes");
        Canvas canvas = new Canvas(1500, 250);
        StackPane stackPane = new StackPane();
        VBox root = new VBox();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(255,255,255));
        gc.fillRect(0,0,1500,250);

        Label boneyardLabel = new Label("Boneyard contains " + b1.dominoCount() + " dominoes");
        boneyardLabel.setStyle("-fx-font: 24 arial;");
        Label computerLabel = new Label("Computer has " + c1.dominoCount() + " dominoes");
        computerLabel.setStyle("-fx-font: 24 arial;");

        Button flipButton = new Button("Flip Domino");
        flipButton.setStyle("-fx-font: 24 arial;");
        flipButton.setOnAction(EventHandler -> {
            rotateChosen = true;
        });
        Button leftButton = new Button("Play Left");
        leftButton.setStyle("-fx-font: 24 arial;");
        leftButton.setOnAction(EventHandler -> {
            lOrR = "l";
            lOrRightChosen = true;
        });
        Button rightButton = new Button("Play Right");
        rightButton.setStyle("-fx-font: 24 arial;");
        rightButton.setOnAction(EventHandler -> {
            lOrR = "r";
            lOrRightChosen = true;
        });
        Button drawBoneyard = new Button("Draw from boneyard");
        drawBoneyard.setStyle("-fx-font: 24 arial;");
        drawBoneyard.setOnAction(EventHandler -> {
            drawing = true;
            playerInput(p1,board1,b1);
            if (didDraw) {
                computerMove(c1, board1, b1);
                didDraw = false;
            }
        });


        AnimationTimer animationTimer = new AnimationTimer() {
            private long startTime = -1;
            @Override
            public void handle(long now) {
                if (startTime < 0){
                    startTime = now;
                }
                Duration elapsed = Duration.ofNanos(now - startTime);
                boneyardLabel.setText("Boneyard contains " + b1.dominoCount() + " dominoes");
                computerLabel.setText("Computer has " + c1.dominoCount() + " dominoes");
                if (deadMove == 2){
                    gameOver = true;
                }
                if (dm1.isGameOver(gameOver,p1,c1)){
                    gc.setFill(Color.rgb(255,255,255));
                    gc.fillRect(0,0,1500,250);
                    gc.setStroke(Color.rgb(0,0,0));
                    gc.setFont(Font.font(36));
                    gc.setFill(Color.rgb(0,0,0));
                    gc.fillText(dm1.endGame(p1,c1,b1,false),50,100);
                    super.stop();
                }
                else {
                    if (rotateChosen && dominoChosen) {
                        p1.flip(dominoNumber);
                        rotateChosen = false;
                        drawDominoes(canvas, p1);
                    }
                    if (dominoChosen && lOrRightChosen) {
                        playerInput(p1, board1, b1);
                        lOrRightChosen = false;
                        dominoChosen = false;
                        computerMove(c1, board1, b1);
                        drawDominoes(canvas, p1);
                    }
                    drawDominoes(canvas, p1);
                }
            }
        };
        drawDominoes(canvas,p1);
        animationTimer.start();

        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xPosition = (int) event.getSceneX();
                yPosition = (int) event.getSceneY();
                if (yPosition >= 190 && xPosition >= 50 && xPosition <= (((p1.dominoCount()) * 100)+50)){
                    dominoNumber = (xPosition - 50)/100;
                    dominoChosen = true;
                }
            }
        });



        stage.setScene(new Scene(root,1500,350));
        root.getChildren().add(boneyardLabel);
        root.getChildren().add(computerLabel);
        stackPane.getChildren().add(canvas);
        root.getChildren().add(stackPane);
        GridPane buttonPane = new GridPane();
        GridPane.setConstraints(flipButton,0,0);
        GridPane.setConstraints(leftButton,1,0);
        GridPane.setConstraints(rightButton, 2, 0);
        GridPane.setConstraints(drawBoneyard, 3, 0);
        buttonPane.getChildren().addAll(flipButton,leftButton,rightButton,drawBoneyard);
        root.getChildren().add(buttonPane);
        root.setAlignment(Pos.TOP_CENTER);
        stage.show();
    }

    /**
     * This is to make the computer move. It checks for all valid moves, and will perform
     * the move if it finds a valid move. If no valid move it draws, or does a dead draw.
     * @param c1 This is the Computer Object
     * @param board1 This is the Board Object
     * @param b1 This is the Boneyard Object
     */
    private static void computerMove(Computer c1, Board board1, Boneyard b1){
        computerPosition = c1.computerMove(board1.boardDominoes.getFirst(),board1.boardDominoes.getLast());
        if (computerPosition == -1){
            if (b1.boneyardDominoes.size() != 0) {
                c1.draw(b1.getBoneyard());
            }
            else {
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
                computerLOrR = "r";
                addToLines(board1.boardDominoes.getLast());
            }
            else {
                left++;
                computerLOrR = "l";
                addToLines(board1.boardDominoes.getFirst());
            }
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
    private static void playerInput(Player p1, Board board1, Boneyard b1){
        //Player selected a domino and left or right
        if (dominoChosen && lOrRightChosen) {
            if (!board1.checkMove(p1.play(dominoNumber), lOrR)) {
                p1.playerDominoes.add(dominoNumber, board1.falseMove.get(0));
                board1.falseMove.clear();
                lOrRightChosen = false;
            }
            if (lOrRightChosen) {
                if (lOrR.matches("l")) {
                    if ((left != 0 || right != 0)) {
                        left++;
                    }
                    addToLines(board1.boardDominoes.getFirst());
                } else if (lOrR.matches("r")) {
                    if ((left != 0 || right != 0)) {
                        right++;
                    }
                    addToLines(board1.boardDominoes.getLast());
                }
            }
        }
        //Player chose to draw
        if (drawing) {
            if (board1.boardDominoes.size() != 0) {
                if (!p1.playerMove(board1.boardDominoes.getFirst(), board1.boardDominoes.getLast())) {
                    if (b1.boneyardDominoes.size() != 0) {
                        p1.draw(b1.getBoneyard());
                        drawing = false;
                        didDraw = true;
                    } else {
                        deadMove++;
                        drawing = false;
                        didDraw = true;
                    }
                } else {
                    while (drawing) {
                        drawing = false;
                    }
                }
            } else {
                drawing = false;
            }
        }
    }

    /**
     * This is where I draw the dominoes on the canvas. It draws both lines
     * and the users dominoes through loops.
     * @param canvas This is the canvas being drawn to
     * @param p1 This is the Player held dominoes
     */
    private static void drawDominoes(Canvas canvas, Player p1){
        int dominoWidth = 100;
        int dominoHalf = dominoWidth/2;
        int dominoHeight = dominoWidth/2;
        int dotRadius = dominoWidth/10;
        int lineWidth = 4;
        int lineStart = 48;
        int boardOne = 50;
        int boardTwo = 100;
        int boardStart = 50;
        int userDepth = 190;
        int userStart = 50;
        int line1Indent = 0;
        int line2Indent = 0;

        if (left%2 == 0) {
            line1Indent = 0;
            line2Indent = 50;
        }
        else {
            line1Indent = 50;
            line2Indent = 0;
        }

        LinkedList<Integer> topLine = new LinkedList<>();
        for (LinkedList l : line1){
            for (Object o : l){
                topLine.add(Integer.valueOf(String.valueOf(o)));
            }
        }
        LinkedList<Integer> bottomLine = new LinkedList<>();
        for (LinkedList l : line2){
            for (Object o : l){
                bottomLine.add(Integer.valueOf(String.valueOf(o)));
            }
        }
        LinkedList<Integer> userLine = new LinkedList<>();
        for (LinkedList l : p1.playerDominoes){
            for (Object o : l){
                userLine.add(Integer.valueOf(String.valueOf(o)));
            }
        }

        Integer dotCount;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(255,255,255));
        gc.fillRect(0,0,1500,350);
        Color dominoColor = Color.rgb(222,185,101);
        Color dotColor = Color.rgb(0,0,255);
        Color lineColor = Color.rgb(0,0,0);

        for (Integer i = 0; i < topLine.size()/2; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(boardStart + (dominoWidth * i) + line1Indent,boardOne,
                    dominoWidth,dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(boardStart + (dominoWidth * i) + lineStart + line1Indent,
                    boardOne,lineWidth,dominoHeight);
            gc.strokeRoundRect(boardStart + (dominoWidth * i) + line1Indent,boardOne,
                    dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        for (Integer i = 0; i < topLine.size(); i++){
            dotCount = topLine.get(i);
            gc.setFill(dotColor);
            switch (dotCount){
                case 0 -> {
                }
                case 1 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 20 + line1Indent),
                            boardOne + 20,dotRadius,dotRadius);
                }
                case 2 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                }
                case 3 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20 + line1Indent),
                            boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                }
                case 4 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                }
                case 5 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20 + line1Indent),
                            boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                }
                case 6 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35 + line1Indent),
                            boardOne + 35,dotRadius,dotRadius);
                }
                default -> {

                }
            }
        }

        for (Integer i = 0; i < bottomLine.size()/2; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(boardStart + (dominoWidth * i) + line2Indent,boardTwo,
                    dominoWidth,dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(boardStart + (dominoWidth * i) + lineStart + line2Indent,
                    boardTwo,lineWidth,dominoHeight);
            gc.strokeRoundRect(boardStart + (dominoWidth * i) + line2Indent,boardTwo,
                    dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        for (Integer i = 0; i < bottomLine.size(); i++) {
            dotCount = bottomLine.get(i);
            gc.setFill(dotColor);
            switch (dotCount) {
                case 0 -> {
                }
                case 1 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 20 + line2Indent),
                            boardTwo + 20, dotRadius, dotRadius);
                }
                case 2 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                }
                case 3 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20) + line2Indent,
                            boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                }
                case 4 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                }
                case 5 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20) + line2Indent,
                            boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                }
                case 6 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35) + line2Indent,
                            boardTwo + 35, dotRadius, dotRadius);
                }
                default -> {

                }
            }
        }

        for (Integer i = 0; i < userLine.size()/2; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(userStart + (dominoWidth * i),userDepth,dominoWidth,
                    dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(userStart + (dominoWidth * i) + lineStart,userDepth,
                    lineWidth,dominoHeight);
            gc.strokeRoundRect(userStart + (dominoWidth * i),userDepth,
                    dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        for (Integer i = 0; i < userLine.size(); i++) {
            dotCount = userLine.get(i);
            gc.setFill(dotColor);
            switch (dotCount) {
                case 0 -> {
                }
                case 1 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 20),
                            userDepth + 20, dotRadius, dotRadius);
                }
                case 2 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 35, dotRadius, dotRadius);
                }
                case 3 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 20),
                            userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 35, dotRadius, dotRadius);
                }
                case 4 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 35, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 35, dotRadius, dotRadius);
                }
                case 5 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 20),
                            userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 35, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 35, dotRadius, dotRadius);
                }
                case 6 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5),
                            userDepth + 35, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35),
                            userDepth + 35, dotRadius, dotRadius);
                }
                default -> {

                }
            }
        }


    }

    /**
     * This is where I add the dominoes to specific lines for drawing to the
     * command line.
     * @param domino This is the domino being added
     */
    private static void addToLines(LinkedList domino){
        if (right%2 != 0 && (lOrR.matches("r") || computerLOrR.matches("r"))){
            line2.addLast(domino);
            lOrR = "";
            computerLOrR = "";
        }
        else if (right%2 == 0 && (lOrR.matches("r") || computerLOrR.matches("r"))){
            line1.addLast(domino);
            lOrR = "";
            computerLOrR = "";
        }
        else if (left%2 != 0 && (lOrR.matches("l") || computerLOrR.matches("l"))){
            line2.addFirst(domino);
            lOrR = "";
            computerLOrR = "";
        }
        else if (left%2 == 0 && (lOrR.matches("l") || computerLOrR.matches("l"))){
            line1.addFirst(domino);
            lOrR = "";
            computerLOrR = "";
        }
    }

}