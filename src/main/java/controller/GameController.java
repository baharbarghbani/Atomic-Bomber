package controller;

import javafx.event.Event;
import javafx.event.EventType;
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

    public static void createTanks(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 3; i++) {
            double x = random.nextDouble(1000);
            Tank tank = new Tank(x, random.nextInt(2), game, pane);
            wave.getTanks().add(tank);
            wave.getAllObjects().add(tank);
        }
    }

    public static void createTrucks(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 2; i++) {
            double x = random.nextDouble(1000);
            Truck truck = new Truck(random.nextInt(2), x, game, pane);
            wave.getTrucks().add(truck);
            wave.getAllObjects().add(truck);
        }
    }

    public static void createTrees(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 5; i++) {
            double x = random.nextDouble(1000);
            Tree tree = new Tree(x, random.nextInt(3), game, pane);
            wave.getAllObjects().add(tree);
            wave.getTrees().add(tree);
        }
    }

    public static void createFort(Wave wave, Game game, Pane pane) {
        for (int i = 0; i < 2; i++) {
            double x = random.nextDouble(1000);
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
    public static void checkPlaneIsInTankArea(Plane plane) {
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
            return null;
    }
    public static NuclearBomb createNuclearBomb(Component component){
        return new NuclearBomb(component.getX(), component.getY(), 0, false, 0.01, -1, GameLauncher.getInstance().root, 60, 60);
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
        GameLauncherController.updateWaveNumber();
        GameLauncherController.addComponents();
    }

}

