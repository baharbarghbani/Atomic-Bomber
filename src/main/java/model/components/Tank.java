package model.components;

import animations.ExplosionAnimation;
import animations.NuclearBombAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;

import java.util.Objects;

public class Tank extends Component {
    protected double speed = 0.2;
    protected int imageNumber;

    public Tank(double x, int imageNumber, Game game, Pane pane){
        super(x, 680, 60 ,60, game, pane);
        this.imageNumber = imageNumber;
        kill = 2;
        hasBonus = false;
        this.setFill(new ImagePattern(new Image(Tank.class.getResource("/Images/tanks/tank" + imageNumber + ".png").toExternalForm())));
    }
    public void moveRight(double dx){
        if (!hitRightWall())
            this.setX(this.getX() + dx);
        else this.setX(-50);
    }
    public void moveLeft(double dx){
        if (!hitLeftWall())
            this.setX(this.getX() - dx);
        else this.setX(1060 - this.getWidth());
    }
    public boolean hitLeftWall(){
        return this.getX() <= -50;
    }
    public boolean hitRightWall(){
        return this.getX() + this.getWidth() >= 1060;
    }
    @Override
    public void setBackground(String url) {
        this.setFill(new ImagePattern(new Image(Tank.class.getResource(url).toExternalForm())));
    }
    @Override
    public void remove(){
        game.getWave().getAllObjects().remove(this);
    }
    @Override
    public void explode(){
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false,false,true);
        explodeAnimation.setComponent(this);
        game.addAnimations(explodeAnimation);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Tank.this);
            }
        });
        explodeAnimation.play();
    }
    @Override
    public void explodeByNuclear() {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/explosion.gif"))));
        imageView.setLayoutX(this.getX());
        imageView.setLayoutY(this.getY());
        NuclearBombAnimation nuclearBombAnimation = new NuclearBombAnimation(imageView, this);        game.addAnimations(nuclearBombAnimation);
        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Tank.this);
            }
        });
        nuclearBombAnimation.play();
    }

    public double getSpeed() {
        return speed * game.getHardness();
    }
    public int getImageNumber(){
        return imageNumber;
    }


}