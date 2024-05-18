package model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
    private Game game;
    private ArrayList<Tree> trees;
    static Random random = new Random();
    public Wave(Pane pane, Game game){
        tanks = new ArrayList<>();
        trucks = new ArrayList<>();
        buildings = new ArrayList<>();
        trees = new ArrayList<>();
        forts = new ArrayList<>();
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
        GameController.createTanks(this, game, pane);
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
    public ArrayList<Building> getBuildings(){
        return buildings;
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

    public void removeObject(Rectangle object){
        allObjects.remove(object);
        pane.getChildren().remove(object);
    }

}
