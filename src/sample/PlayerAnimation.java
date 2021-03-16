package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerAnimation extends AnimationTimer {
    final DoubleProperty playerVelocityX = new SimpleDoubleProperty();
    final DoubleProperty playerVelocityY = new SimpleDoubleProperty();
    final LongProperty lastUpdateTime = new SimpleLongProperty();
    Player player;
    Pane pane;
    int windowSizeX;
    int windowSizeY;
    ArrayList<Rectangle> borders;
    ArrayList<Circle> circles;

    PlayerAnimation(Player player, Pane pane, int windowSizeX, int windowSizeY, ArrayList<Rectangle> borders, ArrayList<Circle> circles){
        this.player = player;
        this.pane = pane;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
        this.borders = borders;
        this.circles = circles;
    }

    void eat(){
        Iterator<Circle> it = circles.iterator();
        while(it.hasNext()){
            Circle circle = it.next();
            if(player.characterIcon.getBoundsInParent().intersects(circle.getBoundsInParent())){
                pane.getChildren().remove(circle);
                it.remove();
            }
        }
    }

    @Override
    public void handle(long timestamp) {
        if(lastUpdateTime.get()>0){
            final double elapsedSeconds = (timestamp - lastUpdateTime.get())/1000000000.0;
            final double deltaX = elapsedSeconds * playerVelocityX.get();
            final double deltaY = elapsedSeconds * playerVelocityY.get();
            final double oldX = player.characterIcon.getTranslateX();
            final double oldY = player.characterIcon.getTranslateY();
            //eat();

            Position pos = checkCollisions(oldX,oldY,deltaX,deltaY);
            player.characterIcon.setTranslateX(pos.getX());
            player.characterIcon.setTranslateY(pos.getY());
        }
        lastUpdateTime.set(timestamp);
    }

    private Position checkCollisions(double oldX, double oldY, double deltaX, double deltaY) {
        Position pos = new Position(oldX+deltaX,oldY+deltaY);
        boolean movingUp = (deltaY < 0);
        boolean movingDown = (deltaY > 0);
        boolean movingLeft = (deltaX < 0);
        boolean movingRight = (deltaX > 0);
        System.out.println("deltaX:" + deltaX + " deltaY: " + deltaY);


        Bounds bounds = player.characterIcon.getBoundsInParent();
        for (Rectangle border: borders){
            if(border.intersects(bounds)){
                if(movingLeft){
                    pos.setX(oldX-deltaX);
                }
                else if (movingRight){
                    pos.setX(oldX-deltaX);
                }
                else if(movingUp){
                    pos.setY(oldY-deltaY);
                }
                else if(movingDown){
                    pos.setY(oldY+deltaY);
                }
                return pos;
            }
        }
        return pos;
    }
}
