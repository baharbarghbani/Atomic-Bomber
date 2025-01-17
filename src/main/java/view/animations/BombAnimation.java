package view.animations;

import javafx.animation.Transition;
import model.Game;
import model.bombs.Bomb;
import model.bombs.Cluster;
import view.GameLauncher;
import view.GameLauncherController;

public class BombAnimation extends Transition {
    private static boolean clusterHasExploded = false;
    private final Bomb bomb;

    public BombAnimation(Bomb bomb) {
        this.bomb = bomb;
        this.setCycleCount(-1);
        this.setCycleDuration(javafx.util.Duration.millis(100));
        Game.getInstance().addAnimations(this);

    }

    @Override
    protected void interpolate(double v) {
        double vx = bomb.getVx();
        double vy = bomb.getVy();
        double angle = Math.atan(vy / vx) + bomb.getAngle();

        bomb.setRotate(angle * 180 / Math.PI);
        double gravity = 0.1;
        vy += gravity;
        bomb.setVy(vy);
        double y = bomb.getY() + vy;
        double x = bomb.getX() + vx;
        bomb.getChildren().get(1).setRotate(angle);
        bomb.setY(y);
        bomb.setX(x);
        if (bomb instanceof Cluster cluster && !clusterHasExploded) {
            if (bomb.getY() > 400) {
                clusterHasExploded = true;
                cluster.divide();
                this.pause();
            }
        }
        if (GameLauncherController.hasCollision(bomb)) {
            this.stop();
            GameLauncher.getInstance().root.getChildren().remove(bomb);
            Game.getInstance().removeAnimation(this);
        }
        while (GameLauncherController.hasCollision(bomb)) {
            GameLauncherController.checkCollision(bomb, this);
        }
        if (bomb.getY() > 700) {
            this.stop();
            Game.getInstance().removeAnimation(this);
            bomb.explode();
        }

    }

    public static void setClusterHasExploded(boolean clusterHasExploded1) {
        clusterHasExploded = clusterHasExploded1;
    }
}
