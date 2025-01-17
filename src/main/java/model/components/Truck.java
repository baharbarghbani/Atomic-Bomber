package model.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;
import view.animations.ExplosionAnimation;
import view.animations.NuclearBombAnimation;

public class Truck extends Component {
    private final double speed = 0.3;
    private final int imageNumber;

    public Truck(int imageNumber, double x, Game game, Pane pane) {
        super(x, 685, 60, 60, game, pane);
        this.imageNumber = imageNumber;
        kill = 1;
        hasBonus = false;
        this.setFill(new ImagePattern(new Image(Tank.class.getResource("/Images/trucks/truck" + imageNumber + ".png").toExternalForm())));
    }

    public double getSpeed() {
        return speed;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void moveRight(double dx) {
        if (!hitRightWall()) this.setX(this.getX() + dx);
        else this.setX(-50);
    }

    public void moveLeft(double dx) {
        if (!hitLeftWall()) this.setX(this.getX() - dx);
        else this.setX(1050 - this.getWidth());
    }

    public boolean hitLeftWall() {
        return this.getX() <= -50;
    }

    public boolean hitRightWall() {
        return this.getX() + this.getWidth() >= 1050;
    }

    @Override
    public void setBackground(String url) {
        this.setFill(new ImagePattern(new Image(Tank.class.getResource(url).toExternalForm())));
    }

    @Override
    public void remove() {
        game.getWave().getAllObjects().remove(this);
    }

    @Override
    public void explode() {
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false, false, true);
        game.addAnimations(explodeAnimation);
        explodeAnimation.setComponent(this);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Truck.this);
                game.getWave().removeObject(Truck.this);
                game.removeAnimation(explodeAnimation);
                game.getWave().removeTruck(Truck.this);
            }
        });
        explodeAnimation.play();
    }

    public void explodeByNuclear() {
        imageView.setLayoutX(this.getX());
        imageView.setLayoutY(this.getY() - 50);
        imageView.setFitWidth(this.getWidth() + 60);
        imageView.setFitHeight(this.getHeight() + 60);
        Animation();
    }

    private void Animation() {
        NuclearBombAnimation nuclearBombAnimation = new NuclearBombAnimation(imageView);
        game.addAnimations(nuclearBombAnimation);
        pane.getChildren().remove(Truck.this);

        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                game.removeAnimation(nuclearBombAnimation);
                game.getWave().removeObject(Truck.this);
                game.getWave().removeTruck(Truck.this);

            }
        });
        nuclearBombAnimation.play();
    }

    @Override
    public void explodeByCluster() {
        imageView.setLayoutX(this.getX());
        imageView.setLayoutY(this.getY() - 30);
        imageView.setFitWidth(this.getWidth() + 30);
        imageView.setFitHeight(this.getHeight() + 30);
        Animation();
    }
}
