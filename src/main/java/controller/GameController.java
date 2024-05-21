package controller;

import animations.MissileAnimation;
import animations.PlaneAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.App;
import model.Game;
import model.User;
import model.Wave;
import model.bombs.Bullet;
import model.components.Mig;
import model.components.Plane;
import model.components.ShootingTank;
import view.AppViewController;
import view.GameLauncherController;

public class GameController {
    public static void checkPlaneInTankArea(Plane plane) {
        if (plane != null) {
            double x = plane.getX();
            double y = plane.getY();
            Wave wave = Game.getInstance().getWave();
            for (ShootingTank tank : wave.getShootingTanks()) {
                double xPrime = tank.getX();
                double yPrime = tank.getY();
                if (Math.sqrt(Math.pow(x - xPrime, 2) + Math.pow(yPrime - y, 2)) < tank.getRadius()) {
                    double angle = getAngleForTank(x, y, xPrime, yPrime, plane);
                    tank.shoot(angle);
                }
            }
        }
    }
    public static void checkPlaneInMigArea(Plane plane){
        Wave wave = Game.getInstance().getWave();
        Mig mig = wave.getMig();
        if (plane != null && mig != null){
            double x = plane.getX();
            double y = plane.getY();
            double yPrime = mig.getY();
            double xPrime = mig.getX();
            if (Math.sqrt(Math.pow(x - xPrime, 2) + Math.pow(yPrime - y, 2)) < mig.getRadius()){
                double angle = getAngleForMig(x, y, xPrime, yPrime, plane);
                mig.shoot(angle);
            }

        }
    }
    private static double getAngleForTank(double x, double y, double xPrime, double yPrime, Plane plane) {
        double dx = 3.5 * Math.cos(plane.angle) / 2;
        double dy = 3.5 * Math.sin(plane.angle) / 2;
        double angle = Math.toDegrees(Math.atan((yPrime - dy -  y) / (xPrime - dx - x)));
        if (xPrime < x)
            angle += 180 - angle;
        return angle;
    }
    private static double getAngleForMig(double x, double y, double xPrime, double yPrime, Plane plane){
        double dx = 3.5 * Math.cos(plane.angle) / 2;
        double dy = 3.5 * Math.sin(plane.angle) / 2;
        double angle = Math.toDegrees(Math.atan((yPrime - dy -  y) / (xPrime - dx - x)));
        return angle;
    }
    public static Wave createWave(Game game) {
        Wave wave = new Wave(game.getPane(), game);
        game.increaseWaveNumber();
        game.setWave(wave);
        return wave;
    }
    public static void addKill(int kill){
        App.getLoggedInUser().addKill(kill);
    }
    public static void checkComponents(){
        if (Game.getInstance().getWaveNumber() == 4){
            try {
                endGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Wave wave = Game.getInstance().getWave();
        if (wave.getAllObjects().isEmpty()){
            goToNextWave();
        }
    }
    public static void goToNextWave(){
        Game game = Game.getInstance();
        game.setWave(createWave(game));
        game.getWave().createShootingTanks();
        GameLauncherController.updateWaveNumber();
        AppViewController.gameLauncherController.addComponents();
    }
    public static void resetAccuracy(){
        App.getLoggedInUser().setShootingCount(0);
        App.getLoggedInUser().setSuccessfulShootingCount(0);
    }
    public static void increaseShootingCount(){
        User user = App.getLoggedInUser();
        user.increaseShootingCount();
    }
    public static void checkMigTime(Game game, Wave wave, Pane root){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20 * App.getMigTimeCoef()), actionEvent -> ComponentCreator.createMig(wave, game, root)));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public static void performTankShootingAnimation(ShootingTank shootingTank, Bullet bullet, int imageNumber, double angle, Game game, Pane pane) {
        if (imageNumber == 0){
            if (angle >= 180 && angle <= 270) {
                pane.getChildren().remove(shootingTank);
                shootingTank.setScaleX(-1);
                pane.getChildren().add(shootingTank);
            }
        }else {
            if (angle <= 360 && angle >= 270) {
                pane.getChildren().remove(shootingTank);
                shootingTank.setScaleX(-1);
                pane.getChildren().add(shootingTank);
            }
        }
        performMissileShootingAnimationForTank(bullet, game, pane, shootingTank);
    }
    public static void performMissileShootingAnimationForTank(Bullet bullet, Game game, Pane pane, ShootingTank shootingTank) {
        MissileAnimation missileAnimation = new MissileAnimation(game, bullet);
        shootingTank.setMissileAnimation(missileAnimation);
        game.addAnimations(missileAnimation);
        pane.getChildren().add(bullet);
        missileAnimation.play();
    }
    public static void performMissileShootingAnimationForMig(Bullet bullet, Game game, Pane pane, Mig mig){
        MissileAnimation missileAnimation = new MissileAnimation(game, bullet);
        mig.setMissileAnimation(missileAnimation);
        game.addAnimations(missileAnimation);
        pane.getChildren().add(bullet);
        missileAnimation.play();
    }

    public static void endGame() throws Exception {
        GameLauncherController.endGame();
    }

}

