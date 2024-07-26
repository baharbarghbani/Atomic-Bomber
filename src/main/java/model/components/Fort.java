package model.components;

import view.animations.ExplosionAnimation;
import view.animations.NuclearBombAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;

public class Fort extends Component {
    public Fort(double x, Game game, Pane pane) {
        super(x, 650, 80, 80, game, pane);
        kill = 3;
        hasBonus = true;
        this.setFill(new ImagePattern(new Image(Fort.class.getResource("/Images/fort.png").toExternalForm())));
    }

    @Override
    public void setBackground(String url) {
        this.setFill(new ImagePattern(new Image(Fort.class.getResource(url).toExternalForm())));
    }

    @Override
    public void remove() {
        game.getWave().getAllObjects().remove(this);
    }

    @Override
    public void explode() {
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false, false, true);
        explodeAnimation.setComponent(this);
        game.addAnimations(explodeAnimation);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Fort.this);
                game.getWave().removeObject(Fort.this);
                game.removeAnimation(explodeAnimation);
                game.getWave().removeFort(Fort.this);
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
        pane.getChildren().remove(Fort.this);

        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                game.removeAnimation(nuclearBombAnimation);
                game.getWave().removeObject(Fort.this);
                game.getWave().removeFort(Fort.this);

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
