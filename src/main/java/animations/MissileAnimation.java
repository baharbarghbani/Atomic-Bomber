package animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game;
import model.components.Bullet;
import model.components.Plane;
import view.GameLauncher;

public class MissileAnimation extends Transition {
    private Game game;
    private Bullet bullet;

    public MissileAnimation(Game game, Bullet bullet) {
        this.game = game;
        this.bullet = bullet;
        setCycleDuration(Duration.millis(10000));
        this.setCycleCount(-1);
        game.addAnimations(this);

    }

    @Override
    protected void interpolate(double v) {
        Plane plane = Game.getInstance().getPlane();
        if (plane != null && bullet != null) {
            bullet.setX(bullet.getX() + bullet.getVx());
            bullet.setY(bullet.getY() - bullet.getVy());
            if (bullet.getBoundsInParent().intersects(plane.getBoundsInParent())) {
                game.removeAnimation(this);
                GameLauncher.getInstance().root.getChildren().remove(bullet);
                plane.decreaseHP();
            }
        }
    }
}


