package model.components;

import animations.ExplosionAnimation;
import animations.NuclearBombAnimation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import model.Game;

public class Tree extends Component {

    public Tree(double x, int imageNumber, Game game, Pane pane) {
        super(x, 670, 60, 60, game, pane);
        kill = 0;
        hasBonus = false;
        this.setFill(new ImagePattern(new Image(Tree.class.getResource("/Images/trees/tree" + imageNumber + ".png").toExternalForm())));
    }

    @Override
    public void setBackground(String url) {
        this.setFill(new ImagePattern(new Image(Tree.class.getResource(url).toExternalForm())));
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
                pane.getChildren().remove(Tree.this);
                game.getWave().removeObject(Tree.this);
                game.removeAnimation(explodeAnimation);
                game.getWave().removeTree(Tree.this);
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
        pane.getChildren().remove(Tree.this);

        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                game.removeAnimation(nuclearBombAnimation);
                game.getWave().removeObject(Tree.this);
                game.getWave().removeTree(Tree.this);
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
