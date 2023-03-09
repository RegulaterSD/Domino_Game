import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.css.RGBColor;

import java.io.*;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Scanner;

public class Project3b_seandavies extends Application {
    private int xPosition;
    private int yPosition;
    private static String lOrR;
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

        Label boneyardLabel = new Label("Boneyard contains " + " dominoes");
        boneyardLabel.setStyle("-fx-font: 24 arial;");
        Label computerLabel = new Label("Computer has " + " dominoes");
        computerLabel.setStyle("-fx-font: 24 arial;");

        Button flipButton = new Button("Flip Domino");
        flipButton.setStyle("-fx-font: 24 arial;");
        flipButton.setOnAction(EventHandler -> {
            rotateChosen = true;
            drawDominoes(canvas,p1);
        });
        Button leftButton = new Button("Play Left");
        leftButton.setStyle("-fx-font: 24 arial;");
        leftButton.setOnAction(EventHandler -> {
            lOrR = "r";
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
        });


        AnimationTimer animationTimer = new AnimationTimer() {
            private long startTime = -1;
            @Override
            public void handle(long now) {
                if (startTime < 0){
                    startTime = now;
                }
                Duration elapsed = Duration.ofNanos(now - startTime);
                long milliseconds = elapsed.toMillis();
                if (rotateChosen && dominoChosen){
                    p1.flip(dominoNumber);
                    rotateChosen = false;
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
                System.out.println("X Position: " + xPosition);
                System.out.println("Y Position: " + yPosition);
                if (yPosition >= 190 && xPosition >= 50 && xPosition <= (((p1.dominoCount()) * 100)+50)){
                    System.out.println("User clicked a domino");
                    dominoNumber = (xPosition - 50)/100;
                    System.out.println("Domino Clicked: " + dominoNumber);
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

    static void playerInput(Player p1, Board board1, Boneyard b1){
        Scanner sc = new Scanner(System.in);
        if ((left != 0 || right != 0) && dominoChosen){
            if (lOrR.matches("r")){
                right++;
            }
            else if (lOrR.matches("l")){
                left++;
            }
        }
        //Player selected a domino and left or right
        if (dominoChosen && lOrRightChosen) {
            System.out.println("Player chose to play");
            if (!board1.checkMove(p1.play(dominoNumber), lOrR)) {
                System.out.println("Player did not do a valid move!");
                p1.playerDominoes.add(dominoNumber, board1.falseMove.get(0));
                board1.falseMove.clear();
                System.out.println(p1.playerDominoes);
            }
            if (lOrR.matches("l")) {
                addToLines(right, left, board1.boardDominoes.getFirst());
            } else if (lOrR.matches("r")) {
                addToLines(right, left, board1.boardDominoes.getLast());
            }
        }
        //Player chose to draw
        if (drawing) {
            System.out.println("Player chose to draw from boneyard");
            if (board1.boardDominoes.size() != 0) {
                if (!p1.playerMove(board1.boardDominoes.getFirst(), board1.boardDominoes.getLast())) {
                    if (b1.boneyardDominoes.size() != 0) {
                        System.out.println("Drawing from BoneYard");
                        p1.draw(b1.getBoneyard());
                        drawing = false;
                    } else {
                        System.out.println("DeadMove");
                        deadMove++;
                        drawing = false;
                    }
                } else {
                    while (drawing) {
                        System.out.println("Cannot draw you have a valid move possible");
                        drawing = false;
                    }
                }
            } else {
                System.out.println("Cannot draw, you have a valid move possible");
                drawing = false;
            }
        }
    }

    //LinkedList line1, LinkedList line2, LinkedList playerDominoes
    //Need to add indent
    static void drawDominoes(Canvas canvas, Player p1){
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

        System.out.println("Top Line: " + topLine);
        System.out.println("Bottom Line: " + bottomLine);
        System.out.println("User Line: " + userLine);
        System.out.println(p1.playerDominoes);

        Integer dotCount;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(255,255,255));
        gc.fillRect(0,0,1500,350);
        Color dominoColor = Color.rgb(222,185,101);
        Color dotColor = Color.rgb(0,0,255);
        Color lineColor = Color.rgb(0,0,0);

        //i variable max needs to change to line1.size/2
        for (Integer i = 0; i < topLine.size()/2; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(boardStart + (dominoWidth * i),boardOne,dominoWidth,dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(boardStart + (dominoWidth * i) + lineStart,boardOne,lineWidth,dominoHeight);
            gc.strokeRoundRect(boardStart + (dominoWidth * i),boardOne,dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        //i variable max needs to change to line1.size
        for (Integer i = 0; i < topLine.size(); i++){
            dotCount = topLine.get(i);
            gc.setFill(dotColor);
            switch (dotCount){
                case 0 -> {
                }
                case 1 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 20),boardOne + 20,dotRadius,dotRadius);
                }
                case 2 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 35,dotRadius,dotRadius);
                }
                case 3 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20),boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 35,dotRadius,dotRadius);
                }
                case 4 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 35,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 35,dotRadius,dotRadius);
                }
                case 5 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20),boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 35,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 35,dotRadius,dotRadius);
                }
                case 6 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5),boardOne + 35,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 5,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 20,dotRadius,dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35),boardOne + 35,dotRadius,dotRadius);
                }
                default -> {

                }
            }
        }

        //i variable max needs to change to line2.size/2
        for (Integer i = 0; i < bottomLine.size()/2; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(boardStart + (dominoWidth * i),boardTwo,dominoWidth,dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(boardStart + (dominoWidth * i) + lineStart,boardTwo,lineWidth,dominoHeight);
            gc.strokeRoundRect(boardStart + (dominoWidth * i),boardTwo,dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        for (Integer i = 0; i < bottomLine.size(); i++) {
            dotCount = bottomLine.get(i);
            gc.setFill(dotColor);
            switch (dotCount) {
                case 0 -> {
                }
                case 1 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 20), boardTwo + 20, dotRadius, dotRadius);
                }
                case 2 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 35, dotRadius, dotRadius);
                }
                case 3 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20), boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 35, dotRadius, dotRadius);
                }
                case 4 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 35, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 35, dotRadius, dotRadius);
                }
                case 5 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 20), boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 35, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 35, dotRadius, dotRadius);
                }
                case 6 -> {
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 5), boardTwo + 35, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 5, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 20, dotRadius, dotRadius);
                    gc.fillOval((boardStart + (dominoHalf * i) + 35), boardTwo + 35, dotRadius, dotRadius);
                }
                default -> {

                }
            }
        }
        //Drawing User Dominoes
        for (Integer i = 0; i < userLine.size()/2; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(userStart + (dominoWidth * i),userDepth,dominoWidth,dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(userStart + (dominoWidth * i) + lineStart,userDepth,lineWidth,dominoHeight);
            gc.strokeRoundRect(userStart + (dominoWidth * i),userDepth,dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        for (Integer i = 0; i < userLine.size(); i++) {
            dotCount = userLine.get(i);
            gc.setFill(dotColor);
            switch (dotCount) {
                case 0 -> {
                }
                case 1 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 20), userDepth + 20, dotRadius, dotRadius);
                }
                case 2 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 35, dotRadius, dotRadius);
                }
                case 3 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 20), userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 35, dotRadius, dotRadius);
                }
                case 4 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 35, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 35, dotRadius, dotRadius);
                }
                case 5 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 20), userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 35, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 35, dotRadius, dotRadius);
                }
                case 6 -> {
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 5), userDepth + 35, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 5, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 20, dotRadius, dotRadius);
                    gc.fillOval((userStart + (dominoHalf * i) + 35), userDepth + 35, dotRadius, dotRadius);
                }
                default -> {

                }
            }
        }


    }

    static void addToLines(int right, int left, LinkedList domino){
        if (right%2 != 0 && lOrR.matches("r")){
            line2.addLast(domino);
            lOrR = "";
        }
        else if (right%2 == 0 && lOrR.matches("r")){
            line1.addLast(domino);
            lOrR = "";
        }
        else if (left%2 != 0 && lOrR.matches("l")){
            line2.addFirst(domino);
            lOrR = "";
        }
        else if (left%2 == 0 && lOrR.matches("l")){
            line1.addFirst(domino);
            lOrR = "";
        }
    }


}