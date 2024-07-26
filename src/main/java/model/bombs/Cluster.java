package model.bombs;

import view.animations.BombAnimation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Cluster extends Bomb {
    static Random random = new Random();

    public Cluster(double x, double y, double angle, boolean flipped, double vx, double vy, Pane pane, double fitWidth, double fitHeight) {
        super(x, y, angle, flipped, vx, vy, pane);
        rectangle = new Rectangle(x, y, fitWidth, fitHeight);
        rectangle.setFill(Color.TRANSPARENT);
        Image rocketImage = new Image(Rocket.class.getResource("/Images/bombs/malware.png").toExternalForm());
        imageView = new ImageView(rocketImage);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        double imageRotation = this.angle;
        if (flipped) {
            imageRotation -= 0;
        }
        imageView.setX(x);
        imageView.setY(y);
        imageView.setRotate(Math.toDegrees(imageRotation));
        rectangle.setRotate(Math.toDegrees(angle));
        this.setRotate(Math.toDegrees(angle));
        this.getChildren().addAll(rectangle, imageView);
    }

    public void divide() {
        double angle = this.angle;
        for (int i = 0; i < random.nextInt(2, 6); i++) {
            Cluster cluster = new Cluster(this.getX(), this.getY(), Math.toRadians(angle), false, 4 * Math.cos(Math.toRadians(angle)), 4 * Math.sin(Math.toRadians(angle)), pane, 20, 20);
            angle += (double) 90 / (i + 1);
            BombAnimation bombAnimtaion = new BombAnimation(cluster);
            pane.getChildren().add(cluster);
            bombAnimtaion.play();
        }
        pane.getChildren().remove(this);
    }
}
