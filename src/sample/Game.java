package sample;

import javafx.application.Application;
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

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.RIGHT){
                    playerIcon.setLayoutX(playerIcon.getLayoutX() + 10);
                }
                else if(keyEvent.getCode() == KeyCode.LEFT){
                    playerIcon.setLayoutX(playerIcon.getLayoutX() - 10);
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
