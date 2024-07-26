package view.animations;

import javafx.animation.Transition;
import model.Game;
import model.components.Mig;

public class MigAnimation extends Transition {
    private final Mig mig;

    public MigAnimation(Mig mig) {
        this.mig = mig;
        Game.getInstance().addAnimations(this);
        setCycleDuration(javafx.util.Duration.millis(15000));
    }

    @Override
    protected void interpolate(double v) {
        double dx = mig.getSpeed();
        mig.move(dx);

    }
}
