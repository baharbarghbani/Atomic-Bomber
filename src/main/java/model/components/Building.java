package model.components;

import view.animations.ExplosionAnimation;
import view.animations.NuclearBombAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;

public class Building extends Component {
    public Building(double x, Game game, Pane pane) {
        super(x, 675, 60, 60, game, pane);
        kill = 2;
        hasBonus = true;
        this.setFill(new ImagePattern(new Image(Building.class.getResource("/Images/houses/house1.png").toExternalForm())));
    }

    @Override
    public void setBackground(String url) {
        this.setFill(new ImagePattern(new Image(Building.class.getResource(url).toExternalForm())));
    }

    @Override
    public void remove() {
        game.getWave().getAllObjects().remove(this);
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
        pane.getChildren().remove(Building.this);

        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                game.removeAnimation(nuclearBombAnimation);
                game.getWave().removeObject(Building.this);
                game.getWave().removeBuilding(Building.this);

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

    @Override
    public void explode() {
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false, false, true);
        explodeAnimation.setComponent(this);
        game.addAnimations(explodeAnimation);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Building.this);
                game.removeAnimation(explodeAnimation);
                game.getWave().removeObject(Building.this);
                game.getWave().removeBuilding(Building.this);
            }
        });
        explodeAnimation.play();
    }

}
