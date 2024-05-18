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

public class Fort extends Component {
    public Fort(double x, Game game, Pane pane){
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
                pane.getChildren().remove(Fort.this);
            }
        });
        explodeAnimation.play();
    }
    @Override
    public void explodeByNuclear() {
        NuclearBombAnimation nuclearBombAnimation = new NuclearBombAnimation(imageView, this);        game.addAnimations(nuclearBombAnimation);
        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Fort.this);
            }
        });
        nuclearBombAnimation.play();
    }
}
