package model.components;

import animations.ExplosionAnimation;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Rocket extends Bomb {
    public Rocket(double x, double y, double angle, boolean flipped, double vx, double vy, Pane pane){
        super(x, y, angle, flipped, vx, vy ,pane);
        this.flipped = flipped;
        Rectangle rectangle = new Rectangle(x, y, 25, 25);
        rectangle.setFill(Color.TRANSPARENT);
        Image rocketImage = new Image(Rocket.class.getResource("/Images/rockets/rocket4.png").toExternalForm());
        imageView = new ImageView(rocketImage);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
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
