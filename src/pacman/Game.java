package pacman;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        float characterSizeX = windowSizeX/n;
        float characterSizeY = windowSizeY/m;
        GameBoard gameboard = new GameBoard(n,m);
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #e0ffff");
        Scene scene = new Scene(pane,windowSizeX,windowSizeY);

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

        String playerIconPath = "file:/home/olcia/Documents/Mimoza/Pacman_2021_RUNAWAY/images/player.jpg";
        Player player = new Player(playerIconPath,characterSizeX,characterSizeY);
        PlayerAnimation playerAnimation = new PlayerAnimation(player,pane,windowSizeX,windowSizeY,borders,circles);
        playerAnimation.start();
        pane.getChildren().add(player.characterIcon);

        int ghostCount = 2;
        Ghost[] ghosts = new Ghost[ghostCount];
        String ghostIconPath = "file:/home/olcia/Documents/Mimoza/Pacman_2021_RUNAWAY/images/ghost.png";
        for (int i = 0; i < ghostCount; i++) {
            ghosts[i] = new Ghost(ghostIconPath, characterSizeX, characterSizeY);
            GhostAnimation ghostAnimation = new GhostAnimation(ghosts[i], pane,windowSizeX,windowSizeY,borders);
            ghostAnimation.start();
            pane.getChildren().add(ghosts[i].characterIcon);
        }

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.RIGHT){
                    playerAnimation.move(Direction.RIGHT);
                }
                else if(keyEvent.getCode() == KeyCode.LEFT){
                    playerAnimation.move(Direction.LEFT);
                }
                else if (keyEvent.getCode() == KeyCode.UP){
                    playerAnimation.move(Direction.UP);
                }
                else if (keyEvent.getCode() == KeyCode.DOWN){
                    playerAnimation.move(Direction.DOWN);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT) {
                    playerAnimation.stopMovingHorizontal();
                }
                else if(keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN){
                    playerAnimation.stopMovingVertical();
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
