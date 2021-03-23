package pacman;

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
    Direction movingDirection;
    final double speed = 300;


    PlayerAnimation(Player player, Pane pane, int windowSizeX, int windowSizeY, ArrayList<Rectangle> borders, ArrayList<Circle> circles){
        this.player = player;
        this.pane = pane;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
        this.borders = borders;
        this.circles = circles;
        movingDirection = Direction.NONE;
    }

    void move(Direction dir){
        movingDirection = dir;
        switch (dir){
            case LEFT:
                playerVelocityX.set(-speed);
                playerVelocityY.set(0);
                break;
            case RIGHT:
                playerVelocityX.set(speed);
                playerVelocityY.set(0);
                break;
            case UP:
                playerVelocityY.set(-speed);
                playerVelocityX.set(0);
                break;
            case DOWN:
                playerVelocityY.set(speed);
                playerVelocityX.set(0);
                break;
            default:
                break;
        }
    }

    void stopMovingHorizontal(){
        playerVelocityX.set(0);
    }
    void stopMovingVertical(){
        playerVelocityY.set(0);
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
            eat();

            Position pos = checkCollisions(oldX,oldY,deltaX,deltaY);
            player.characterIcon.setTranslateX(pos.getX());
            player.characterIcon.setTranslateY(pos.getY());
        }
        lastUpdateTime.set(timestamp);
    }

    private Position checkCollisions(double oldX, double oldY, double deltaX, double deltaY) {
        Position pos = new Position(oldX+deltaX,oldY+deltaY);

        Bounds playerBounds = player.characterIcon.getBoundsInParent();
        for (Rectangle border: borders) {
            if ((movingDirection == Direction.LEFT || movingDirection == Direction.RIGHT) && border.intersects(playerBounds.getMinX() + deltaX, playerBounds.getMinY(), playerBounds.getWidth(), playerBounds.getHeight())) {
                pos.setX(oldX);
            }
            if ((movingDirection == Direction.UP || movingDirection == Direction.DOWN) && border.intersects(playerBounds.getMinX(), playerBounds.getMinY() + deltaY, playerBounds.getWidth(), playerBounds.getHeight())) {
                pos.setY(oldY);
            }
        }
        return pos;
    }
}
