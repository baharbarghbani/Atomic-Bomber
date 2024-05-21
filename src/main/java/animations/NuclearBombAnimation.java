package animations;

import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Game;
import view.GameLauncher;

public class NuclearBombAnimation extends Transition {
    public ImageView imageView;
    public NuclearBombAnimation(ImageView imageView) {
        this.imageView = imageView;
//        Game.getInstance().getPlane().requestFocus();
        if (GameLauncher.getInstance().root.getChildren().contains(imageView))
            GameLauncher.getInstance().root.getChildren().remove(imageView);
        GameLauncher.getInstance().root.getChildren().add(imageView);
        setCycleDuration(Duration.millis(1000));
        setCycleCount(1);// Set the duration of the transition
//        setAutoReverse(true);
    }
    @Override
    protected void interpolate(double v) {
        imageView.setOpacity(1-v);
    }


}
