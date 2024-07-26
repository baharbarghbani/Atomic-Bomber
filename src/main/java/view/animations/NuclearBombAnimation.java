package view.animations;

import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.GameLauncher;

public class NuclearBombAnimation extends Transition {
    public ImageView imageView;

    public NuclearBombAnimation(ImageView imageView) {
        this.imageView = imageView;
        GameLauncher.getInstance().root.getChildren().remove(imageView);
        GameLauncher.getInstance().root.getChildren().add(imageView);
        setCycleDuration(Duration.millis(1000));
        setCycleCount(1);
    }

    @Override
    protected void interpolate(double v) {
        imageView.setOpacity(1 - v);
    }


}
