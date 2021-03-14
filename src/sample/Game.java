package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        int windowSizeX = 800;
        int windowSizeY = 800;

        String playerIconPath = "file:/home/olcia/Documents/Mimoza/Pacman_2021_RUNAWAY/images/player.jpg";
        Player player = new Player(playerIconPath);

        Pane pane = new Pane();
        ImageView playerIcon = new ImageView(player.characterIcon);
        playerIcon.setFitHeight(Math.round(windowSizeX/10.0));
        playerIcon.setFitWidth(Math.round(windowSizeY/10.0));
        pane.getChildren().addAll(playerIcon);
        Scene scene = new Scene(pane,windowSizeX,windowSizeY);

        final double speed = 200;
        final DoubleProperty playerVelocityX = new SimpleDoubleProperty();
        final DoubleProperty playerVelocityY = new SimpleDoubleProperty();
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final AnimationTimer gameAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if(lastUpdateTime.get()>0){
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get())/1000000000.0;
                    final double deltaX = elapsedSeconds * playerVelocityX.get();
                    final double deltaY = elapsedSeconds * playerVelocityY.get();
                    final double oldX = playerIcon.getTranslateX();
                    final double oldY = playerIcon.getTranslateY();
                    final double newX = oldX + deltaX;
                    final double newY = oldY + deltaY;
                    playerIcon.setTranslateX(newX);
                    playerIcon.setTranslateY(newY);
                }
                lastUpdateTime.set(timestamp);
            }
        };
        gameAnimation.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.RIGHT){
                    playerVelocityX.set(speed);
                }
                else if(keyEvent.getCode() == KeyCode.LEFT){
                    playerVelocityX.set(-speed);
                }
                else if (keyEvent.getCode() == KeyCode.UP){
                    playerVelocityY.set(-speed);
                }
                else if (keyEvent.getCode() == KeyCode.DOWN){
                    playerVelocityY.set(speed);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT) {
                    playerVelocityX.set(0);
                }
                else if(keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN){
                    playerVelocityY.set(0);
                }
            }
        });

        primaryStage.setTitle("Pacman 2021 RUNAWAY");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
