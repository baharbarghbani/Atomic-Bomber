package view;

import animations.BombAnimation;
import animations.FreezingAnimation;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
import java.sql.Time;
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
    public Label migWarning;
    @FXML
    public Label waveOver;
    @FXML
    public ImageView heart1;
    @FXML
    public ImageView heart2;
    @FXML
    public ImageView heart3;
    @FXML
    public ChoiceBox<String> choiceBox;
    @FXML
    public Text guideMenuText;
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
    public static Label migWarningText;
    public static Label waveOverText;
    public static ImageView heart1Image;
    public static ImageView heart2Image;
    public static ImageView heart3Image;
    private Plane plane;
    public Pane root;
    @FXML
    public Pane pauseMenu;
    @FXML
    public Pane guide;

    @FXML
    public void initialize() throws FileNotFoundException {
        AppViewController.setIcon();
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
        guide.setVisible(false);
        staticProgressBar = progressBar;
        migWarningText = migWarning;
        migWarningText.setVisible(false);
        waveOverText = waveOver;
        waveOverText.setVisible(false);
        heart1 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/heart.png"))));
        heart2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/heart.png"))));
        heart3 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/heart.png"))));
        heart1Image = heart1;
        heart2Image = heart2;
        heart3Image = heart3;
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleEvent(newValue);
        });
        guideMenuText.setText("Press UP, DOWN, LEFT, RIGHT to move the plane\nPress SPACE to shoot\nPress R to shoot nuclear bomb\nPress C to shoot cluster bomb\nPress P to go to next wave\nPress G to get nuclear bomb\nPress CONTROL to get cluster bomb\nPress H to increase HP\nPress TAB to freeze the game");
        guideMenuText.setFill(Color.WHITE);
    }

    public void addLives(){
        heart1Image.setFitWidth(50);
        heart2Image.setFitWidth(50);
        heart3Image.setFitWidth(50);
        heart1Image.setFitHeight(50);
        heart2Image.setFitHeight(50);
        heart3Image.setFitHeight(50);
        heart1Image.setX(22);
        heart1Image.setY(125);
        heart2Image.setX(75);
        heart2Image.setY(125);
        heart3Image.setX(128);
        heart3Image.setY(125);
        root.getChildren().add(heart1Image);
        root.getChildren().add(heart2Image);
        root.getChildren().add(heart3Image);
    }
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
            waveOverText.setText("Wave " + String.valueOf(Game.getInstance().getWaveNumber()) + "\nAccuracy: " + App.getLoggedInUser().getAccuracy() + "%");
            waveOverText.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), actionEvent -> waveOverText.setVisible(false)));
            timeline.setCycleCount(-1);
            timeline.play();
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
//        plane.requestFocus();
        AppViewController.gameLauncherController.addComponents();
        ComponentCreator.createTanksAnimation(wave.getTanks(), game, 3);
        ComponentCreator.createTrucksAnimation(wave.getTrucks(), game);
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
//        plane.requestFocus();
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
//            plane.requestFocus();
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
//                plane.requestFocus();
            }
            if (event.getCode() == KeyCode.R){
                GameController.increaseShootingCount();
                if (App.getLoggedInUser().getNuclearBombNumber() == 0)
                    return;
                createNuclear(planeAnimation);

            }
            if (event.getCode() == KeyCode.C){
                GameController.increaseShootingCount();
                if (App.getLoggedInUser().getClusterBombNumber() == 0)
                    return;
                createCluster(planeAnimation);
            }
            if (event.getCode() == KeyCode.P){
                if (!Game.getInstance().getWave().getAllObjects().isEmpty()){
                    GameLauncherController.jumpToNextWave();
                }
                if (Game.getInstance().getWaveNumber() == 3){
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
                GameController.increaseHP();
                plane.increaseHitPoint(1);
            }
            if (event.getCode() == KeyCode.TAB){
                App.setFreezed(true);
                if (staticProgressBar.getProgress() > 0.97){
                    freeze();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), actionEvent -> melt()));
                    timeline.setCycleCount(1);
                    timeline.play();
                }
            }
        });
    }

    private void melt() {
        int count = Game.getInstance().getAllAnimations().size();
        for (int i = 0; i < count; i++) {
            Transition animation = Game.getInstance().getAllAnimations().get(i);
            if (animation instanceof PlaneAnimation)
                continue;
            animation.play();
        }
        App.setFreezed(false);
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
//        plane.requestFocus();
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
//        plane.requestFocus();
    }
    public static void increaseProgressBar(){
        staticProgressBar.setProgress(staticProgressBar.getProgress() + 0.2);
        if (staticProgressBar.getProgress() > 0.97){
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
        App.setPaused(false);
        pauseMenu.setVisible(false);
    }
    public void pauseGame(MouseEvent mouseEvent) throws IOException {
        GameLauncherController.pauseGame();
        App.setPaused(true);
        if (App.isGrayScale()){
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(0);
        ApplicationController.getStage().getScene().getRoot().setEffect(grayscale);
        }
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
        guide.setVisible(true);
        pauseMenu.setVisible(false);
    }

    private void handleEvent(String newValue) {
        if(newValue.equals("music1"))
        AppViewController.playMusic("src/main/media/music1.mp3");
        else if(newValue.equals("music2"))
            AppViewController.playMusic("src/main/media/music2.mp3");
        else if(newValue.equals("music3"))
            AppViewController.playMusic("src/main/media/music3.mp3");
        else if(newValue.equals("music4"))
            AppViewController.playMusic("src/main/media/music4.mp3");
        else if(newValue.equals("music5"))
            AppViewController.playMusic("src/main/media/Billie_Eilish_-_CHIHIRO_@BaseNaija.mp3");

    }

    public static void freeze() {
        App.setFreezed(true);
        ImageView imageView = new ImageView(GameLauncherController.class.getResource("/Images/frizz.png").toString());
        imageView.setFitWidth(1000);
        imageView.setFitHeight(1300);
        imageView.setOpacity(0.4);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        colorAdjust.setContrast(0.5);
        ApplicationController.getStage().getScene().getRoot().setEffect(colorAdjust);
        new FreezingAnimation(imageView).play();
        AppViewController.gameLauncherController.root.getChildren().add(imageView);
        resetProgressBar();
    }
    public void unmute(ActionEvent actionEvent) {
        App.setMuted(false);
        AppViewController.playMusic(AppViewController.musicPath);
    }

    public void backToPauseMenu(ActionEvent actionEvent) {
        guide.setVisible(false);
        pauseMenu.setVisible(true);

    }
}
