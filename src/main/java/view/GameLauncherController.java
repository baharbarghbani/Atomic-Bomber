package view;

import animations.BombAnimtaion;
import animations.TankAnimation;
import animations.TruckAnimation;
import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.App;
import model.Game;
import model.Wave;
import model.components.*;

import java.util.ArrayList;
import java.util.Objects;

public class GameLauncherController {



    public static void createTanksAnimation(ArrayList<Tank> tanks, Game game, int number) {
        TankAnimation tankAnimation;
        for (int i = 0; i < number; i++) {
            Tank tank = tanks.get(i);
            tankAnimation = new TankAnimation(tank, game);
            game.addAnimations(tankAnimation);
            tankAnimation.play();
        }
    }
    public static void createTankCheatCode(ArrayList<Tank> tanks){
        for (Tank tank : tanks){
            Pane root = GameLauncher.getInstance().root;
            if (!root.getChildren().contains(tank)){
                TankAnimation tankAnimation = new TankAnimation(tank, Game.getInstance());
                Game.getInstance().addAnimations(tankAnimation);
                root.getChildren().add(tank);
                tankAnimation.play();
            }
        }
    }
    public static void createShootingTanksAnimation(ArrayList<ShootingTank> shootingTanks, Game game) {
        TankAnimation shootingTankAnimation;
        for (int i = 0; i < 2; i++) {
            ShootingTank shootingTank = shootingTanks.get(i);
            shootingTankAnimation = new TankAnimation(shootingTank, game);
            game.addAnimations(shootingTankAnimation);
            shootingTankAnimation.play();
        }
    }
    public static void createTrucksAnimation(ArrayList<Truck> trucks, Game game) {
        TruckAnimation truckAnimation;
        for (int i = 0; i < 2; i++) {
            Truck truck = trucks.get(i);
            truckAnimation = new TruckAnimation(truck, game);
            game.addAnimations(truckAnimation);
            truckAnimation.play();
        }
    }

    public static void pauseGame() {
        for (Transition animation : Game.getInstance().getAllAnimations()) {
            animation.pause();
        }

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

    public static void shootBombs(Bomb bomb) {
        BombAnimtaion rocketAnimation = new BombAnimtaion(bomb);
        rocketAnimation.play();
    }

    public static void updateKillCount() {
        GameLauncher.killNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getKill()));
    }
    public static void updateNuclearBombCount() {
        GameLauncher.nuclearBombNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getNuclearBombNumber()));
    }
    public static void updateClusterBombCount() {
        GameLauncher.clusterBombNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getClusterBombNumber()));
    }
    public static void checkCollision(Bomb bomb, Transition transition){
        Wave wave = Game.getInstance().getWave();
        for (Component objects : wave.getAllObjects()){
            if (bomb.getBoundsInParent().intersects(objects.getBoundsInParent())){
                GameController.addKill(objects.getKill());
                if (objects.hasBonus()) {
                    Bomb bomb2 = GameController.giveBonus(objects);
                    bomb2.bonusAction(objects);
                }

                if (bomb instanceof NuclearBomb){
                    objects.explodeByNuclear();
                } else if (bomb instanceof Rocket) {
                    objects.explode();
                } else if (bomb instanceof Cluster) {
                    objects.explodeByCluster();
                }
                objects.remove();
                Game.getInstance().removeAnimation(transition);
                transition.stop();
//                Game.getInstance().getWave().removeObject(objects);
                App.getLoggedInUser().addKill(objects.getKill());
                App.getLoggedInUser().increaseSuccessfulShootingCount();
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
        GameLauncher.waveNumberText.setText("Wave "+String.valueOf(Game.getInstance().getWaveNumber()));
        AppController.showAlert("accuracy: " + App.getLoggedInUser().getAccuracy() + "%", "Wave "+String.valueOf(Game.getInstance().getWaveNumber())+" started", Alert.AlertType.INFORMATION, "/Images/backgrounds/background7.png");
        GameController.resetAccuracy();
    }
    public static void addComponents(){
        Game game = Game.getInstance();
        Pane root = GameLauncher.getInstance().root;
        root.getChildren().addAll(game.getWave().getBuildings());
        root.getChildren().addAll(game.getWave().getForts());
        root.getChildren().addAll(game.getWave().getTanks());
        root.getChildren().addAll(game.getWave().getTrucks());
        root.getChildren().addAll(game.getWave().getTrees());
        root.getChildren().addAll(game.getWave().getShootingTanks());
        if (game.getWaveNumber() == 3){
            root.getChildren().add(game.getWave().getMig());
        }
        createTanksAnimation(game.getWave().getTanks(), game,3);
        createTrucksAnimation(game.getWave().getTrucks(), game);
        createShootingTanksAnimation(game.getWave().getShootingTanks(), game);
    }
    public static void jumpToNextWave(){
        Pane pane = GameLauncher.getInstance().root;
        pane.getChildren().removeAll(Game.getInstance().getWave().getAllObjects());
    }







}
