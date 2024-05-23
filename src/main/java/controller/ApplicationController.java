package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.stage.Stage;
import model.App;
import model.Game;
import model.GameScore;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ApplicationController {
    private static final String USERS_FILE_PATH = "src/main/database/Users.json";
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ApplicationController.stage = stage;
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (Reader reader = new FileReader(USERS_FILE_PATH)) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
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

    public static void deleteUser(String username) {
        List<User> userList = loadUsers();
        userList.removeIf(user -> user.getUsername().equals(username));
    }

    public static void saveGameScore() {
        List<GameScore> gameScores = GameScore.getAllGameScores();
        try (Writer writer = new FileWriter("src/main/database/GameScores.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(gameScores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<GameScore> loadGameScores() {
        List<GameScore> gameScores = new ArrayList<>();
        try (Reader reader = new FileReader("src/main/database/GameScores.json")) {
            Type gameScoreListType = new TypeToken<ArrayList<GameScore>>() {
            }.getType();
            Gson gson = new Gson();
            gameScores = gson.fromJson(reader, gameScoreListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameScores;
    }


    public static String getGameResult() {
        if (Game.getInstance().getPlane() != null) {
            return "Won!";
        } else return "Lost!";
    }
    

    public static String getAccuracy() {
        return String.valueOf(App.getLoggedInUser().getAccuracy());
    }

    public static String getKill() {
        return String.valueOf(App.getLoggedInUser().getKill());
    }
}