package view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game;
import model.components.Truck;

public class TruckAnimation extends Transition {
    private final Truck truck;
    private final Game game;
    private final double speed;

    public TruckAnimation(Truck truck, Game game) {
        this.truck = truck;
        speed = truck.getSpeed();
        setCycleDuration(Duration.millis(2000));
        this.setCycleCount(-1);
        this.game = game;
        this.game.addAnimations(this);
    }

    @Override
    public void interpolate(double v) {
        double dx = speed;
        if (truck.getImageNumber() == 0) truck.moveRight(dx);
        else truck.moveLeft(dx);
    }
}
