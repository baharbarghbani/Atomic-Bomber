package model;

import controller.ApplicationController;

import java.util.ArrayList;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser;
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
        System.out.println(users);
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
        // Load users from JSON file
        ApplicationController.loadUsers();

        // Delete user with username "exampleUser"
        ApplicationController.deleteUser(user.getUsername());

        // Save modified users data to JSON file
        ApplicationController.saveUser();
    }

}
