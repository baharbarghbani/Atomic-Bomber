package model.components;

import animations.BonusAnimation;
import animations.ExplosionAnimation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Game;

import java.util.Objects;

public abstract class Component extends Rectangle{
    protected Game game;
    protected Pane pane;
    protected int kill;
    protected boolean hasBonus;
    protected static ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Component.class.getResourceAsStream("/Images/explosion.gif"))));

    public Component(double x, int i, int i1, int i2, Game game, Pane pane) {
        super(x, i, i1, i2);
        this.game = game;
        this.pane = pane;
    }

    public abstract void setBackground(String url);
    public abstract void explode();
    public abstract void remove();
    public abstract void explodeByNuclear();
    public int getKill(){
        return kill;
    }
    public boolean hasBonus(){
        return hasBonus;
    }



    public Bomb getBonus() {
        return null;
    }
}