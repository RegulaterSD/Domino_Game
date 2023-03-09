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

public class Project3b_seandavies extends Application {
    private int xPosition;
    private int yPosition;
    static Dominoes d1 = new Dominoes();

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
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

        Button drawBoneyard = new Button("Draw from boneyard");
        drawBoneyard.setStyle("-fx-font: 24 arial;");
        drawBoneyard.setOnAction(EventHandler -> {

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
            }
        };
        animationTimer.start();

        drawDominoes(canvas,d1);

        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xPosition = (int) event.getSceneX();
                yPosition = (int) event.getSceneY();
                System.out.println("X Position: " + xPosition);
                System.out.println("Y Position: " + yPosition);
            }
        });



        stage.setScene(new Scene(root,1500,350));
        root.getChildren().add(boneyardLabel);
        root.getChildren().add(computerLabel);
        stackPane.getChildren().add(canvas);
        root.getChildren().add(stackPane);
        root.getChildren().add(drawBoneyard);
        root.setAlignment(Pos.TOP_CENTER);
        stage.show();
    }

    //LinkedList line1, LinkedList line2, LinkedList playerDominoes
    static void drawDominoes(Canvas canvas, Dominoes d1){
        int dominoWidth = 100;
        int dominoHalf = dominoWidth/2;
        int dominoHeight = dominoWidth/2;
        int dotRadius = dominoWidth/10;
        int lineWidth = 4;
        int lineStart = 48;
        int boardOne = 50;
        int boardTwo = 100;
        int boardStart = 50;
        int userDepth = 200;
        int userStart = 50;

        System.out.println(d1.currentDominoes);

        LinkedList<Integer> line1 = new LinkedList<>();
        for (LinkedList l : d1.currentDominoes){
            for (Object o : l){
                line1.add(Integer.valueOf(String.valueOf(o)));
            }
        }

        System.out.println(line1);

        Integer dotCount;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color dominoColor = Color.rgb(222,185,101);
        Color dotColor = Color.rgb(0,0,255);
        Color lineColor = Color.rgb(0,0,0);

        //i variable max needs to change to line1.size/2
        for (Integer i = 0; i < 10; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(boardStart + (dominoWidth * i),boardOne,dominoWidth,dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(boardStart + (dominoWidth * i) + lineStart,boardOne,lineWidth,dominoHeight);
            gc.strokeRoundRect(boardStart + (dominoWidth * i),boardOne,dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        //i variable max needs to change to line1.size
        for (Integer i = 0; i < 20; i++){
            dotCount = line1.get(i);
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
        for (Integer i = 0; i < 10; i++){
            gc.setFill(dominoColor);
            gc.fillRoundRect(boardStart + (dominoWidth * i),boardTwo,dominoWidth,dominoHeight,dotRadius,dotRadius);
            gc.setFill(lineColor);
            gc.fillRect(boardStart + (dominoWidth * i) + lineStart,boardTwo,lineWidth,dominoHeight);
            gc.strokeRoundRect(boardStart + (dominoWidth * i),boardTwo,dominoWidth,dominoHeight,dotRadius,dotRadius);
        }
        for (Integer i = 0; i < 20; i++) {
            dotCount = line1.get(i + 20);
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


    }
}
