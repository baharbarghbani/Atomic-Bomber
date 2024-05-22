package animations;

import controller.ApplicationController;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import controller.ApplicationController;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.App;
import model.Game;
import view.AppViewController;


public class FreezingAnimation extends Transition {
    private ImageView freez;
    public FreezingAnimation(ImageView freez) {
        for (Transition t : Game.getInstance().getAllAnimations()){
            if (t instanceof PlaneAnimation || t instanceof BonusAnimation) continue;
            t.pause();
        }
//        Game.getInstance().getPlane().requestFocus();
        this.freez = freez;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(5000));
    }
    @Override
    protected void interpolate(double frac) {
        this.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Transition t : Game.getInstance().getAllAnimations()){
                    if (t.getStatus() == Status.PAUSED) t.play();
                }
                App.setFreezed(false);
                ColorAdjust colorAdjust = new ColorAdjust();
                ApplicationController.getStage().getScene().getRoot().setEffect(colorAdjust);
                AppViewController.gameLauncherController.root.getChildren().remove(freez);
            }
        });
    }

}


