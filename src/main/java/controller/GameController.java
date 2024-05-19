package controller;

import javafx.scene.layout.Pane;
import model.App;
import model.Game;
import model.User;
import model.Wave;
import model.components.*;
import view.GameLauncher;
import view.GameLauncherController;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    static Random random = new Random();

    public static void createBuildings(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 3; i++) {
            double x = random.nextDouble(1000);
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

    //    public void shootBomb(Plane plane) {
//        Rocket bomb = new Rocket(plane.getX(), plane.getY());
//        Game.getInstance().getWave().getBombs().add(bomb);
//        Game.getInstance().getWave().getAllObjects().add(bomb);
//    }
    public static void isPlaneInTankArea(Plane plane) {
        double x = plane.getX();
        double y = plane.getY();
        Wave wave = Game.getInstance().getWave();
        for (ShootingTank tank : wave.getShootingTanks()) {
            double xPrime = tank.getX();
            double yPrime = tank.getY();
            if (Math.sqrt(Math.pow(x - xPrime, 2) + Math.pow(yPrime - y, 2)) < tank.getRadius()) {
                double dx = 3.3 * Math.cos(plane.angle);
                double dy = 3.3 * Math.sin(plane.angle);
                double angle = Math.toDegrees(Math.atan((yPrime - dy - y) / (xPrime - dx - x)));
                if (xPrime < x)
                    angle += 180 - angle;
                tank.shoot(angle);
            }
        }
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
        if (game.getWaveNumber() == 3){
            game.getWave().createMig();
        }
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
    public static void increaseKillingCount(){
        User user = App.getLoggedInUser();
        user.increaseSuccessfulShootingCount();
    }
    public static void checkCluster(Bomb bomb){
        if (bomb instanceof Cluster cluster && bomb.getY() > 400){
            cluster.divide();
        }
    }

    public static void createMig(Wave wave, Game game, Pane pane) {
        Mig mig = new Mig(1050, 120);
        wave.setMig(mig);

    }
}

