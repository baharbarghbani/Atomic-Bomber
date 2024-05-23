package model;

import controller.ApplicationController;

import java.util.ArrayList;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser;
    private static int gameDifficulty = 1;
    private static double migTimeCoef = 1.0;
    private static boolean isGrayScale;
    private static boolean isMuted = false;
    private static boolean isFreezed = false;
    private static boolean isPaused = false;

    public static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void loadUsers(ArrayList<User> users) {
        App.users = users;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static void deleteUser(User user) {
        users.remove(user);
        ApplicationController.loadUsers();
        ApplicationController.deleteUser(user.getUsername());
        ApplicationController.saveUser();
    }

    public static int getGameDifficulty() {
        return gameDifficulty;
    }

    public static void setGameDifficulty(int gameDifficulty) {
        App.gameDifficulty = gameDifficulty;
    }

    public static double getMigTimeCoef() {
        return migTimeCoef;
    }

    public static void setMigTimeCoef(double migTimeCoef) {
        App.migTimeCoef = migTimeCoef;
    }

    public static boolean isGrayScale() {
        return isGrayScale;
    }

    public static void setGrayScale(boolean grayScale) {
        isGrayScale = grayScale;
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static void setMuted(boolean muted) {
        isMuted = muted;
    }

    public static boolean isFreezed() {
        return isFreezed;
    }

    public static void setFreezed(boolean isFreezed) {
        App.isFreezed = isFreezed;
    }

    public static boolean isPaused() {
        return isPaused;
    }

    public static void setPaused(boolean isPaused) {
        App.isPaused = isPaused;
    }

}
