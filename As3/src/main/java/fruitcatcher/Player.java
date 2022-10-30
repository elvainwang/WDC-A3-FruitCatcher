package fruitcatcher;

import java.util.ArrayList;
import java.util.List;

public class Player extends Element{
    public int status;  // 0 -- pause, 1 -- move

    public Player(int postion, int direction, int speed) {
        super(postion, direction, speed);
        this.status = 0;  // initial status : move
        // direction: 0 -- right; 1 -- down; 2 -- left; 3 -- up
    }

    public boolean collideWithEnemy(List<Enemy> enemies) {
        for (Enemy enemy: enemies) {
            if (position == enemy.position){
                return true;
            }
        }
        return false;
    }

    public void tick(App app) {
        if (status == 1) {
            timer ++;
            if (timer == count) {
                if (collideWithEnemy(app.enemies)) {
                    app.lose = true;
                    return ;
                }
                int row = position / App.NUMCOL;
                int col = position % App.NUMCOL;
                if (direction == 0) {
                    if (col == App.NUMCOL - 1) {
                        status = 0;
                    }
                    else {
                        col ++;
                    }
                }
                else if (direction == 1) {
                    if (row == App.NUMROW - 1) {
                        status = 0;
                    }
                    else {
                        row ++;
                    }
                }
                else if (direction == 2) {
                    if (col == 0) {
                        status = 0;
                    }
                    else {
                        col --;
                    }
                }
                else {
                    if (row == 0) {
                        status = 0;
                    }
                    else {
                        row --;
                    }
                }
                timer = 0;
                position = row * App.NUMCOL + col;
            }
        }
    }

    public void pressKey(int keyCode, App app) {
        // Left: 37
        // Up: 38
        // Right: 39
        // Down: 40
        if (keyCode == 37) {
            direction = 2;
            if (status == 0) status = 1;
        }
        else if (keyCode == 38) {
            direction = 3;
            if (status == 0) status = 1;
        }
        else if (keyCode == 39) {
            direction = 0;
            if (status == 0) status = 1;
        }
        else if (keyCode == 40) {
            direction = 1;
            if (status == 0) status = 1;
        }
    }


}
