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

public class Building extends Component{
    public Building(double x, Game game, Pane pane){
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
    public void remove(){
        game.getWave().getAllObjects().remove(this);
    }

    public void explodeByNuclear() {
        imageView.setLayoutX(this.getX());
        imageView.setLayoutY(this.getY() - 30);
        imageView.setFitWidth(this.getWidth() + 40);
        imageView.setFitHeight(this.getHeight() + 40);
        NuclearBombAnimation nuclearBombAnimation = new NuclearBombAnimation(imageView);
        game.addAnimations(nuclearBombAnimation);
        pane.getChildren().remove(Building.this);

        nuclearBombAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(imageView);
                game.removeAnimation(nuclearBombAnimation);
            }
        });
        nuclearBombAnimation.play();
    }

    @Override
    public void explode(){
        ExplosionAnimation explodeAnimation = new ExplosionAnimation(false,false,true);
        explodeAnimation.setComponent(this);
        game.addAnimations(explodeAnimation);
        explodeAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pane.getChildren().remove(Building.this);
                game.removeAnimation(explodeAnimation);
            }
        });
        explodeAnimation.play();
    }

}
