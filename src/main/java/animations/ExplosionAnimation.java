package animations;

import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.bombs.Bomb;
import model.bombs.Cluster;
import model.bombs.NuclearBomb;
import model.bombs.Rocket;
import model.components.Component;
import model.components.Plane;

import java.io.File;

public class ExplosionAnimation extends Transition {
    private Component component;
    private Bomb bomb;
    private Plane plane;
    private final boolean isPlane;
    private final boolean isBomb;
    private final boolean isComponent;

    public ExplosionAnimation(boolean isPlane, boolean isBomb, boolean isObject) {
        this.isPlane = isPlane;
        this.isBomb = isBomb;
        this.isComponent = isObject;
        explosion();
        setCycleDuration(javafx.util.Duration.millis(500));
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    @Override
    protected void interpolate(double v) {
        if (isComponent) {
            int frame = (int) Math.floor(v * 8);
            component.setBackground("/Images/fire/fire" + frame + ".png");
        } else if (isBomb) {
            if (bomb != null && bomb instanceof Rocket) {
                int frame = (int) Math.floor(v * 4);
                bomb.setBackground("/Images/planeExplosion/explosion" + frame + ".png");
            } else if (bomb != null && (bomb instanceof NuclearBomb || bomb instanceof Cluster)) {
                bomb.explodeNuclear();
            }

        } else if (isPlane) {
            int frame = (int) Math.floor(v * 4);
            plane.setBackground("/Images/planeExplosion/explosion" + frame + ".png");
        }
    }

    private void explosion() {
        Media media = new Media(new File("src/main/media/explosion.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.setAutoPlay(true);
    }
}
