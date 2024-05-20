package model;

import javafx.scene.layout.Pane;
import model.components.*;

import controller.GameController;

import java.util.ArrayList;
import java.util.Random;

public class Wave {
    private Pane pane;
    private ArrayList<Component> allObjects;
    private ArrayList<Tank> tanks;
    private ArrayList<Truck> trucks;
    private ArrayList<Building> buildings;
    private ArrayList<Fort> forts;
    private ArrayList<ShootingTank> shootingTanks;
    private Game game;
    private ArrayList<Tree> trees;
    private static Mig mig;
    static Random random = new Random();
    public Wave(Pane pane, Game game){
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
    public ArrayList<Tank> getTanks() {
        return tanks;
    }
    public void createTrucks() {
        GameController.createTrucks(this, game, pane);
    }
    public void createTanks() {
        GameController.createTanks(this, game, pane, 3);
    }
    public void createBuildings(){
        GameController.createBuildings(this, game, pane);
    }
    public void createTrees(){
        GameController.createTrees(this, game, pane);
    }
    public void createFort(){
        GameController.createFort(this, game, pane);
    }
    public void createShootingTanks(){
        GameController.createShootingTanks(this, game, pane);
    }
    public ArrayList<Building> getBuildings(){
        return buildings;
    }
    public ArrayList<ShootingTank> getShootingTanks(){
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

    public void removeObject(Component object){
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
    public static void removeMig() {
        mig = null;
    }
    public Mig getMig() {
        return mig;
    }

    public void setMig(Mig mig) {
        Wave.mig = mig;
    }
}
