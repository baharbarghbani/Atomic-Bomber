package model.components;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Bullet extends Bomb {
    private double speed = 3;

    public Bullet(double x, double y, double angle, double vx, double vy) {
        super(x, y, angle, false, vx, vy, null);
        Rectangle rectangle = new Rectangle(x, y, 10, 10);
        rectangle.setFill(Color.TRANSPARENT);
        ImageView bulletImage = new ImageView(new Image(Objects.requireNonNull(Bullet.class.getResource("/Images/bullet.png")).toExternalForm()));
        bulletImage.setFitWidth(10);
        bulletImage.setFitHeight(10);
        bulletImage.setX(x);
        bulletImage.setY(y);
        bulletImage.setRotate(Math.toDegrees(angle));
        rectangle.setRotate(Math.toDegrees(angle));
        this.setRotate(Math.toDegrees(angle));
        this.getChildren().addAll(rectangle, bulletImage);
    }
}
