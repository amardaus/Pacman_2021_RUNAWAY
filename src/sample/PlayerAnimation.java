package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;

public class PlayerAnimation extends AnimationTimer {
    final DoubleProperty playerVelocityX = new SimpleDoubleProperty();
    final DoubleProperty playerVelocityY = new SimpleDoubleProperty();
    final LongProperty lastUpdateTime = new SimpleLongProperty();
    Player player;
    int windowSizeX;
    int windowSizeY;

    PlayerAnimation(Player player,int windowSizeX,int windowSizeY){
        this.player = player;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
    }

    @Override
    public void handle(long timestamp) {
        if(lastUpdateTime.get()>0){
            final double elapsedSeconds = (timestamp - lastUpdateTime.get())/1000000000.0;
            final double deltaX = elapsedSeconds * playerVelocityX.get();
            final double deltaY = elapsedSeconds * playerVelocityY.get();
            final double oldX = player.characterIcon.getTranslateX();
            final double oldY = player.characterIcon.getTranslateY();
            double newX = oldX + deltaX;
            double newY = oldY + deltaY;
            if(newX > windowSizeX-player.characterIcon.getFitWidth()) newX = windowSizeX-player.characterIcon.getFitWidth();
            if(newX < 0) newX = 0;
            if(newY > windowSizeY-player.characterIcon.getFitHeight()) newY = windowSizeY-player.characterIcon.getFitHeight();
            if(newY < 0) newY = 0;

            player.characterIcon.setTranslateX(newX);
            player.characterIcon.setTranslateY(newY);
        }
        lastUpdateTime.set(timestamp);
    }
}
