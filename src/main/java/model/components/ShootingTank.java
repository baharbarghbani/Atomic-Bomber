package model.components;

import animations.ExplosionAnimation;
import animations.NuclearBombAnimation;
import animations.TankShootingAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;
import view.GameLauncher;

public class ShootingTank extends Tank{
    private int radius = 200;
    public ShootingTank(double x ,int imageNumber, Game game, Pane pane){
        super(x, imageNumber, game, pane);
        this.setY(700);
        this.setFill(new ImagePattern(new Image(Tank.class.getResource("/Images/tanks/shooting-tank" + imageNumber + ".png").toExternalForm())));
    }
    public void shoot(double angle){
        Bullet bullet = new Bullet(this.getX()+ 20, this.getY(), Math.toRadians(angle), 6 * Math.cos(angle), Math.abs(6 * Math.sin(angle)));
        if (imageNumber == 0){
            if (angle >= 180 && angle <= 270) {
                GameLauncher.getInstance().root.getChildren().remove(this);
                this.setScaleX(-1);
                GameLauncher.getInstance().root.getChildren().add(this);
            }
        }else {
            if (angle <= 360 && angle >= 270) {
                GameLauncher.getInstance().root.getChildren().remove(this);
                this.setScaleX(-1);
                GameLauncher.getInstance().root.getChildren().add(this);
            }
        }
        TankShootingAnimation shootingTankAnimation = new TankShootingAnimation(this, game, angle, bullet);
        game.addAnimations(shootingTankAnimation);
        GameLauncher.getInstance().root.getChildren().add(bullet);
        shootingTankAnimation.play();
    }
    @Override
    public void explode(){
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false,false,true);
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
    public int getRadius(){
        return radius*Game.getInstance().getHardness();
    }
}
