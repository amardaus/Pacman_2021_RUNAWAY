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
        System.out.println("eat");
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
            double newX, newY;
            newX = oldX + deltaX;
            newY = oldY + deltaY;
            eat();

            Position pos = checkCollisions(newX, newY);
            player.characterIcon.setTranslateX(pos.x);
            player.characterIcon.setTranslateY(pos.y);
        }
        lastUpdateTime.set(timestamp);
    }

    private Position checkCollisions(double newX, double newY) {
        Position pos = new Position(newX,newY);
        Bounds bounds = player.characterIcon.getBoundsInParent();
        for (Rectangle border: borders){
            if(border.intersects(bounds)){
                System.out.println(bounds.getMaxX());
                System.out.println(border.getBoundsInParent().getMinX());
                System.out.println("--------");

                // TO NIEDZIAŁA BO JEDNOCZEŚNIE ŁAPIE SIĘ KILKA COLLIDERÓW - TRZBA UWZGLĘDNIĆ KIERUNEK PRZEMIESZCZENIA
                /*if(bounds.getMinX() < border.getBoundsInParent().getMaxX()){
                    //pos.x = border.getBoundsInParent().getMaxX() + 1;
                    System.out.println(bounds.getMinX());
                    System.out.println(border.getBoundsInParent().getMaxX());
                    System.out.println("--------");
                    System.out.println("left");
                }
                else if(bounds.getMaxX() > border.getBoundsInParent().getMinX()){
                    //pos.x = border.getBoundsInParent().getMinX() - 81;
                    System.out.println("right");
                }
                else if(bounds.getMinY() > border.getBoundsInParent().getMaxY()){
                    System.out.println("top");
                }
                else if(bounds.getMaxY() > border.getBoundsInParent().getMinY()){
                    //System.out.println("bottom");
                }*/
                return pos;
            }
        }
        return pos;
    }
}
