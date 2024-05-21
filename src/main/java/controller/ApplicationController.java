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
import model.Game;
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


    public static String getGameResult() {
        if (Game.getInstance().getPlane() != null){
            return "Won!";
        }else return "Lost!";
    }

    public static String getWave() {
        return String.valueOf(Game.getInstance().getWave());
    }

    public static String getAccuracy() {
        return String.valueOf(App.getLoggedInUser().getAccuracy());
    }

    public static String getKill() {
        return String.valueOf(App.getLoggedInUser().getKill());
    }
}