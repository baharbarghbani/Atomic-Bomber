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

public class Tree extends Component {

    public Tree(double x, int imageNumber, Game game, Pane pane){
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
                pane.getChildren().remove(Tree.this);
            }
        });
        explodeAnimation.play();
    }
    @Override
    public void explodeByNuclear() {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/explosion.gif"))));
        imageView.setX(this.getX());
        imageView.setY(this.getY());
        NuclearBombAnimation nuclearBombAnimation = new NuclearBombAnimation(imageView, this);        game.addAnimations(nuclearBombAnimation);
        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Tree.this);
            }
        });
        nuclearBombAnimation.play();
    }
}
