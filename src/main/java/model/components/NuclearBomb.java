package model.components;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.GameLauncherController;

import java.util.List;

public class NuclearBomb extends Bomb {
    public NuclearBomb(double x, double y, double angle, boolean flipped, double vx, double vy, Pane pane, double fitWidth, double fitHeight){
        super(x, y, angle, flipped, vx, vy,pane);
        rectangle = new Rectangle(x, y, 25, 25);
        rectangle.setFill(Color.TRANSPARENT);
        Image rocketImage = new Image(Rocket.class.getResource("/Images/atomic-bomb.png").toExternalForm());
        imageView = new ImageView(rocketImage);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        double imageRotation = this.angle;
        if(flipped){
            imageRotation -=0;
        }
        // Position the ImageView within the Rectangle
        imageView.setX(x);
        imageView.setY(y);
        imageView.setRotate(Math.toDegrees(imageRotation));
        rectangle.setRotate(Math.toDegrees(angle));
        this.setRotate(Math.toDegrees(angle));
        this.getChildren().addAll(rectangle, imageView);
    }


}
