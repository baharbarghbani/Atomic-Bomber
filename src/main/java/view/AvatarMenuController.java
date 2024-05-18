package view;

import controller.ApplicationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.App;
import model.Result;
import view.ProfileMenu;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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


    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp");

    @FXML
    public void initialize() {
        ApplicationController applicationController = new ApplicationController();
        applicationController.setIcon();
        imageView1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar1.png"))));
        imageView2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar3.png"))));
        imageView3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar2.png"))));
        imageView4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar4.png"))));
        imageView5.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar5.png"))));
        imageView6.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/avatars/avatar6.png"))));
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
            ApplicationController.showAlert(result.getMessage(), "Changing avatar failed!", Alert.AlertType.WARNING, "/Images/backgrounds/background1.png");
        } else {
            ApplicationController.showAlert(result.getMessage(), "Changed avatar successfully!", Alert.AlertType.INFORMATION, "/Images/backgrounds/background1.png");
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

    public void setupDragAndDrop(AnchorPane root) {
        root.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles() && isValidImageFile(event.getDragboard().getFiles().get(0).getAbsolutePath())) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        root.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles() && isValidImageFile(event.getDragboard().getFiles().get(0).getAbsolutePath())) {
                App.getLoggedInUser().setAvatarPath(event.getDragboard().getFiles().get(0).getAbsolutePath());
            }
            event.consume();
        });
    }

    private boolean isValidImageFile(String filePath) {
        return SUPPORTED_EXTENSIONS.contains(getFileExtension(filePath).toLowerCase());
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}