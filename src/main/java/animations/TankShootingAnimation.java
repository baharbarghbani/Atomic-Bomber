package animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game;
import model.components.Bullet;
import model.components.Plane;
import model.components.ShootingTank;
import view.GameLauncher;

public class TankShootingAnimation extends Transition {
    private ShootingTank shootingTank;
    private double speed;
    private Game game;
    private Bullet bullet;
    private double angle;
    public TankShootingAnimation(ShootingTank shootingTank, Game game, double angle, Bullet bullet) {
        this.shootingTank = shootingTank;
        speed = shootingTank.getSpeed();
        this.game = game;
        this.bullet = bullet;
        this.angle = angle;
        setCycleDuration(Duration.millis(10000));
        this.setCycleCount(-1);
        game.addAnimations(this);

    }
    @Override
    protected void interpolate(double v) {
        Plane plane = Game.getInstance().getPlane();
        bullet.setX(bullet.getX() + bullet.getVx());
        bullet.setY(bullet.getY() - bullet.getVy());
        if(bullet.getBoundsInParent().intersects(plane.getBoundsInParent())){
            game.removeAnimation(this);
            GameLauncher.getInstance().root.getChildren().remove(bullet);
            if (plane != null)
                 plane.decreaseHP();
        }
    }
}
