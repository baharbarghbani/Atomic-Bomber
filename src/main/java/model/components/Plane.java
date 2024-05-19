package model.components;

import animations.ExplosionAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Game;

public class Plane extends Rectangle {

    public boolean flipped = false;
    public static final int HEIGHT = 80;
    public static final double WIDTH = 100;
    private static Plane instance;
    public double angle = 0.001;
    private int hp = 10;
    private Game game;
    private Pane pane;
    public Plane(Game game, Pane pane){
        super(0, 90, 80 ,60);
        this.game = game;
        this.pane = pane;
        this.setFill(new ImagePattern(new Image(Plane.class.getResource("/Images/icon.png").toExternalForm())));
    }
    public void moveRight(){
        if (!hitRightWall())
            this.setX(this.getX() + 10);
        else this.setX(-100);
    }
    public void moveLeft(){
        if (!hitLeftWall())
            this.setX(this.getX() - 10);
        else this.setX(1100 - this.getWidth());
    }
    public void moveUp(){
        if (!hitTopWall())
            this.setY(this.getY() - 10);
    }
    public void moveDown(){
        if (!hitBottomWall()) {
            this.setY(this.getY() + 10);
        }

    }
    public boolean hitTopWall(){
        return this.getY() <= 0;
    }
    public boolean hitBottomWall(){
        return this.getY() + this.getHeight() >= 600;
    }
    public boolean hitLeftWall(){
        return this.getX() <= -100;
    }
    public boolean hitRightWall(){
        return this.getX() + this.getWidth() >= 1100;
    }
    public double getAngle(){
        return angle;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }

    public void setBackground(String url) {
        this.setFill(new ImagePattern(new Image(Plane.class.getResource(url).toExternalForm())));
    }

    public void explode(){
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(true,false,false);
        explodeAnimation.setPlane(this);
        this.setRotate(-90);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Plane.this);
            }
        });

        explodeAnimation.play();
    }
    public void remove(){
        game.setPlane(null);
    }
    public void decreaseHP(){
        hp--;
        if (hp == 0){
            explode();
        }
    }

}
