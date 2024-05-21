package animations;

import javafx.animation.Transition;
import model.Game;
import model.components.Mig;

public class MigAnimation extends Transition {
    private Mig mig;

    public MigAnimation(Mig mig){
        this.mig = mig;
        Game.getInstance().addAnimations(this);
//        Game.getInstance().getPlane().requestFocus();
        setCycleDuration(javafx.util.Duration.millis(15000));
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> GameController.createMig(Game.getInstance().getWave(), Game.getInstance(), GameLauncher.getInstance().root)));
    }
    @Override
    protected void interpolate(double v) {
        double dx = mig.getSpeed();
        mig.move(dx);

    }
}
