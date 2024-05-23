package model.components;

import animations.MigAnimation;
import animations.MissileAnimation;
import controller.ComponentCreator;
import controller.GameController;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Game;
import model.Wave;
import model.bombs.Bullet;

public class Mig extends Rectangle {
    private static final double radius = 200;
    MigAnimation migAnimation;
    private MissileAnimation missileAnimation;
    private final double speed = 2;
    private final Game game;
    private final Pane pane;
    private final double passingTime = 20;

    public Mig(double x, double y, Game game, Pane pane) {
        super(1050, 120, 100, 80);
        this.game = game;
        this.pane = pane;

        this.setFill(new ImagePattern(new Image(Plane.class.getResource("/Images/jet.png").toExternalForm())));
        migAnimation = new MigAnimation(this);
        migAnimation.setCycleCount(-1);
        game.addAnimations(migAnimation);
        migAnimation.play();
    }

    public double getSpeed() {
        return speed * game.getHardness();
    }

    public void move(double dx) {
        if (!hitLeftWall()) {
            this.setX(this.getX() - dx);
        } else {
            Wave.removeMig();
//            pauseMissileAnimation();
            this.migAnimation.stop();
        }
    }

    public boolean hitLeftWall() {
        return this.getX() <= -120;
    }

    public double getRadius() {
        return radius * Game.getInstance().getHardness();
    }

    public void shoot(double angle) {
        Bullet bullet = ComponentCreator.createBulletForMig(this.getX(), this.getY(), angle);
        Plane plane = game.getPlane();
//        if (plane != null){
//            plane.requestFocus();
//        }
        GameController.performMissileShootingAnimationForMig(bullet, game, pane, this);
//        if (plane != null)
//            plane.requestFocus();
    }

    public void setMissileAnimation(MissileAnimation missileAnimation) {
        this.missileAnimation = missileAnimation;
    }

    public void pauseMissileAnimation() {
        missileAnimation.pause();
    }

    public double getPassingTime() {
        return passingTime * game.getHardness();
    }
}
