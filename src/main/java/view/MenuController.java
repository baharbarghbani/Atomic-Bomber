package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class MenuController{
    public Image imageInitialize(ImageView imageView) throws FileNotFoundException {
        if (App.getLoggedInUser() == null) {
            return null;
        }
        String path = App.getLoggedInUser().getAvatarPath();
        if (path.contains("/Images/avatars"))
            return new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        else
            return new Image(new FileInputStream(path));
    }


}
