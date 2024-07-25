package model.components;

import view.animations.ExplosionAnimation;
import view.animations.MissileAnimation;
import view.animations.NuclearBombAnimation;
import view.ComponentCreator;
import controller.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;
import model.bombs.Bullet;

public class ShootingTank extends Tank {
    private final int radius = 200;
    private MissileAnimation missileAnimation;

    public ShootingTank(double x, int imageNumber, Game game, Pane pane) {
        super(x, imageNumber, game, pane);
        this.setY(700);
        this.setFill(new ImagePattern(new Image(Tank.class.getResource("/Images/tanks/shooting-tank" + imageNumber + ".png").toExternalForm())));
    }

    public void shoot(double angle) {
        Bullet bullet = ComponentCreator.createBulletForTank(this.getX(), this.getY(), angle);
        GameController.performTankShootingAnimation(this, bullet, imageNumber, angle, game, pane);
    }

    @Override
    public void explode() {
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false, false, true);
        explodeAnimation.setComponent(this);
        game.addAnimations(explodeAnimation);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(ShootingTank.this);
                game.getWave().removeObject(ShootingTank.this);
                game.removeAnimation(explodeAnimation);
                game.getWave().removeShootingTank(ShootingTank.this);
            }
        });
        explodeAnimation.play();
    }

    @Override
    public void explodeByNuclear() {
        imageView.setLayoutX(this.getX());
        imageView.setLayoutY(this.getY() - 30);
        imageView.setFitWidth(this.getWidth() + 40);
        imageView.setFitHeight(this.getHeight() + 40);
        NuclearBombAnimation nuclearBombAnimation = new NuclearBombAnimation(imageView);
        pane.getChildren().remove(ShootingTank.this);
        game.addAnimations(nuclearBombAnimation);
        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                game.getWave().removeObject(ShootingTank.this);
                game.getWave().removeShootingTank(ShootingTank.this);
                game.removeAnimation(nuclearBombAnimation);
            }
        });
        nuclearBombAnimation.play();
    }

    public int getRadius() {
        return radius * Game.getInstance().getHardness();
    }

    public void setMissileAnimation(MissileAnimation missileAnimation) {
        this.missileAnimation = missileAnimation;
    }
}
