package animations;

import javafx.animation.Transition;
import model.components.*;

public class ExplosionAnimation extends Transition {
    private Component component;
    private Bomb bomb;
    private Plane plane;
    private boolean isPlane;
    private boolean isBomb;
    private boolean isComponent;
    public ExplosionAnimation(boolean isPlane, boolean isBomb, boolean isObject){
        this.isPlane = isPlane;
        this.isBomb = isBomb;
        this.isComponent = isObject;
        setCycleDuration(javafx.util.Duration.millis(500));
    }
    public void setPlane(Plane plane){
        this.plane = plane;
    }
    public void setComponent(Component component){
        this.component = component;
    }
    public void setBomb(Bomb bomb){
        this.bomb = bomb;
    }
    @Override
    protected void interpolate(double v) {
        if (isComponent) {
            int frame = (int) Math.floor(v * 8);
            component.setBackground("/Images/fire/fire" + frame + ".png");
        }
        else if (isBomb) {
            if (bomb instanceof Rocket) {
                int frame = (int) Math.floor(v * 4);
                bomb.setBackground("/Images/planeExplosion/explosion" + frame + ".png");
            } else if (bomb instanceof NuclearBomb || bomb instanceof Cluster) {
                bomb.explodeNuclear();
            }

        }
        else if (isPlane) {
            int frame = (int) Math.floor(v * 4);
            plane.setBackground("/Images/planeExplosion/explosion" + frame + ".png");
        }
    }
}
