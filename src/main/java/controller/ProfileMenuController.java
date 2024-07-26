package controller;

import enums.Menu;
import model.App;
import model.Result;
import model.User;
import view.AvatarMenu;
import view.LoginMenu;

public class ProfileMenuController {
    static LoginMenu loginMenu = new LoginMenu();

    public Result changeInfo(String newUsername, String newPassword) {
        if (newUsername.isEmpty() || newPassword.isEmpty())
            return new Result("Username and password can't be empty", false);

        if (App.findUserByUsername(newUsername) != null) return new Result("Username already exists", false);

        if (!Menu.USERNAME.getMatcher(newUsername).matches())
            return new Result("Username must be between 5 to 20 characters", false);

        if (!Menu.PASSWORD.getMatcher(newPassword).matches())
            return new Result("Please choose a stronger password!", false);


        User user = App.getLoggedInUser();
        ApplicationController.loadUsers();
        ApplicationController.deleteUser(user.getUsername());
        user.setUsername(newUsername);
        user.setPassword(newPassword);

        ApplicationController.saveUser();
        return new Result("Information changed successfully", true);
    }

    public void deleteAccount() {
        App.deleteUser(App.getLoggedInUser());
        App.setLoggedInUser(null);
        try {
            loginMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goToAvatarMenu() {
        AvatarMenu avatarMenu = new AvatarMenu();
        try {
            avatarMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        App.setLoggedInUser(null);
        try {
            loginMenu.start(ApplicationController.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
