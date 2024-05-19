package model.components;

import animations.MigAnimation;
import controller.GameController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Game;

public class Mig extends Rectangle {
    MigAnimation migAnimation;
    private double speed = 1.3;
    public Mig(double x, double y) {
        super(1050, 120, 100, 80);
        this.setFill(new ImagePattern(new Image(Plane.class.getResource("/Images/jet.png").toExternalForm())));
        migAnimation = new MigAnimation(this);
        migAnimation.setCycleCount(-1);
        Game.getInstance().addAnimations(migAnimation);
        migAnimation.play();

    }

    public double getSpeed() {
        return speed * Game.getInstance().getHardness();
    }

    public void move(double dx) {
        if (!hitLeftWall())
            this.setX(this.getX() - dx);
        else{
            Game.getInstance().getWave().removeMig(this);
            Game.getInstance().removeAnimation(migAnimation);
            migAnimation.pause();
        }
    }
    public boolean hitLeftWall(){
        return this.getX() <= -120;
    }
}
