package pacman;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class GhostAnimation extends AnimationTimer {
    final DoubleProperty ghostVelocityX = new SimpleDoubleProperty();
    final DoubleProperty ghostVelocityY = new SimpleDoubleProperty();
    final LongProperty lastUpdateTime = new SimpleLongProperty();
    Ghost ghost;
    Pane pane;
    int windowSizeX;
    int windowSizeY;
    ArrayList<Rectangle> borders;
    Direction movingDirection;
    final double speed = 100;


    GhostAnimation(Ghost ghost, Pane pane, int windowSizeX, int windowSizeY, ArrayList<Rectangle> borders){
        this.ghost = ghost;
        this.pane = pane;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
        this.borders = borders;
        movingDirection = Direction.RIGHT;
        ghostVelocityX.set(speed);
    }

    void move(Direction dir){
        movingDirection = dir;
        switch (dir){
            case LEFT:
                ghostVelocityX.set(-speed);
                ghostVelocityY.set(0);
                break;
            case RIGHT:
                ghostVelocityX.set(speed);
                ghostVelocityY.set(0);
                break;
            case UP:
                ghostVelocityY.set(-speed);
                ghostVelocityX.set(0);
                break;
            case DOWN:
                ghostVelocityY.set(speed);
                ghostVelocityX.set(0);
                break;
            default:
                break;
        }
    }

    void stopMovingHorizontal(){
        ghostVelocityX.set(0);
    }
    void stopMovingVertical(){
        ghostVelocityY.set(0);
    }

    @Override
    public void handle(long timestamp) {
        if(lastUpdateTime.get()>0){
            final double elapsedSeconds = (timestamp - lastUpdateTime.get())/1000000000.0;
            final double deltaX = elapsedSeconds * ghostVelocityX.get();
            final double deltaY = elapsedSeconds * ghostVelocityY.get();
            final double oldX = ghost.characterIcon.getTranslateX();
            final double oldY = ghost.characterIcon.getTranslateY();

            Position pos = checkCollisions(oldX,oldY,deltaX,deltaY);
            ghost.characterIcon.setTranslateX(pos.getX());
            ghost.characterIcon.setTranslateY(pos.getY());
        }
        lastUpdateTime.set(timestamp);
    }

    private Position checkCollisions(double oldX, double oldY, double deltaX, double deltaY) {
        Position pos = new Position(oldX+deltaX,oldY+deltaY);

        Bounds ghostBounds = ghost.characterIcon.getBoundsInParent();
        for (Rectangle border: borders) {
            if ((movingDirection == Direction.LEFT || movingDirection == Direction.RIGHT) && border.intersects(ghostBounds.getMinX() + deltaX, ghostBounds.getMinY(), ghostBounds.getWidth(), ghostBounds.getHeight())) {
                pos.setX(oldX);
                Random rand = new Random();
                boolean b = rand.nextBoolean();
                if(b){
                    ghostVelocityY.set(speed);
                }else{
                    ghostVelocityY.set(-speed);
                }
                ghostVelocityX.set(0);
            }
            if ((movingDirection == Direction.UP || movingDirection == Direction.DOWN) && border.intersects(ghostBounds.getMinX(), ghostBounds.getMinY() + deltaY, ghostBounds.getWidth(), ghostBounds.getHeight())) {
                pos.setY(oldY);
                Random rand = new Random();
                boolean b = rand.nextBoolean();
                if(b){
                    ghostVelocityX.set(speed);
                }else{
                    ghostVelocityX.set(-speed);
                }
                ghostVelocityX.set(0);
            }
        }
        return pos;
    }
}
