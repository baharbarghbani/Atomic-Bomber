package view;

import animations.BombAnimtaion;
import animations.TankAnimation;
import animations.TruckAnimation;
import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.App;
import model.Game;
import model.Wave;
import model.components.*;

import java.util.ArrayList;
import java.util.Objects;

public class GameLauncherController {


    public static void createTanksAnimation(ArrayList<Tank> tanks, Game game) {
        TankAnimation tankAnimation;
        for (int i = 0; i < 3; i++) {
            Tank tank = tanks.get(i);
            tankAnimation = new TankAnimation(tank, game);
            tankAnimation.play();
        }
    }
    public static void createShootingTanksAnimation(ArrayList<ShootingTank> shootingTanks, Game game) {
        TankAnimation tankAnimation;
        for (int i = 0; i < 2; i++) {
            ShootingTank shootingTank = shootingTanks.get(i);
            tankAnimation = new TankAnimation(shootingTank, game);
            tankAnimation.play();
        }
    }
    public static void createTrucksAnimation(ArrayList<Truck> trucks, Game game) {
        TruckAnimation truckAnimation;
        for (int i = 0; i < 2; i++) {
            Truck truck = trucks.get(i);
            truckAnimation = new TruckAnimation(truck, game);
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

    public static void shootBombs(Bomb rocket) {
        BombAnimtaion rocketAnimation = new BombAnimtaion(rocket);
        rocketAnimation.play();
    }

    public static void updateKillCount() {
        GameLauncher.killNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getKill()));
    }
    public static void updateNuclearBombCount() {
        GameLauncher.nuclearBombNumberText.setText("X"+String.valueOf(App.getLoggedInUser().getNuclearBombNumber()));
    }
    public static void checkCollision(Bomb bomb, Transition transition){
        Wave wave = Game.getInstance().getWave();
        for (Component objects : wave.getAllObjects()){
            if (bomb.getBoundsInParent().intersects(objects.getBoundsInParent())){
                GameController.addKill(objects.getKill());
                if (objects.hasBonus() && objects instanceof Building) {
                    Bomb bomb2 = GameController.giveBonus(objects);
                    bomb2.bonusAction(objects);
                }
                if (bomb instanceof NuclearBomb){
                    objects.explodeByNuclear();

                } else if (bomb instanceof Rocket) {
                    objects.explode();
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
        GameLauncher.waveNumberText.setText("Wave "+String.valueOf(Game.getInstance().getWaveNumber()));
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
        createTanksAnimation(game.getWave().getTanks(), game);
        createTrucksAnimation(game.getWave().getTrucks(), game);
        createShootingTanksAnimation(game.getWave().getShootingTanks(), game);
    }






}
