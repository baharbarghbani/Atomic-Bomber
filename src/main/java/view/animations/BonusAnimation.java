package view.animations;

import javafx.animation.Transition;
import model.App;
import model.Game;
import model.bombs.Bomb;
import model.bombs.Cluster;
import model.bombs.NuclearBomb;
import model.components.Plane;
import view.GameLauncherController;

public class BonusAnimation extends Transition {
    private final Bomb bomb;

    public BonusAnimation(Bomb bomb) {
        this.bomb = bomb;
        this.setCycleDuration(javafx.util.Duration.millis(15000));
    }

    @Override
    protected void interpolate(double v) {
        if (bomb instanceof NuclearBomb) {
            bomb.setY(bomb.getY() + bomb.getVy());
            double vx = bomb.getVx() * -1;
            bomb.setVx(vx);
            bomb.setX(bomb.getX() + vx);
            if (hasCollision()) {
                App.getLoggedInUser().addNuclearBomb();
                GameLauncherController.updateNuclearBombCount();
                this.pause();
                bomb.remove();
            }
        }
        if (bomb instanceof Cluster) {
            bomb.setY(bomb.getY() + bomb.getVy());
            double vx = bomb.getVx() * -1;
            bomb.setVx(vx);
            bomb.setX(bomb.getX() + vx);
            if (hasCollision()) {
                App.getLoggedInUser().addCluster();
                GameLauncherController.updateClusterBombCount();
                this.pause();
                bomb.remove();
            }
        }
    }

    public boolean hasCollision() {
        Plane plane = Game.getInstance().getPlane();
        if (plane != null) {
            return bomb.getBoundsInParent().intersects(plane.getBoundsInParent());
        }
        return false;
    }

}
