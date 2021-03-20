package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) {
        int windowSizeX = 800;
        int windowSizeY = 800;
        int n = 10;
        int m = n;
        final double speed = 200;

        String playerIconPath = "file:/home/olcia/Documents/Mimoza/Pacman_2021_RUNAWAY/images/player.jpg";
        Player player = new Player(playerIconPath,windowSizeX/n,windowSizeX/m);

        Pane pane = new Pane();
        GameBoard gameboard = new GameBoard(n,m);
        pane.setStyle("-fx-background-color: #e0ffff");

        int rectSizeX = windowSizeX/gameboard.n;
        int rectSizeY = windowSizeY/gameboard.m;
        ArrayList<Rectangle> borders = new ArrayList<>();
        ArrayList<Circle> circles = new ArrayList<>();
        for(int i = 0; i < gameboard.board.length; i++){
            for(int j = 0; j < gameboard.board[0].length; j++){
                Rectangle rect = new Rectangle(j*rectSizeX, i*rectSizeY, rectSizeX, rectSizeY);
                if(gameboard.board[i][j] == 0){
                    rect.setFill(Color.DARKGREY);
                    Circle circ = new Circle(j*rectSizeX+0.5*rectSizeX,i*rectSizeY+rectSizeY*0.5,5,Color.BLANCHEDALMOND);
                    circles.add(circ);
                }
                else if(gameboard.board[i][j] == 1){
                    rect.setFill(Color.DARKBLUE);
                    borders.add(rect);
                }
                pane.getChildren().add(rect);
            }
        }
        pane.getChildren().addAll(circles);
        player.characterIcon.setTranslateX(rectSizeX+1);
        player.characterIcon.setTranslateY(rectSizeY+1);
        pane.getChildren().add(player.characterIcon);
        Scene scene = new Scene(pane,windowSizeX,windowSizeY);

        PlayerAnimation playerAnimation = new PlayerAnimation(player,pane,windowSizeX,windowSizeY,borders,circles);
        playerAnimation.start();

        /*int N = 3;
        Opponent[] opponents = new Opponent[N];
        for (Opponent opponent: opponents) {
            opponentAnimation =
        }*/

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.RIGHT){
                    // tutaj sprawdzac kolizje i na nie pozwolić zanim jeszcze się wydarzy
                    // jezeli jest kolizja to ZATRZYMUJE GRACZA, NIE POZWALAM TEMU DEBILOWI IŚĆ DALEJ
                    // tylko go cofam i git.
                    playerAnimation.playerVelocityX.set(speed);
                    playerAnimation.playerVelocityY.set(0);
                }
                else if(keyEvent.getCode() == KeyCode.LEFT){
                    playerAnimation.playerVelocityX.set(-speed);
                    playerAnimation.playerVelocityY.set(0);
                }
                else if (keyEvent.getCode() == KeyCode.UP){
                    playerAnimation.playerVelocityY.set(-speed);
                    playerAnimation.playerVelocityX.set(0);
                }
                else if (keyEvent.getCode() == KeyCode.DOWN){
                    playerAnimation.playerVelocityY.set(speed);
                    playerAnimation.playerVelocityX.set(0);
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
