package view;
import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Game;

import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class GameLauncher extends Application {
    //    public static Label killNumberText;
//    public static Label rocketNumberText;
//    public static Label nuclearBombNumberText;
//    public static Label waveNumberText;
//    public static Label clusterBombNumberText;
//    public static ImageView pauseButtonImage;
    public Pane root;
    public Pane pauseMenu;
    public Pane endGameMenu;
    public Scene scene;
    protected static GameLauncher instance;

    public static void main(String[] args) {
        launch(args);
    }
    public static GameLauncher getInstance(){
        return instance;
    }
    public static void setInstance(GameLauncher gameLauncher){
        instance = gameLauncher;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ApplicationController.setStage(primaryStage);
        setInstance(this);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/GameFxml.fxml")));
        root = fxmlLoader.load();
        root.getStyleClass().add("game");
        scene = new Scene(root);
        AppViewController.gameLauncherController.setRoot(root);
        AppViewController.gameLauncherController.createGameLauncher(scene);
        primaryStage.setTitle("Atomic Bomber");
        primaryStage.setScene(scene);
        AppViewController.gameLauncherController.checkGrayScale(primaryStage);
        primaryStage.show();
        Game.getInstance().getPlane().requestFocus();
    }
//    public void createGameLauncher() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/GameFxml.fxml"));
//        root = fxmlLoader.load();
//        root.getStyleClass().add("game");
//        scene = new Scene(root);
//        AppController.gameLauncherController.setRoot(root);
//        AppController.gameLauncherController.createGame();
//        AppController.gameLauncherController.creatPlane(root);
//        Game game = Game.getInstance();
//        plane = game.getPlane();
//        game.setPlane(plane);
//        Wave wave = GameController.createWave(game);
//        GameController.checkMigTime(game, game.getWave(), root);
//        plane.requestFocus();
//        scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
//        root.getChildren().add(plane);
//        plane.requestFocus();
//        GameLauncherController.addComponents();
//        ComponentCreator.createTanksAnimation(wave.getTanks(), game, 3);
//        ComponentCreator.createTrucksAnimation(wave.getTrucks(), game);
//    }
//    @FXML
//    public void initialize() throws FileNotFoundException {
//        AppController.appController.setIcon();
//        rocket.setImage(AppController.gameLauncherController.getRocket());
//        rocketNumber.setText("X" + App.getLoggedInUser().getRocketNumber());
//        nuclearBomb.setImage(AppController.gameLauncherController.getNuclearBomb());
//        nuclearBombNumber.setText("X" + App.getLoggedInUser().getNuclearBombNumber());
//        killNumberImage.setImage(AppController.gameLauncherController.getKillNumber());
//        killNumber.setText("X" + App.getLoggedInUser().getKill());
//        killNumberText = killNumber;
//        nuclearBombNumberText = nuclearBombNumber;
//        rocketNumberText = rocketNumber;
//        username.setText(App.getLoggedInUser().getUsername());
//        userAvatar.setImage(AppController.appController.imageInitialize());
//        waveNumber.setText("Wave 1");
//        waveNumberText = waveNumber;
//        pauseButton.setImage(new Image(requireNonNull(getClass().getResourceAsStream("/Images/pause.png"))));
//        pauseButtonImage = pauseButton;
//        clusterBomb.setImage(new Image(requireNonNull(getClass().getResourceAsStream("/Images/bombs/malware.png"))));
//        clusterBombNumber.setText("X" + App.getLoggedInUser().getClusterBombNumber());
//        clusterBombNumberText = clusterBombNumber;
//        pauseMenu.setVisible(false);
//    }
//    public void createGame() {
//        game = new Game(root);
//        Game.setInstance(game);
//    }
//
//    private void creatPlane(Pane pane) {
//        plane = new Plane(game, pane);
//        plane.setFocusTraversable(true);
//        PlaneAnimation planeAnimation = new PlaneAnimation(game, pane, plane);
//        planeAnimation.play();
//        plane.requestFocus();
//        plane.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.UP) {
//                planeAnimation.setUp(true);
//                plane.setDisable(false);
//            }
//            if (event.getCode() == KeyCode.DOWN) {
//                planeAnimation.setDown(true);
//                plane.setDisable(false);
//            }
//            if (event.getCode() == KeyCode.LEFT) {
//                planeAnimation.setLeft(true);
//                plane.setDisable(false);
//            }
//            if (event.getCode() == KeyCode.RIGHT) {
//                planeAnimation.setRight(true);
//                plane.setDisable(false);
//            }
//        });
//        plane.setOnKeyReleased(event -> {
//            plane.setFocusTraversable(true);
//            plane.requestFocus();
//            if (event.getCode() == KeyCode.RIGHT) {
//                planeAnimation.setRight(false);
//                plane.setDisable(false);
//            }
//            if (event.getCode() == KeyCode.LEFT) {
//                planeAnimation.setLeft(false);
//                plane.setDisable(false);
//            }
//            if (event.getCode() == KeyCode.UP) {
//                planeAnimation.setUp(false);
//                plane.setDisable(false);
//            }
//            if (event.getCode() == KeyCode.DOWN) {
//                planeAnimation.setDown(false);
//                plane.setDisable(false);
//            }
//            if (event.getCode() == KeyCode.SPACE) {
//                GameController.increaseShootingCount();
//                System.out.println(App.getLoggedInUser().getShootingCount());
//                plane.setDisable(false);
//                double angle = -plane.getAngle() + Math.PI/2;
//                double y;
//                double x = plane.getX() + plane.getWidth()/2;
//                if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
//                    y = plane.getY() + plane.getHeight() / 2;
//                } else {
//                    y = plane.getY() + plane.getHeight() / 3;
//                }
//                if (plane.flipped)
//                    x -= 10;
//                double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
//                double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);
//
//                Rocket rocket = new Rocket(x, y, angle, plane.flipped, vx, vy, root);
//                root.getChildren().add(rocket);
//                GameLauncherController.shootBombs(rocket);
//                plane.requestFocus();
//            }
//            if (event.getCode() == KeyCode.R){
//                if (App.getLoggedInUser().getNuclearBombNumber() == 0)
//                    return;
//                createNuclear(planeAnimation);
//
//            }
//            if (event.getCode() == KeyCode.C){
//                if (App.getLoggedInUser().getClusterBombNumber() == 0)
//                    return;
//                createCluster(planeAnimation);
//            }
//            if (event.getCode() == KeyCode.P){
//                if (!Game.getInstance().getWave().getAllObjects().isEmpty()){
//                    GameLauncherController.jumpToNextWave();
//                }
//                GameController.goToNextWave();
//            }
//            if (event.getCode() == KeyCode.G){
//                App.getLoggedInUser().addNuclearBomb();
//                GameLauncherController.updateNuclearBombCount();
//            }
//            if (event.getCode() == KeyCode.T){
//                Wave wave = Game.getInstance().getWave();
//                ComponentCreator.createTanks(wave, game, root, 1);
//                ComponentCreator.createTankCheatCode(wave.getTanks());
//            }
//            if (event.getCode() == KeyCode.CONTROL) {
//                App.getLoggedInUser().addCluster();
//                GameLauncherController.updateClusterBombCount();
//            }
//        });
//    }
//
//    public void createNuclear(PlaneAnimation planeAnimation) {
//        double angle = -plane.getAngle() + Math.PI/2;
//        double y;
//        double x = plane.getX() + plane.getWidth()/2;
//        if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
//            y = plane.getY() + plane.getHeight() / 2;
//        } else {
//            y = plane.getY() + plane.getHeight() / 3;
//        }
//        if (plane.flipped)
//            x -= 10;
//        double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
//        double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);
//        NuclearBomb nuclearBomb = new NuclearBomb(x, y, angle, plane.flipped, vx, vy, root, 30, 30);
//        plane.setDisable(false);
//        root.getChildren().add(nuclearBomb);
//        App.getLoggedInUser().reduceNuclearBomb();
//        GameLauncherController.updateNuclearBombCount();
//        GameLauncherController.shootBombs(nuclearBomb);
//        plane.requestFocus();
//    }
//    public void createCluster(PlaneAnimation planeAnimation){
//        plane.setDisable(false);
//        BombAnimation.setClusterHasExploded(false);
//        double angle = -plane.getAngle() + Math.PI/2;
//        double y;
//        double x = plane.getX() + plane.getWidth()/2;
//        if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
//            y = plane.getY() + plane.getHeight() / 2;
//        } else {
//            y = plane.getY() + plane.getHeight() / 3;
//        }
//        if (plane.flipped)
//            x -= 10;
//        double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
//        double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);
//        Cluster cluster = new Cluster(x, y, angle, plane.flipped, vx, vy, root, 20, 20);
//        root.getChildren().add(cluster);
//        GameLauncherController.shootBombs(cluster);
//        App.getLoggedInUser().reduceClusterBomb();
//        GameLauncherController.updateClusterBombCount();
//        plane.requestFocus();
//    }
//
//    @FXML
//    public void returnToGame(){
//        GameLauncherController.resumeGame();
//        if (App.isGrayScale()){
//            ColorAdjust grayscale = new ColorAdjust();
//            grayscale.setSaturation(-1);
//            ApplicationController.getStage().getScene().getRoot().setEffect(grayscale);
//        }
//        pauseMenu.setVisible(false);
//    }
//    public void pauseGame(MouseEvent mouseEvent) throws IOException {
//        GameLauncherController.pauseGame();
//        ColorAdjust grayscale = new ColorAdjust();
//        grayscale.setSaturation(0);
//        ApplicationController.getStage().getScene().getRoot().setEffect(grayscale);
//        pauseMenu.setVisible(true);
//    }
//    public static void endGame() throws Exception {
//        EndGameMenu endGameMenu = new EndGameMenu();
//        endGameMenu.start(ApplicationController.getStage());
//    }
//
//    public void exitWithSave(ActionEvent actionEvent) {
//
//    }
//
//    public void exitWithoutSave(ActionEvent actionEvent) throws Exception {
//        AppController.loginMenu.start(ApplicationController.getStage());
//    }
//
//    public void pauseMusic(ActionEvent actionEvent) {
//    }
//
//    public void keyGuide(ActionEvent actionEvent) {
//    }
//
//    public void changeMusic(ActionEvent actionEvent) {
//    }
//
//    public void back(ActionEvent actionEvent) {
//
//    }
}