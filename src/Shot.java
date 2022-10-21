/*
This is file to configure bullets
 */

import java.awt.*; //importing for the general graphics

public class Shot implements Runnable {

    //dimensions of a bullet
    private int bulletWidth = 4;
    private int bulletHeight = 15;

    private int bulletSpeed = 8;

    private int x = 0;

    private int shotHeight = 0;

    boolean shotState = true;

    EnemyArmy enemyArmy = null;

    SpaceInvaders spaceInvaders = null;

    //
    public Shot(int xVal, int yVal, EnemyArmy enemyarmy) {
        x = xVal;//Set the shot direction
        shotHeight = yVal;
        enemyArmy = enemyarmy;
        Thread thread = new Thread(this);
        thread.start();


    }

    //
    private boolean moveShot() {
        //did the bullet hit any enemy
        if (enemyArmy.checkShot(x, shotHeight)) {
                //Enemy was shot
                System.out.println("Enemy was hit");  // just to console to see what is going on
            shotState = false;
            return true;
        }

        shotHeight = shotHeight - 2;

        //Checking if the bullet is outside of the screen, if it is than the state is false
        if (shotHeight < 0) {
            shotState = false;
            return true;
        }
        return false;
    }

   //drawing the bullet
    public void drawShot(Graphics g) {
        if (shotState) {
            g.setColor(Color.white);  //bullet is visible

        } else {
            //g.setColor(Color.red);    //bullet is not visible
        }
        g.fillRect(x, shotHeight, bulletWidth, bulletHeight);

    }

    //just check state of the bullet
    public boolean getShotState() {
        return shotState;
    }

    //this runs the bullet. Using run from Runnable and Thread for the speed
    public void run() {
        while(true) {
            try {
                Thread.sleep(bulletSpeed);
            } catch(InterruptedException ie) {
                //Ignore this exception
            }

            if (moveShot()) {
                //if bullet already hit the enemy, break
                break;
            }

        }
    }






}
