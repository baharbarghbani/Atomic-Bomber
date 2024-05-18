package model.components;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;

public class ShootingTank extends Tank{
    public ShootingTank(double x ,int imageNumber, Game game, Pane pane){
        super(x, imageNumber, game, pane);
        this.setFill(new ImagePattern(new Image(Tank.class.getResource("/Images/tanks/shooting-tank" + imageNumber + ".png").toExternalForm())));
    }
}
