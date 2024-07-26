package view.animations;

import javafx.animation.Transition;
import model.components.Plane;

public class PlaneDamage extends Transition {
    Plane plane;

    public PlaneDamage(Plane plane) {
        this.plane = plane;
        setCycleDuration(javafx.util.Duration.millis(1000));
        setCycleCount(1);
    }

    @Override
    public void interpolate(double v) {
        int frame = (int) Math.floor(v * 5);
        plane.setImage("/Images/planeExplosion/explosion" + frame + ".png");
    }
}
