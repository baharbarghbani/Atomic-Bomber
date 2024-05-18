package model.components;

import animations.BonusAnimation;
import animations.ExplosionAnimation;
import controller.GameController;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Game;

public class Bomb extends Group {
    protected double angle;
    protected boolean flipped;
    protected double vx;
    protected double vy;
    protected ImageView imageView;
    protected Pane pane;
    protected BonusAnimation bonusAnimation;

    public Bomb(double x, double y, double angle, boolean flipped, double vx, double vy, Pane pane) {
        this.flipped = flipped;
        this.angle = angle;
        this.vx = vx;
        this.vy = vy;
        this.pane = pane;
    }

    public double getAngle(){
        return angle;
    }


    public double getX(){
        return ((Rectangle) (this.getChildren().getFirst())).getX();
    }

    public double getY(){
        return ((Rectangle) (this.getChildren().getFirst())).getY();
    }

    public void setX(double x){
        ((Rectangle) (this.getChildren().get(0))).setX(x);
        ((ImageView)this.getChildren().get(1)).setX(x);
    }

    public void setY(double y){
        ((Rectangle) (this.getChildren().get(0))).setY(y);
        ((ImageView)this.getChildren().get(1)).setY(y);

    }
    public double getVx(){
        return vx;
    }

    public double getVy() {
        return vy;
    }
    public void setVy(double Vy){
        this.vy = Vy;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public void setBackground(String url) {
        imageView.setImage(new Image(Rocket.class.getResource(url).toExternalForm()));
    }

    public void explode(){
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false,true,false);
        explodeAnimation.setRocket(this);
        explodeAnimation.setOnFinished(actionEvent -> {
            this.getChildren().clear();
            pane.getChildren().remove(this);
        });
        explodeAnimation.play();
    }
    public void bonusAction(Component component){
        pane.getChildren().add(this);
        new Thread(() -> {Platform.runLater(() -> {
            bonusAnimation = new BonusAnimation(this);
            Game.getInstance().addAnimations(bonusAnimation);
            bonusAnimation.setOnFinished(actionEvent -> {
                pane.getChildren().remove(this);
            });
            bonusAnimation.play();
        });
        }).start();
    }
    public void remove(){
        pane.getChildren().remove(this);

    }
}
