package controller;

import animations.MissileAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.App;
import model.Game;
import model.User;
import model.Wave;
import model.components.*;
import view.AppController;
import view.GameLauncher;
import view.GameLauncherController;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    static Random random = new Random();

    public static void createBuildings(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 3; i++) {
            double x = random.nextDouble(900);
            Building building = new Building(x, game, pane);
            wave.getBuildings().add(building);
            wave.getAllObjects().add(building);
        }
    }

    public static void createTanks(Wave wave, Game game, Pane pane, int number) {
        for (int i = 0; i < number; i++) {
            double x = random.nextDouble(900);
            Tank tank = new Tank(x, random.nextInt(2), game, pane);
            wave.getTanks().add(tank);
            wave.getAllObjects().add(tank);
        }
    }

    public static void createTrucks(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 2; i++) {
            double x = random.nextDouble(900);
            Truck truck = new Truck(random.nextInt(2), x, game, pane);
            wave.getTrucks().add(truck);
            wave.getAllObjects().add(truck);
        }
    }

    public static void createTrees(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 5; i++) {
            double x = random.nextDouble(900);
            Tree tree = new Tree(x, random.nextInt(3), game, pane);
            wave.getAllObjects().add(tree);
            wave.getTrees().add(tree);
        }
    }
    public static void createShootingTanks(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 2; i++) {
            double x = random.nextDouble(900);
            ShootingTank shootingTank = new ShootingTank(x, random.nextInt(2), game, pane);
            wave.getAllObjects().add(shootingTank);
            wave.getShootingTanks().add(shootingTank);
        }
    }
    public static void createFort(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 2; i++) {
            double x = random.nextDouble(900);
            Fort fort = new Fort(x, game, pane);
            wave.getAllObjects().add(fort);
            wave.getForts().add(fort);
        }
        resolveCollisions(wave);

    }

    public static void resolveCollisions(Wave wave) {
        ArrayList<Component> objects = wave.getAllObjects();
        for (int i = 0; i < objects.size(); i++) {
            Component obj1 = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++) {
                Component obj2 = objects.get(j);
                // Check for collision along the x-axis
                if (obj1.getX() < obj2.getX() + obj2.getWidth() && obj1.getX() + obj1.getWidth() > obj2.getX()) {
                    // Collision detected, resolve it
                    double overlap = (obj1.getX() + obj1.getWidth()) - obj2.getX();
                    // Move obj1 to the left by overlap amount to resolve the collision
                    if ((obj1.getX() - overlap) < 10 || (obj1.getX() - overlap) + obj1.getWidth() > 1000) {
                        obj1.setX(obj1.getX() + overlap);
                    } else {
                        obj1.setX(obj1.getX() - overlap);
                    }
                }
            }
        }
    }

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
        System.out.println(angle);
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

    public static Bomb giveBonus(Component component){
        if (component instanceof Building){
            return createNuclearBomb(component);
        }else
            return createClusterBomb(component);
    }

    private static Bomb createClusterBomb(Component component) {
        return new Cluster(component.getX(), component.getY(), 0.01, false, 0.1, -1, GameLauncher.getInstance().root, 60, 60);
    }

    public static NuclearBomb createNuclearBomb(Component component){
        return new NuclearBomb(component.getX(), component.getY(), 0.01, false, 0.1, -1, GameLauncher.getInstance().root, 60, 60);
    }
    public static void checkComponents(){
        Wave wave = Game.getInstance().getWave();
        if (wave.getAllObjects().isEmpty()){
            goToNextWave();
        }
    }
    public static void goToNextWave(){
        Game game = Game.getInstance();
        game.setWave(createWave(game));
        game.getWave().createShootingTanks();
//        if (game.getWaveNumber() == 3){
//            createMig(game.getWave(), game, game.getPane());
//        }
        GameLauncherController.updateWaveNumber();
        GameLauncherController.addComponents();
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
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), actionEvent -> GameController.createMig(wave, game, root)));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public static void createMig(Wave wave, Game game, Pane pane) {
        if (game.getWaveNumber() == 3) {
            AppController.showAlert("Mig is coming", "Mig is coming", Alert.AlertType.INFORMATION, "/Images/backgrounds/background7.png", false);
            if (game.getPlane() != null){
                game.getPlane().requestFocus();
            }
            Mig mig = new Mig(1050, 120, game, pane);
            wave.setMig(mig);
            Timeline shootTimeline = new Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0.5), actionEvent -> GameController.checkPlaneInMigArea(game.getPlane())));
            shootTimeline.setCycleCount(-1);
            shootTimeline.play();
            if (game.getPlane() != null)
                game.getPlane().requestFocus();
            pane.getChildren().add(mig);

        }
    }
    public static Bullet createBullet(double x, double y,double angle) {
        Bullet bullet = new Bullet(x+ 20, y, Math.toRadians(angle), 6 * Math.cos(angle), Math.abs(6 * Math.sin(angle)));
        return bullet;
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
}

