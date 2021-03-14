package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) {
        int windowSizeX = 800;
        int windowSizeY = 800;
        int n = 20; int m = 20;
        final double speed = 200;

        String playerIconPath = "file:/home/olcia/Documents/Mimoza/Pacman_2021_RUNAWAY/images/player.jpg";
        Player player = new Player(playerIconPath,windowSizeX/n,windowSizeX/m);

        Pane pane = new Pane();
        GameBoard gameboard = new GameBoard(n,m);
        pane.setStyle("-fx-background-color: #e0ffff");

        int rectSizeX = windowSizeX/gameboard.n;
        int rectSizeY = windowSizeY/gameboard.m;
        System.out.println(gameboard.board.length);
        System.out.println(gameboard.board[0].length);
        for(int i = 0; i < gameboard.board.length; i++){
            for(int j = 0; j < gameboard.board[0].length; j++){
                if(gameboard.board[i][j] == 0){
                    Rectangle rect = new Rectangle(j*rectSizeX, i*rectSizeY, rectSizeX, rectSizeY);
                    rect.setFill(Color.DARKSEAGREEN);
                    pane.getChildren().add(rect);
                }
                else if(gameboard.board[i][j] == 1){
                    Rectangle rect = new Rectangle(j*rectSizeX, i*rectSizeY, rectSizeX, rectSizeY);
                    rect.setFill(Color.DARKGREEN);
                    pane.getChildren().add(rect);
                }
            }
        }
        player.characterIcon.setTranslateX(1*rectSizeX);
        player.characterIcon.setTranslateY(1*rectSizeY);
        pane.getChildren().add(player.characterIcon);
        Scene scene = new Scene(pane,windowSizeX,windowSizeY);

        PlayerAnimation playerAnimation = new PlayerAnimation(player,windowSizeX,windowSizeY);
        playerAnimation.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.RIGHT){
                    playerAnimation.playerVelocityX.set(speed);
                }
                else if(keyEvent.getCode() == KeyCode.LEFT){
                    playerAnimation.playerVelocityX.set(-speed);
                }
                else if (keyEvent.getCode() == KeyCode.UP){
                    playerAnimation.playerVelocityY.set(-speed);
                }
                else if (keyEvent.getCode() == KeyCode.DOWN){
                    playerAnimation.playerVelocityY.set(speed);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT) {
                    playerAnimation.playerVelocityX.set(0);
                }
                else if(keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN){
                    playerAnimation.playerVelocityY.set(0);
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
