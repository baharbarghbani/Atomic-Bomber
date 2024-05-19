package view;

import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PauseMenu extends Application {
    static PauseMenuController pauseMenuController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationController.setStage(primaryStage);
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PauseMenuFxml.fxml"));
            Pane root = fxmlLoader.load();
            pauseMenuController = fxmlLoader.getController();
            Scene scene = new Scene(root);
            root.getStyleClass().add("pause-menu");
            scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
