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

        AnimationTimer animationTimer = new AnimationTimer() {
            private long startTime = -1;
            @Override
            public void handle(long now) {
                if (startTime < 0){
                    startTime = now;
                }
                Duration elapsed = Duration.ofNanos(now - startTime);
                long milliseconds = elapsed.toMillis();
                drawDominoes(canvas);
            }
        };
        animationTimer.start();

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

    //LinkedList board, LinkedList playerDominoes
    static void drawDominoes(Canvas canvas){
        int dominoWidth = 100;
        int dominoHeight = 50;
        int dotRadius = 10;
        int lineWidth = 4;
        int lineStart = 48;
        int boardOne = 50;
        int boardTwo = 100;
        int boardStart = 50;
        int userDepth = 200;
        int userStart = 50;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color dominoColor = Color.rgb(222,185,101);
        Color dotColor = Color.rgb(0,0,255);
        Color lineColor = Color.rgb(0,0,0);
        gc.setFill(dominoColor);
        gc.fillRoundRect(boardStart,boardOne,dominoWidth,dominoHeight,dotRadius,dotRadius);
        gc.setFill(dotColor);
        gc.fillOval(boardStart + 20,boardOne + 20,dotRadius,dotRadius);
        gc.setFill(lineColor);
        gc.fillRect(boardStart + lineStart,boardOne,lineWidth,dominoHeight);


    }
}
