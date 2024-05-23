package model;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import model.components.Plane;

import java.util.ArrayList;

public class Game {
    public static final int HEIGHT = 900;
    protected static Game instance;
    protected Plane plane;
    private Wave wave;
    private final Pane pane;
    private int hardness;
    private int waveNumber = 0;
    private final ArrayList<Transition> allAnimations = new ArrayList<>();

    public Game(Pane pane) {
        this.pane = pane;
        this.hardness = App.getGameDifficulty();
    }

    public static Game getInstance() {
        return instance;
    }

    public static void setInstance(Game game) {
        instance = game;
    }

    public int getHardness() {
        return hardness;
    }

    public Wave getWave() {
        return wave;
    }

    public void setWave(Wave wave) {
        this.wave = wave;
    }

    public void addAnimations(Transition animation) {
        allAnimations.add(animation);
    }

    public ArrayList<Transition> getAllAnimations() {
        return allAnimations;
    }

    public Pane getPane() {
        return pane;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void removeAnimation(Transition transition) {
        allAnimations.remove(transition);
    }

    public void increaseWaveNumber() {
        waveNumber++;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

}
