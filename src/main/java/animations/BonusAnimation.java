package animations;

import javafx.animation.Transition;
import model.App;
import model.Game;
import model.components.Bomb;
import model.components.NuclearBomb;
import model.components.Plane;
import view.GameLauncherController;

public class BonusAnimation extends Transition {
    private Bomb bomb;
    public BonusAnimation(Bomb bomb){
        this.bomb = bomb;
        this.setCycleDuration(javafx.util.Duration.millis(10000));
    }
    @Override
    protected void interpolate(double v) {
        if (bomb instanceof NuclearBomb){
            bomb.setY(bomb.getY() + bomb.getVy());
            double vx = bomb.getVx() * -1;
            bomb.setX(bomb.getX() + vx);
            if (hasCollision()){
                App.getLoggedInUser().addNuclearBomb();
                GameLauncherController.updateNuclearBombCount();
                this.pause();
                bomb.remove();
            }
        }
    }
    public boolean hasCollision(){
        Plane plane = Game.getInstance().getPlane();
        if (bomb.getBoundsInParent().intersects(plane.getBoundsInParent())){
            return true;
        }
        return false;
    }

}
