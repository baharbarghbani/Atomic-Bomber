package controller;

import animations.TankAnimation;
import animations.TruckAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.App;
import model.Game;
import model.Wave;
import model.bombs.*;
import model.components.*;
import view.GameLauncher;
import view.GameLauncherController;

import java.util.ArrayList;
import java.util.Random;

public class ComponentCreator {
    static Random random = new Random();

    public static void createBuildings(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 3; i++) {
            double x = random.nextDouble(900);
            Building building = new Building(x, game, pane);
            wave.getBuildings().add(building);
            wave.getAllObjects().add(building);
        }
        resolveCollisions(wave);
    }

    public static void createTanks(Wave wave, Game game, Pane pane, int number) {
        for (int i = 0; i < number; i++) {
            double x = random.nextDouble(900);
            Tank tank = new Tank(x, random.nextInt(2), game, pane);
            wave.getTanks().add(tank);
            wave.getAllObjects().add(tank);
        }
        resolveCollisions(wave);
    }

    public static void createTrucks(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 2; i++) {
            double x = random.nextDouble(900);
            Truck truck = new Truck(random.nextInt(2), x, game, pane);
            wave.getTrucks().add(truck);
            wave.getAllObjects().add(truck);
        }
        resolveCollisions(wave);
    }

    public static void createTrees(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 5; i++) {
            double x = random.nextDouble(900);
            Tree tree = new Tree(x, random.nextInt(3), game, pane);
            wave.getAllObjects().add(tree);
            wave.getTrees().add(tree);
        }
        resolveCollisions(wave);
    }
    public static void createShootingTanks(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 2; i++) {
            double x = random.nextDouble(900);
            ShootingTank shootingTank = new ShootingTank(x, random.nextInt(2), game, pane);
            wave.getAllObjects().add(shootingTank);
            wave.getShootingTanks().add(shootingTank);
        }
        resolveCollisions(wave);
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
    public static void createTanksAnimation(ArrayList<Tank> tanks, Game game, int number) {
        TankAnimation vehicleAnimation;
        for (int i = 0; i < number; i++) {
            Tank tank = tanks.get(i);
            vehicleAnimation = new TankAnimation(tank, game);
            game.addAnimations(vehicleAnimation);
            vehicleAnimation.play();
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
    private static void resolveCollisions(Wave wave) {
        ArrayList<Component> objects = wave.getAllObjects();
        for (int i = 0; i < objects.size(); i++) {
            Component obj1 = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++) {
                Component obj2 = objects.get(j);
                if (obj1.getX() < obj2.getX() + obj2.getWidth() && obj1.getX() + obj1.getWidth() > obj2.getX()) {
                    double overlap = (obj1.getX() + obj1.getWidth()) - obj2.getX();
                    if ((obj1.getX() - overlap) < 10 || (obj1.getX() - overlap) + obj1.getWidth() > 1000) {
                        obj1.setX(obj1.getX() + overlap);
                    } else {
                        obj1.setX(obj1.getX() - overlap);
                    }
                }
            }
        }
    }
    public static void createMig(Wave wave, Game game, Pane pane) {
        if (game.getWaveNumber() == 3 && !App.isPaused()) {
            GameLauncherController.migWarningText.setVisible(true);
            Timeline migWarningTimeLine  = new Timeline(new KeyFrame(Duration.seconds(3), actionEvent -> GameLauncherController.migWarningText.setVisible(false)));
            migWarningTimeLine.setCycleCount(-1);
            migWarningTimeLine.play();
            if (game.getPlane() != null){
                game.getPlane().requestFocus();
            }
            Mig mig = new Mig(1050, 120, game, pane);
            wave.setMig(mig);
            Timeline shootTimeline = new Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0.25), actionEvent -> GameController.checkPlaneInMigArea(game.getPlane())));
            shootTimeline.setCycleCount(-1);
            shootTimeline.play();
            if (game.getPlane() != null)
                game.getPlane().requestFocus();
            pane.getChildren().add(mig);
        }
    }
    public static Bullet createBulletForTank(double x, double y, double angle) {
        return new Bullet(x+ 20, y, Math.toRadians(angle), 6 * Math.cos(angle),Math.abs(6 * Math.sin(angle)));
    }
    public static Bullet createBulletForMig(double x, double y,double angle){
        return new Bullet(x, y, Math.toRadians(angle), 6 * Math.cos(angle), 6 * Math.sin(angle));
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


}
