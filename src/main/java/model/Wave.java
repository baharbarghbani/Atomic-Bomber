package model;

import controller.ComponentCreator;
import javafx.scene.layout.Pane;
import model.components.*;

import java.util.ArrayList;
import java.util.Random;

public class Wave {
    private static Mig mig;
    private final Pane pane;
    private final ArrayList<Component> allObjects;
    private final ArrayList<Tank> tanks;
    private final ArrayList<Truck> trucks;
    private final ArrayList<Building> buildings;
    private final ArrayList<Fort> forts;
    private final ArrayList<ShootingTank> shootingTanks;
    private final Game game;
    private final ArrayList<Tree> trees;

    public Wave(Pane pane, Game game) {
        tanks = new ArrayList<>();
        trucks = new ArrayList<>();
        buildings = new ArrayList<>();
        trees = new ArrayList<>();
        forts = new ArrayList<>();
        shootingTanks = new ArrayList<>();
        allObjects = new ArrayList<>();
        this.pane = pane;
        this.game = game;
        createBuildings();
        createTrucks();
        createTanks();
        createTrees();
        createFort();
    }

    public static void removeMig() {
        mig = null;
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public void createTrucks() {
        ComponentCreator.createTrucks(this, game, pane);
    }

    public void createTanks() {
        ComponentCreator.createTanks(this, game, pane, 3);
    }

    public void createBuildings() {
        ComponentCreator.createBuildings(this, game, pane);
    }

    public void createTrees() {
        ComponentCreator.createTrees(this, game, pane);
    }

    public void createFort() {
        ComponentCreator.createFort(this, game, pane);
    }

    public void createShootingTanks() {
        ComponentCreator.createShootingTanks(this, game, pane);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<ShootingTank> getShootingTanks() {
        return shootingTanks;
    }

    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Fort> getForts() {
        return forts;
    }

    public ArrayList<Component> getAllObjects() {
        return allObjects;
    }

    public void removeObject(Component object) {
        allObjects.remove(object);
    }

    public void removeShootingTank(ShootingTank shootingTank) {
        shootingTanks.remove(shootingTank);
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    public void removeFort(Fort fort) {
        forts.remove(fort);
    }

    public void removeTree(Tree tree) {
        trees.remove(tree);
    }

    public void removeTruck(Truck truck) {
        trucks.remove(truck);
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);
    }

    public Mig getMig() {
        return mig;
    }

    public void setMig(Mig mig) {
        Wave.mig = mig;
    }
}
