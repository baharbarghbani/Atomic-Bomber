package model;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import model.components.Plane;

import java.util.ArrayList;

public class Game {
    public static final int HEIGHT = 900;
    private Wave wave;
    private Pane pane;
    private int hardness = 1;
    protected Plane plane;
    private int waveNumber = 0;
    private ArrayList<Transition> allAnimations = new ArrayList<>();
    protected static Game instance;
    public static Game getInstance(){
        return instance;
    }
    public static void setInstance(Game game){
        instance = game;
    }


    public Game(Pane pane){
        this.pane = pane;
    }

    public int getHardness(){
        return hardness;
    }
    public void setHardness(int hardness){
        this.hardness = hardness;
    }


    public void setWave(Wave wave) {
        this.wave = wave;
    }
    public Wave getWave(){
        return wave;
    }

    public void addAnimations(Transition animation) {
        allAnimations.add(animation);
    }

    public Pane getPane() {
        return pane;
    }
    public void setPlane(Plane plane){
        this.plane = plane;
    }
    public Plane getPlane(){
        return plane;
    }

    public void removeAnimation(Transition transition) {
        allAnimations.remove(transition);
    }
    public void increaseWaveNumber(){
        waveNumber++;
    }

    public int getWaveNumber() {
        return waveNumber;
    }
}
