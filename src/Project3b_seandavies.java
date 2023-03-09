import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.time.Duration;
import java.util.LinkedList;

public class Project3b_seandavies extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Dominoes");
        Canvas canvas = new Canvas(600, 250);
        StackPane stackPane = new StackPane();
        VBox root = new VBox();

        Label boneyardLabel = new Label("Boneyard contains " + " dominoes");
        boneyardLabel.setStyle("-fx-font: 24 arial;");
        Label computerLabel = new Label("Computer has " + " dominoes");
        computerLabel.setStyle("-fx-font: 24 arial;");

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

        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {

            }
        });



        stage.setScene(new Scene(root,600,500));
        root.getChildren().add(boneyardLabel);
        root.getChildren().add(computerLabel);
        stackPane.getChildren().add(canvas);
        root.getChildren().add(stackPane);
        root.setAlignment(Pos.TOP_CENTER);
        stage.show();

    }
}
