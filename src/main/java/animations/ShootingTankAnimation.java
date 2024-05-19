package animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game;
import model.components.Plane;
import model.components.ShootingTank;
import model.components.Tank;

public class ShootingTankAnimation extends Transition {
    private ShootingTank shootingTank;
    private double speed;
    private Game game;
    public ShootingTankAnimation(ShootingTank shootingTank, Game game) {
        this.shootingTank = shootingTank;
        speed = shootingTank.getSpeed();
        this.game = game;
        setCycleDuration(Duration.millis(2000));
        this.setCycleCount(-1);
        game.addAnimations(this);

    }
    @Override
    protected void interpolate(double v) {
        double dx = speed;
        Plane plane = game.getPlane();
        if (shootingTank.getImageNumber() == 0)
            shootingTank.moveRight(dx);
        else
            shootingTank.moveLeft(dx);
        double x = shootingTank.getX();
        double y = shootingTank.getY();
        double xPrime = plane.getX();
        double yPrime = plane.getY();

    }
}
