package animations;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Game;
import model.bombs.Bullet;
import model.components.Plane;
import view.GameLauncher;

import java.io.File;

public class MissileAnimation extends Transition {
    private final Game game;
    private final Bullet bullet;

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
                explosion();
                GameLauncher.getInstance().root.getChildren().remove(bullet);
                plane.decreaseHP();
                PlaneDamage planeDamage = new PlaneDamage(plane);
                planeDamage.play();
                Timeline timeline = getTimeline(planeDamage, plane);
                timeline.play();
                GameController.checkHP();
                this.pause();
            }
        }
    }

    private Timeline getTimeline(PlaneDamage planeDamage, Plane plane) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            planeDamage.pause();
            plane.setImage("/Images/icon.png");
        }));
        timeline.setCycleCount(1);
        return timeline;
    }

    private void explosion() {
        Media media = new Media(new File("src/main/media/explosion.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.setAutoPlay(true);
    }
}


