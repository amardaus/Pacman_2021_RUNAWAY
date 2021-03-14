package sample;

import javafx.scene.image.Image;

public class Character {
    Image characterIcon;
    int xPos, yPos;

    Character(String characterIconPath){
        characterIcon = new Image(characterIconPath);
    }
}
