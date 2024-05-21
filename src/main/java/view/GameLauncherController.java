package view;

import animations.BombAnimation;
import animations.PlaneAnimation;
import controller.ApplicationController;
import controller.ComponentCreator;
import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.Game;
import model.Wave;
import model.bombs.Rocket;
import model.bombs.*;
import model.components.Component;
import model.components.Plane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class GameLauncherController {
    @FXML
    public Label username;
    @FXML
    public ImageView userAvatar;
    @FXML
    public ImageView pauseButton;
    @FXML
    public Label clusterBombNumber;
    @FXML
    public ImageView clusterBomb;
    @FXML
    public ProgressBar progressBar;
    @FXML
    protected ImageView rocket;
    @FXML
    public Label  rocketNumber;
    @FXML
    public Label nuclearBombNumber;
    @FXML
    protected ImageView nuclearBomb;
    @FXML
    public Label killNumber;
    @FXML
    public ImageView killNumberImage;
    @FXML
    public Label waveNumber;
    private  Game game;
    public static Label killNumberText;
    public static Label rocketNumberText;
    public static Label nuclearBombNumberText;
    public static Label waveNumberText;
    public static Label clusterBombNumberText;
    public static ImageView pauseButtonImage;
    public static ProgressBar staticProgressBar;
    private Plane plane;
    public Pane root;
    public Pane pauseMenu;
    public Pane endGameMenu;


    public static void pauseGame() {
        for (Transition animation : Game.getInstance().getAllAnimations()) {
            animation.pause();
        }

    }

    public static void resumeGame() {
        for (Transition animation : Game.getInstance().getAllAnimations()) {
            animation.play();
        }

    }

    public static void updateClusterBombCount() {
        clusterBombNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getClusterBombNumber()));
    }

    public Image getRocket() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/rockets/rocket4.png")));
    }

    public Image getNuclearBomb() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/atomic-bomb.png")));
    }
    public Image getKillNumber(){
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/death.png")));
    }

    public static void shootBombs(Bomb rocket) {
        BombAnimation rocketAnimation = new BombAnimation(rocket);
        rocketAnimation.play();
    }

    public static void updateKillCount() {
        killNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getKill()));
    }
    public static void updateNuclearBombCount() {
        nuclearBombNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getNuclearBombNumber()));
    }
    public static void checkCollision(Bomb bomb, Transition transition){
        Wave wave = Game.getInstance().getWave();
        for (Component objects : wave.getAllObjects()){
            if (bomb.getBoundsInParent().intersects(objects.getBoundsInParent())){
                GameController.addKill(objects.getKill());
                App.getLoggedInUser().increaseSuccessfulShootingCount();
                increaseProgressBar();
                if (objects.hasBonus()) {
                    Bomb bomb2 = ComponentCreator.giveBonus(objects);
                    bomb2.bonusAction(objects);
                }
                if (bomb instanceof NuclearBomb){
                    objects.explodeByNuclear();

                } else if (bomb instanceof Rocket) {
                    objects.explode();
                }
                if (bomb instanceof Cluster){
                    objects.explodeByCluster();
                }
                objects.remove();
                Game.getInstance().removeAnimation(transition);
                App.getLoggedInUser().addKill(objects.getKill());
                GameLauncherController.updateKillCount();
                GameController.checkComponents();
                break;
            }
        }
    }
    public static boolean hasCollision(Bomb bomb){
        Wave wave = Game.getInstance().getWave();
        for (Component object: wave.getAllObjects()){
            if (bomb.getBoundsInParent().intersects(object.getBoundsInParent())){
                return true;
            }
        }
        return false;
    }
    public static void updateWaveNumber() {
        if (Game.getInstance().getWaveNumber() < 4) {
            waveNumberText.setText("Wave " + String.valueOf(Game.getInstance().getWaveNumber()));
            AppViewController.showAlert("Accuracy: " + App.getLoggedInUser().getAccuracy() + "%", "Wave " + String.valueOf(Game.getInstance().getWaveNumber()) + " started", Alert.AlertType.INFORMATION, "/Images/backgrounds/background7.png", false);
            GameController.resetAccuracy();
        }
    }
    public void addComponents(){
        Game game = Game.getInstance();
        root.getChildren().addAll(game.getWave().getBuildings());
        root.getChildren().addAll(game.getWave().getForts());
        root.getChildren().addAll(game.getWave().getTanks());
        root.getChildren().addAll(game.getWave().getTrucks());
        root.getChildren().addAll(game.getWave().getTrees());
        if (game.getWaveNumber() == 2 || game.getWaveNumber() == 3){
            root.getChildren().addAll(game.getWave().getShootingTanks());
            ComponentCreator.createShootingTanksAnimation(game.getWave().getShootingTanks(), game);
        }
        ComponentCreator.createTanksAnimation(game.getWave().getTanks(), game,3);
        ComponentCreator.createTrucksAnimation(game.getWave().getTrucks(), game);
    }
    public static void jumpToNextWave(){
        Pane pane = GameLauncher.getInstance().root;
        pane.getChildren().removeAll(Game.getInstance().getWave().getAllObjects());
    }
    public void checkGrayScale(Stage stage){
        if (App.isGrayScale()){
            ColorAdjust grayscale = new ColorAdjust();
            grayscale.setSaturation(-1);
            stage.getScene().getRoot().setEffect(grayscale);
        }
    }
    public void createGameLauncher(Scene scene) throws IOException {
        AppViewController.gameLauncherController.setRoot(root);
        AppViewController.gameLauncherController.createGame();
        AppViewController.gameLauncherController.creatPlane(root, 0, 90);
        Game game = Game.getInstance();
        plane = game.getPlane();
        game.setPlane(plane);
        Wave wave = GameController.createWave(game);
        GameController.checkMigTime(game, game.getWave(), root);
        scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
        plane.requestFocus();
        AppViewController.gameLauncherController.addComponents();
        ComponentCreator.createTanksAnimation(wave.getTanks(), game, 3);
        ComponentCreator.createTrucksAnimation(wave.getTrucks(), game);
    }
    @FXML
    public void initialize() throws FileNotFoundException {
        AppViewController.appController.setIcon();
        rocket.setImage(AppViewController.gameLauncherController.getRocket());
        rocketNumber.setText("X" + App.getLoggedInUser().getRocketNumber());
        nuclearBomb.setImage(AppViewController.gameLauncherController.getNuclearBomb());
        nuclearBombNumber.setText("X" + App.getLoggedInUser().getNuclearBombNumber());
        killNumberImage.setImage(AppViewController.gameLauncherController.getKillNumber());
        killNumber.setText("X" + App.getLoggedInUser().getKill());
        killNumberText = killNumber;
        nuclearBombNumberText = nuclearBombNumber;
        rocketNumberText = rocketNumber;
        username.setText(App.getLoggedInUser().getUsername());
        userAvatar.setImage(AppViewController.appController.imageInitialize());
        waveNumber.setText("Wave 1");
        waveNumberText = waveNumber;
        pauseButton.setImage(new Image(requireNonNull(getClass().getResourceAsStream("/Images/pause.png"))));
        pauseButtonImage = pauseButton;
        clusterBomb.setImage(new Image(requireNonNull(getClass().getResourceAsStream("/Images/bombs/malware.png"))));
        clusterBombNumber.setText("X" + App.getLoggedInUser().getClusterBombNumber());
        clusterBombNumberText = clusterBombNumber;
        pauseMenu.setVisible(false);
        staticProgressBar = progressBar;
    }
    public void createGame() {
        game = new Game(root);
        Game.setInstance(game);
    }

    public void creatPlane(Pane pane, double xPrime, double yPrime) {
        plane = new Plane(game, pane, xPrime, yPrime);
        game.setPlane(plane);
        pane.getChildren().add(plane);
        plane.setFocusTraversable(true);
        PlaneAnimation planeAnimation = new PlaneAnimation(game, pane, plane);
        planeAnimation.play();
        plane.setPlaneAnimation(planeAnimation);
        plane.requestFocus();
        plane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                planeAnimation.setUp(true);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.DOWN) {
                planeAnimation.setDown(true);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.LEFT) {
                planeAnimation.setLeft(true);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                planeAnimation.setRight(true);
                plane.setDisable(false);
            }
        });
        plane.setOnKeyReleased(event -> {
            plane.setFocusTraversable(true);
            plane.requestFocus();
            if (event.getCode() == KeyCode.RIGHT) {
                planeAnimation.setRight(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.LEFT) {
                planeAnimation.setLeft(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.UP) {
                planeAnimation.setUp(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.DOWN) {
                planeAnimation.setDown(false);
                plane.setDisable(false);
            }
            if (event.getCode() == KeyCode.SPACE) {
                GameController.increaseShootingCount();
                plane.setDisable(false);
                double angle = -plane.getAngle() + Math.PI/2;
                double y;
                double x = plane.getX() + plane.getWidth()/2;
                if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
                    y = plane.getY() + plane.getHeight() / 2;
                } else {
                    y = plane.getY() + plane.getHeight() / 3;
                }
                if (plane.flipped)
                    x -= 10;
                double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
                double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);

                Rocket rocket = new Rocket(x, y, angle, plane.flipped, vx, vy, root);
                root.getChildren().add(rocket);
                GameLauncherController.shootBombs(rocket);
                plane.requestFocus();
            }
            if (event.getCode() == KeyCode.R){
                if (App.getLoggedInUser().getNuclearBombNumber() == 0)
                    return;
                createNuclear(planeAnimation);

            }
            if (event.getCode() == KeyCode.C){
                if (App.getLoggedInUser().getClusterBombNumber() == 0)
                    return;
                createCluster(planeAnimation);
            }
            if (event.getCode() == KeyCode.P){
                if (!Game.getInstance().getWave().getAllObjects().isEmpty()){
                    GameLauncherController.jumpToNextWave();
                }
                if (Game.getInstance().getWaveNumber() == 4){
                    try {
                        GameLauncherController.endGame();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                GameController.goToNextWave();
            }
            if (event.getCode() == KeyCode.G){
                App.getLoggedInUser().addNuclearBomb();
                GameLauncherController.updateNuclearBombCount();
            }
            if (event.getCode() == KeyCode.T){
                Wave wave = Game.getInstance().getWave();
                ComponentCreator.createTanks(wave, game, root, 1);
                ComponentCreator.createTankCheatCode(wave.getTanks());
            }
            if (event.getCode() == KeyCode.CONTROL) {
                App.getLoggedInUser().addCluster();
                GameLauncherController.updateClusterBombCount();
            }
            if (event.getCode() == KeyCode.H){
                plane.increaseHitPoint(1);
            }
            if (event.getCode() == KeyCode.TAB){
                if (staticProgressBar.getProgress() == 1){
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), actionEvent -> freeze()));
                    timeline.setCycleCount(-1);
                    timeline.play();
                }else AppViewController.showAlert("You can't freeze game", "Freeze Failed!", Alert.AlertType.ERROR, "/Images/backgrounds/background7.png", false);
            }
        });
    }

    public void createNuclear(PlaneAnimation planeAnimation) {
        double angle = -plane.getAngle() + Math.PI/2;
        double y;
        double x = plane.getX() + plane.getWidth()/2;
        if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
            y = plane.getY() + plane.getHeight() / 2;
        } else {
            y = plane.getY() + plane.getHeight() / 3;
        }
        if (plane.flipped)
            x -= 10;
        double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
        double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);
        NuclearBomb nuclearBomb = new NuclearBomb(x, y, angle, plane.flipped, vx, vy, root, 30, 30);
        plane.setDisable(false);
        root.getChildren().add(nuclearBomb);
        App.getLoggedInUser().reduceNuclearBomb();
        GameLauncherController.updateNuclearBombCount();
        GameLauncherController.shootBombs(nuclearBomb);
        plane.requestFocus();
    }
    public void createCluster(PlaneAnimation planeAnimation){
        plane.setDisable(false);
        BombAnimation.setClusterHasExploded(false);
        double angle = -plane.getAngle() + Math.PI/2;
        double y;
        double x = plane.getX() + plane.getWidth()/2;
        if (plane.getAngle() > Math.PI && plane.getAngle() < Math.PI * 2) {
            y = plane.getY() + plane.getHeight() / 2;
        } else {
            y = plane.getY() + plane.getHeight() / 3;
        }
        if (plane.flipped)
            x -= 10;
        double vy = -planeAnimation.getSpeed() * Math.sin(plane.angle);
        double vx = planeAnimation.getSpeed() * Math.cos(plane.angle);
        Cluster cluster = new Cluster(x, y, angle, plane.flipped, vx, vy, root, 20, 20);
        root.getChildren().add(cluster);
        GameLauncherController.shootBombs(cluster);
        App.getLoggedInUser().reduceClusterBomb();
        GameLauncherController.updateClusterBombCount();
        plane.requestFocus();
    }
    public static void increaseProgressBar(){
        staticProgressBar.setProgress(staticProgressBar.getProgress() + 0.2);
        if (staticProgressBar.getProgress() == 1){
            staticProgressBar.setProgress(1);
        }
    }
    public static void resetProgressBar(){
        staticProgressBar.setProgress(0);
    }

    @FXML
    public void returnToGame(){
        GameLauncherController.resumeGame();
        if (App.isGrayScale()){
            ColorAdjust grayscale = new ColorAdjust();
            grayscale.setSaturation(-1);
            ApplicationController.getStage().getScene().getRoot().setEffect(grayscale);
        }
        pauseMenu.setVisible(false);
    }
    public void pauseGame(MouseEvent mouseEvent) throws IOException {
        GameLauncherController.pauseGame();
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(0);
        ApplicationController.getStage().getScene().getRoot().setEffect(grayscale);
        pauseMenu.setVisible(true);
    }
    public static void endGame() throws Exception {
        EndGameMenu endGameMenu = new EndGameMenu();
        endGameMenu.start(ApplicationController.getStage());
    }
    public void setRoot(Pane root){
        this.root = root;
    }

    public void exitWithSave(ActionEvent actionEvent) {

    }

    public void exitWithoutSave(ActionEvent actionEvent) throws Exception {
        AppViewController.loginMenu.start(ApplicationController.getStage());
    }
    @FXML
    public void pauseMusic() {
        AppViewController.pauseMusic();
    }
    @FXML
    public void keyGuide(ActionEvent actionEvent) {
    }
    @FXML
    public void changeMusic(ActionEvent actionEvent) {

    }
    public static void freeze() {
        int animationCount = Game.getInstance().getAllAnimations().size();
        for (int i = 0; i < animationCount; i++) {
            Transition animation = Game.getInstance().getAllAnimations().get(i);
            if (animation instanceof PlaneAnimation)
                continue;
            animation.pause();
        }
        resetProgressBar();

    }







}
