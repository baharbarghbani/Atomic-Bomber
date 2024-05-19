package animations;

import controller.GameController;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game;
import model.components.Plane;
import model.components.ShootingTank;
import model.components.Tank;

public class TankAnimation extends Transition{
    private Tank tank;
    private double speed;
    private Game game;
    public TankAnimation(Tank tank, Game game) {
        this.tank = tank;
        speed = tank.getSpeed();
        setCycleDuration(Duration.millis(2000));
        this.setCycleCount(-1);
        this.game = game;
        game.addAnimations(this);

    }

    @Override
    protected void interpolate(double v) {
        double dx = speed;
        if (tank.getImageNumber() == 0)
            tank.moveRight(dx);
        else
            tank.moveLeft(dx);
    }
}
