package controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.App;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.Objects.requireNonNull;

public class ApplicationController {
    private static Stage stage;
    private static final String USERS_FILE_PATH = "src/main/database/Users.json";


    public static void setStage (Stage stage) {
        ApplicationController.stage = stage;
    }

    public static Stage getStage () {
        return stage;
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (Reader reader = new FileReader(USERS_FILE_PATH)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            Gson gson = new Gson();
            users = gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    public static void saveUser() {
        List<User> users = App.getUsers();
        try (Writer writer = new FileWriter(USERS_FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteUser(String username){
        List<User> userList = loadUsers();
        userList.removeIf(user -> user.getUsername().equals(username));
    }
    @FXML
    public static void showAlert(String errorMessage, String title, Alert.AlertType alertType, String imagePath){
        Platform.runLater(() -> {
            // UI-related code here
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(errorMessage);
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(ApplicationController.class.getResourceAsStream(imagePath))));
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);
            StackPane dialogPane = new StackPane();
            dialogPane.getChildren().add(imageView);
            alert.getDialogPane().setContent(dialogPane);

            // Apply the background to the header area
            alert.getDialogPane().setStyle("-fx-background-color: transparent;");
            // Set the background of the alert dialog
            alert.getDialogPane().setBackground(new Background(new BackgroundImage(
                    imageView.getImage(),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT)));
            alert.showAndWait();
        });
    }
    public void setIcon(){
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/icon.png")));
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(16);
        iconView.setFitWidth(16);
        ApplicationController.getStage().getIcons().add(iconView.getImage());
    }


}