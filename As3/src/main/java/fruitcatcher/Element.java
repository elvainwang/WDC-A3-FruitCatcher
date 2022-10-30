package fruitcatcher;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Element {
    // position & direction
    public int position;
    public int direction;
    public int speed; // # of pixels per frame
    public int count;
    public int timer;

    public Element(int position, int direction, int speed) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.count = App.ICONSIZE / speed;
        this.timer = 0;
    }

    // is called for every frame
    public abstract void tick(App app);
}
