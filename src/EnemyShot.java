/*
This is file to configure bullets from enemies
almost the same as bullets from the players ship
 */

import java.awt.*;

public class EnemyShot implements Runnable {

    //dimensions of a bullet

    private int bulletWidth  = 4;
    private int bulletHeight = 5;

    private int bulletSpeed = 15;

    private int x = 0;

    private int shotHeight = 0;

    boolean shotState = true;

    Ship ship = null;

    //
    public EnemyShot(int xVal, int yVal, Ship sh) {
        x = xVal;//Set the shot direction
        shotHeight = yVal;
        ship = sh;
        Thread thread = new Thread(this);
        thread.start();
    }

    //
    private boolean moveShot() {

        //did the bullet hit players ship
        if (ship.checkShot(x, shotHeight)) {
            //Ship was hit
            System.out.println("Enemy shot the ship!");
            ship.hitByAlien();
            shotState = false;
            return true;
        }

        shotHeight = shotHeight + 2;


        //Checking if the bullet is outside of the screen, if it is than the state is false
        if (shotHeight > SpaceInvaders.height) {
            shotState = false;
            return true;
        }
        return false;
    }

    //drawing the bullet
    public void drawShot(Graphics g) {
        if (shotState) {
            g.setColor(Color.orange);  //bullet is visible
        } else {
            g.setColor(Color.black);  //bullet is not visible
        }
        g.fillRect(x, shotHeight, bulletWidth, bulletHeight);
    }

    //just check state of the bullet
    public boolean getShotState() {
        return shotState;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(bulletSpeed);
            } catch(InterruptedException ie) {
                //Ignore this exception
            }

            if (moveShot()) {
                //if bullet already hit the players ship, break
                break;
            }

        }
    }


}
