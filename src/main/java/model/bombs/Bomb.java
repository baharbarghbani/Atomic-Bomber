package model.bombs;

import animations.BonusAnimation;
import animations.ExplosionAnimation;
import animations.NuclearBombAnimation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Game;
import model.components.Component;

import java.util.Objects;

public class Bomb extends Group {
    protected double angle;
    protected boolean flipped;
    protected double vx;
    protected double vy;
    protected ImageView imageView;
    protected Pane pane;
    protected BonusAnimation bonusAnimation;
    protected Rectangle rectangle;

    public Bomb(double x, double y, double angle, boolean flipped, double vx, double vy, Pane pane) {
        this.flipped = flipped;
        this.angle = angle;
        this.vx = vx;
        this.vy = vy;
        this.pane = pane;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getX() {
        if (this.getChildren().isEmpty()) {
            return 0;
        }
        return ((Rectangle) (this.getChildren().getFirst())).getX();
    }

    public void setX(double x) {
        ((Rectangle) (this.getChildren().get(0))).setX(x);
        ((ImageView) this.getChildren().get(1)).setX(x);
    }

    public double getY() {
        if (this.getChildren().isEmpty()) {
            return 0;
        }
        return ((Rectangle) (this.getChildren().getFirst())).getY();
    }

    public void setY(double y) {
        ((Rectangle) (this.getChildren().get(0))).setY(y);
        ((ImageView) this.getChildren().get(1)).setY(y);

    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double Vy) {
        this.vy = Vy;
    }

    public void setBackground(String url) {
        imageView.setImage(new Image(Rocket.class.getResource(url).toExternalForm()));
    }

    public void explodeCluster() {

    }

    public void explode() {
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false, true, false);
        explodeAnimation.setBomb(this);
        Game.getInstance().addAnimations(explodeAnimation);
        explodeAnimation.setOnFinished(actionEvent -> {
            this.getChildren().clear();
            pane.getChildren().remove(this);
            Game.getInstance().removeAnimation(explodeAnimation);
        });
        explodeAnimation.play();
    }

    public void explodeNuclear() {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/explosion.gif"))));
        try {
            imageView.setLayoutX(this.getX());
            imageView.setLayoutY(this.getY() - 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        pane.getChildren().remove(Bomb.this);
        NuclearBombAnimation nuclearBombAnimation = new NuclearBombAnimation(imageView);
        Game.getInstance().addAnimations(nuclearBombAnimation);
        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                Game.getInstance().removeAnimation(nuclearBombAnimation);
            }
        });
        nuclearBombAnimation.play();
    }

    public void bonusAction(Component component) {
        pane.getChildren().add(this);
        new Thread(() -> {
            Platform.runLater(() -> {
                bonusAnimation = new BonusAnimation(this);
                Game.getInstance().addAnimations(bonusAnimation);
                bonusAnimation.setOnFinished(actionEvent -> {
                    pane.getChildren().remove(this);
                });
                bonusAnimation.play();
            });
        }).start();
    }

    public void remove() {
        pane.getChildren().remove(this);
    }
}
