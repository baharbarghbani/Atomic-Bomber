package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import model.App;
import model.Result;

import java.io.File;
import java.util.Objects;

import static view.MainMenuController.profileMenu;

public class AvatarMenuController {
    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public ImageView imageView3;
    @FXML
    public ImageView imageView4;
    @FXML
    public ImageView imageView5;
    @FXML
    public ImageView imageView6;
    @FXML
    public ImageView avatarImageView;

    @FXML
    public void initialize() {
        AppViewController.setIcon();
        imageView1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar1.png"))));
        imageView2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar3.png"))));
        imageView3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar2.png"))));
        imageView4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar4.png"))));
        imageView5.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar5.png"))));
        imageView6.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar6.png"))));
        Image avatar = new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.getLoggedInUser().getAvatarPath())));
        avatarImageView.setImage(avatar);
        avatarImageView.setOnDragOver(event -> {
            if (event.getGestureSource() != avatarImageView && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        avatarImageView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                File file = dragboard.getFiles().get(0);
                App.getLoggedInUser().setAvatarPath(file.getAbsolutePath());
                Image image = new Image(file.toURI().toString());
                avatarImageView.setImage(image);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }


    @FXML
    public void handleButtonClick(MouseEvent mouseEvent) {
        String path = getAvatarPath(mouseEvent);
        App.getLoggedInUser().setAvatarPath(path);
        ProfileMenu profileMenu = new ProfileMenu();
        try {
            profileMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseCustomAvatar() throws Exception {
        Result result = chooseAvatar();
        if (!result.isSuccess()) {
            AppViewController.showAlert(result.getMessage(), "Changing avatar failed!", Alert.AlertType.WARNING, "/Images/backgrounds/background1.png", true);
        } else {
            AppViewController.showAlert(result.getMessage(), "Changed avatar successfully!", Alert.AlertType.INFORMATION, "/Images/backgrounds/background1.png", true);
            ProfileMenu profileMenu = new ProfileMenu();
            profileMenu.start(ApplicationController.getStage());
        }
    }

    @FXML
    public void handleBackButton(MouseEvent mouseEvent) {
        try {
            profileMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAvatarPath(MouseEvent mouseEvent) {
        Button clickedButton = (Button) mouseEvent.getSource();
        StringBuilder path = new StringBuilder("/Images/avatars/");
        switch (clickedButton.getId()) {
            case "button1":
                path.append("avatar1.png");
                break;
            case "button2":
                path.append("avatar3.png");
                break;
            case "button3":
                path.append("avatar2.png");
                break;
            case "button4":
                path.append("avatar4.png");
                break;
            case "button5":
                path.append("avatar5.png");
                break;
            case "button6":
                path.append("avatar6.png");
                break;
        }
        return path.toString();
    }

    public Result chooseAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar Image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (imageFilter.getExtensions().contains("*." + fileExtension)) {
                String avatarAddress = selectedFile.getAbsolutePath();
                ApplicationController.loadUsers();
                ApplicationController.deleteUser(App.getLoggedInUser().getUsername());
                App.getLoggedInUser().setAvatarPath(avatarAddress);
                ApplicationController.saveUser();
                return new Result("Avatar changed successfully!", true);
            } else {
                return new Result("Unsupported file format!", false);
            }
        }
        return new Result("Avatar not changed!", false);
    }
}
