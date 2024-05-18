package controller;

import controller.ApplicationController;
import enums.Menu;
import model.App;
import model.Result;
import model.User;

import java.util.Random;


public class LoginMenuController {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String NUMBER_FOR_AVATAR = "123456";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static final int USERNAME_LENGTH = 8; // Length of the random username
    private static final int PASSWORD_LENGTH = 10;
    private static final Random random = new Random();


    public Result login(String username, String password){
        User user = App.findUserByUsername(username);
        if (username.isEmpty())
            return new Result("Please fill in username field.", false);

        if (password.isEmpty())
            return new Result("Please fill in password field.", false);

        if (user == null)
            return new Result("Account not found.\nPlease sign up first.", false);

        if (!user.getPassword().equals(password)){
            return new Result("Incorrect password for user " + username, false);
        }
        App.setLoggedInUser(user);
        return new Result("Login successful.", true);
    }

    public Result signUp(String username, String password) {
        if (App.findUserByUsername(username) != null){
            return new Result("Username already exists.", false);
        }
        if(!Menu.USERNAME.getMatcher(username).matches()){
            return new Result("Username must be between 5 to 20 characters", false);
        }
        if(!Menu.PASSWORD.getMatcher(password).matches()){
            return new Result("Please use a password with at least 8 characters,\n" +
                    "including one uppercase letter,\n" +
                    "one lowercase letter, one digit,\n" +
                    "and one special character.", false);
        }
        String imagePath = generateRandomAvatar();
        User user = new User(username, password, false, imagePath);
        App.addUser(user);
        App.setLoggedInUser(user);
        ApplicationController.saveUser();
        return new Result("Account created successfully.", true);
    }

    public Result startGameAsGuest() {
        String username = generateRandomString(USERNAME_LENGTH);
        String password = generateRandomString(PASSWORD_LENGTH);
        String avatarPath = generateRandomAvatar();
        User user = new User(username, password, true, avatarPath);
        App.setLoggedInUser(user);
        App.addUser(user);
        ApplicationController.loadUsers();
        ApplicationController.saveUser();

        return new Result("Guest account created successfully.\n" +
                "Your username is " + username, true);
    }
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char randomChar = DATA_FOR_RANDOM_STRING.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
    public Result recoverPassword(String username){
        if (username.isEmpty())
            return new Result("Please fill in username field.", false);
        User user = App.findUserByUsername(username);
        if (user == null)
            return new Result("Username " + username + " not found.", false);
        return new Result("Your password is: " + user.getPassword(), true);
    }
    public String generateRandomAvatar(){
        StringBuilder sb = new StringBuilder();
        sb.append("/Images/avatars/avatar");
        int randomNumber = random.nextInt(6) + 1;
        sb.append(randomNumber).append(".png");
        return sb.toString();
    }
}
