package fruitcatcher;

import processing.core.PImage;

public class Enemy extends Element{

    public Enemy(int position, int direction, int speed) {
        super(position, direction, speed);
        // direction:  0 -- northeast, 1 -- southeast
        //             2 -- northwest, 3 -- southwest
    }

    public void tick(App app) {
        timer ++;
        if(timer == count) {
            if(position == app.player.position) {
                app.lose = true;
                return ;
            }
            int row = position / App.NUMCOL;
            int col = position % App.NUMCOL;

            if(direction == 0) {
                if (col == App.NUMCOL - 1) {
                    if (row == 0) {
                        direction = 3;
                    }
                    else {
                        direction = 2;
                    }
                }
                else {
                    if (row == 0) {
                        direction = 1;
                    }
                    else {
                        col ++; row --;
                    }
                }
            }
            else if(direction == 1) { //southeast
                if (row == App.NUMROW - 1) {
                    if (col == App.NUMCOL - 1) {
                        direction = 2;
                    }
                    else {
                        direction = 0;
                    }
                }
                else {
                    if (col == App.NUMCOL - 1) {
                        direction = 3;
                    }
                    else {
                        col ++; row ++;
                    }
                }
            }
            else if(direction == 2) {
                if (row == 0) {
                    if (col == 0) {
                        direction = 1;
                    }
                    else {
                        direction = 3;
                    }
                }
                else {
                    if (col == 0) {
                        direction = 0;
                    }
                    else {
                        col --; row --;
                    }
                }
            }
            else { // direction = 3, southwest
                if (row == App.NUMROW - 1) {
                    if (col == 0) {
                        direction = 0;
                    }
                    else {
                        direction = 2;
                    }
                }
                else {
                    if (col == 0) {
                        direction = 1;
                    }
                    else {
                        col --; row ++;
                    }
                }
            }
            // change to new position
            position = row * App.NUMCOL + col;
            timer = 0;
        }
    }

}
