package pacman;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character {
    Image characterImage;
    ImageView characterIcon;

    Character(String characterIconPath, int sizeX, int sizeY){
        characterImage = new Image(characterIconPath);
        characterIcon = new ImageView(characterImage);
        characterIcon.setFitWidth(sizeX);
        characterIcon.setFitHeight(sizeY);
    }
}
