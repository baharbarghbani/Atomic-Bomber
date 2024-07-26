package view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game;
import model.components.Tank;

public class TankAnimation extends Transition {
    private Tank tank;
    private Game game;
    private double speed;
    public TankAnimation(Tank tank, Game game) {
        this.tank = tank;
        speed = tank.getSpeed();
        setCycleDuration(Duration.millis(2000));
        this.setCycleCount(-1);
        this.game = game;
        this.game.addAnimations(this);
    }
    @Override
    public void interpolate(double v) {
        double dx = speed;
        if (tank.getImageNumber() == 0)
            tank.moveRight(dx);
        else
            tank.moveLeft(dx);
    }
}
