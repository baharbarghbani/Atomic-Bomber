
package animations;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;
import model.components.Plane;
import view.GameLauncher;

public class PlaneAnimation extends Transition {
    private final Game game;
    private final Pane root;
    private final Plane plane;
    private final double speed = 3.3;
    private boolean up, down, left, right;
    private boolean isFlipped = false;

    private Timeline checkTank;

    public PlaneAnimation(Game game, Pane root, Plane plane) {
        this.game = game;
        this.root = root;
        this.plane = plane;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
        plane.setScaleX(-1);
        game.addAnimations(this);
        checkTank = new Timeline(new KeyFrame(Duration.seconds(0.5), actionEvent -> GameController.checkPlaneInTankArea(plane)));
        checkTank.setCycleCount(-1);
        checkTank.play();

    }

    @Override
    protected void interpolate(double v) {
        double piAngle = 2 * Math.PI;
        double dAngle = Math.PI / 180;
        plane.setRotate(-plane.angle * 180 / Math.PI);
        if ((this.plane.angle >= (piAngle / 4) && this.plane.angle < (3 * piAngle / 4)) && !isFlipped) {
            isFlipped = true;
            plane.flipped = true;
            plane.setScaleY(-1);
        }
        if (((this.plane.angle > 0 && this.plane.angle < (piAngle / 4)) || (this.plane.angle >= (3 * piAngle / 4) && this.plane.angle < (piAngle))) && isFlipped) {
            isFlipped = false;
            plane.flipped = false;
            plane.setScaleY(1);
        }
        if (plane.angle < 0) {
            this.plane.angle = piAngle + this.plane.angle;
        } else if (plane.angle > piAngle) {
            this.plane.angle = this.plane.angle - piAngle;
        }

        double y = plane.getY() - speed * Math.sin(plane.angle);
        double x = plane.getX() + speed * Math.cos(plane.angle);

        if (isUp()) {
            if ((this.plane.angle >= (piAngle / 4) && this.plane.angle < (3 * piAngle / 4))) {
                plane.angle -= dAngle;
            }
            if ((this.plane.angle > 0 && this.plane.angle < (piAngle / 4)) || (this.plane.angle >= (3 * piAngle / 4) && this.plane.angle < (piAngle))) {
                plane.angle += dAngle;
            }
        }
        if (isDown()) {
            if ((this.plane.angle >= (piAngle / 4) && this.plane.angle < (3 * piAngle / 4))) {
                plane.angle += dAngle;
            }
            if ((this.plane.angle > 0 && this.plane.angle < (piAngle / 4)) || (this.plane.angle >= (3 * piAngle / 4) && this.plane.angle < (piAngle))) {
                plane.angle -= dAngle;
            }
        }
        if (isRight()) {
            if ((this.plane.angle < (piAngle / 2) && this.plane.angle >= 0)) {
                plane.angle -= dAngle;
            }
            if ((this.plane.angle >= (piAngle / 2) && this.plane.angle < piAngle)) {
                plane.angle += dAngle;
            }
        }
        if (isLeft()) {
            if ((this.plane.angle >= 0 && this.plane.angle < (piAngle / 2))) {
                plane.angle += dAngle;
            }
            if ((this.plane.angle >= (piAngle / 2) && this.plane.angle < piAngle)) {
                plane.angle -= dAngle;
            }
        }
        if (y <= 0) {
            this.plane.angle = piAngle - this.plane.angle;
            y = plane.getY();
        }
        if (x <= -Plane.WIDTH) {
            x = root.getWidth();
        } else if (x >= root.getWidth()) {
            x = 0;
        }
        plane.setY(y);
        plane.setX(x);
        if (y > Game.HEIGHT - 180 - Plane.HEIGHT) {
            plane.explode();
            plane.remove();
            this.stop();
        }
//        if (GameController.checkPlaneInTankArea(plane)) {
//            ShootingTank tank = GameController.getTankInArea(plane);
//            assert tank != null;
//            double angle = Math.atan((tank.getY() - plane.getY()) / (tank.getX() - plane.getX()));
//            tank.shoot(angle, Math.cos(angle) * tank.getSpeed(), Math.sin(angle) * tank.getSpeed());
//            plane.explode();
//            plane.remove();
//            this.stop();
//            EndGameMenu.getInstance().show();

    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    public double getSpeed(){
        return speed;
    }
}