package view;

import controller.ApplicationController;
import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static view.LoginMenuController.applicationController;
import static view.MainMenuController.menuController;
import static view.MainMenuController.profileMenu;

public class ProfileMenu extends Application {
    static Scene currentScene;
    public ImageView imageView;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationController.setStage(stage);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(requireNonNull(getClass().getResource("/FXML/ProfileMenuFxml.fxml")));

            // Set the icon for the stage
            stage.setTitle("Profile Menu");
            // Create custom title bar layout
            // Set the scene
            currentScene = new Scene(root);
            root.getStyleClass().add("profile-menu");
            currentScene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
//            stage.setTitle("Login Menu");
            stage.setScene(currentScene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() throws Exception{
        applicationController.setIcon();
        Image image = menuController.imageInitialize(imageView);
        imageView.setImage(image);
    }
    @FXML
    public void changeUsername(){
        try {
            MenuController.changeUsername.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void deleteAccount(){
        MenuController.profileMenuController.deleteAccount();

    }
    @FXML
    public void logout(){
        MenuController.profileMenuController.logout();

    }
    @FXML
    public void openAvatarMenu(){
        MenuController.profileMenuController.goToAvatarMenu();
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
