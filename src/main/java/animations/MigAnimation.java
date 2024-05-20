package animations;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game;
import model.components.Mig;
import model.components.Tree;
import view.GameLauncher;
import view.GameLauncherController;

import java.sql.Time;

public class MigAnimation extends Transition {
    private Mig mig;

    public MigAnimation(Mig mig){
        this.mig = mig;
        setCycleDuration(javafx.util.Duration.millis(15000));
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> GameController.createMig(Game.getInstance().getWave(), Game.getInstance(), GameLauncher.getInstance().root)));
    }
    @Override
    protected void interpolate(double v) {
        double dx = mig.getSpeed();
        mig.move(dx);

    }
}
