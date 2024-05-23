package model.components;

import view.animations.ExplosionAnimation;
import view.animations.PlaneAnimation;
import controller.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Game;

import java.util.Objects;

public class Plane extends Rectangle {

    public static final int HEIGHT = 80;
    public static final double WIDTH = 100;
    public boolean flipped = false;
    public double angle = 0.001;
    private int hp = 3;
    private final Game game;
    private final Pane pane;
    private final double x;
    private final double y;
    private PlaneAnimation planeAnimation;

    public Plane(Game game, Pane pane, double x, double y) {
        super(x, y, 80, 60);
        this.game = game;
        this.x = x;
        this.y = y;
        this.pane = pane;
        this.setFill(new ImagePattern(new Image(Plane.class.getResource("/Images/icon.png").toExternalForm())));
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public PlaneAnimation getPlaneAnimation() {
        return this.planeAnimation;
    }

    public void setPlaneAnimation(PlaneAnimation planeAnimation) {
        this.planeAnimation = planeAnimation;
    }

    public void setBackground(String url) {
        this.setFill(new ImagePattern(new Image(Plane.class.getResource(url).toExternalForm())));
    }

    public void explode() {
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(true, false, false);
        explodeAnimation.setPlane(this);
        this.setRotate(-90);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                explodeAnimation.pause();
                pane.getChildren().remove(Plane.this);
                try {
                    GameController.endGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Game.getInstance().setPlane(null);
        explodeAnimation.play();
    }

    public void remove() {
        game.setPlane(null);
    }

    public void decreaseHP() {
        hp--;
        if (hp == 0) {
            explode();
        }
    }

    public void increaseHitPoint(int i) {
        this.hp += i;
    }


    public int getHP() {
        return hp;
    }

    public void setImage(String s) {
        this.setFill(new ImagePattern(new Image(Objects.requireNonNull(this.getClass().getResource(s)).toExternalForm())));
    }
}
