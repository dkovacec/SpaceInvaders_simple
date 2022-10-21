/*
File for creating enemy army
 */
import java.awt.*;
import java.util.*;

public class EnemyArmy {

    //These are arrays for the enemies
    //Each array is one row
    Enemy rowOne[] = new Enemy[10];
    Enemy rowTwo[] = new Enemy[10];




    //Enemies will move to the right
    boolean movingRight = true;

    //How many pixels should they move?
    int moveDistance = 15;

    //A container to store details of the current alien shots
    Vector alienShots = new Vector();

    private Ship ship;

    private SpaceInvaders spaceInvaders;

    Image alienImage = null;



    public EnemyArmy(Ship s, SpaceInvaders si, Image ai) {
        ship = s;
        spaceInvaders = si;
        alienImage = ai;


        createArmy();
        setStartingPositions();
    }

    //Initializing enemy rows
    private void createArmy() {


        //Filling the enemy arrays with loop
        for (int i = 0; i < 10; i++) {
            rowOne[i] = new Enemy(alienImage, spaceInvaders);
            rowTwo[i] = new Enemy(alienImage, spaceInvaders);
        }
    }

    //height from where enemy will start apperaing
    private void setStartingPositions() {

        int rowHeight = 45;     //top row height
        int leftStart = 45;     //appear 50 from the left

        for (int i = 0; i < 10; i++) {
            rowOne[i].setPosition(leftStart, rowHeight);
            leftStart += 40;
        }

        rowHeight += 45;        //second row
        leftStart = 45;         //second row
        for (int i = 0; i < 10; i++) {
            rowTwo[i].setPosition(leftStart, rowHeight);
            leftStart += 40;
        }


    }

    // moving the enemies
    public void moveArmy() {

        if (movingRight) {
            //We are moving right

            //Before anything first check if the rightest enemy touched the right edge
            for (int i = 9; i >= 0; i--) {  //Loop goes down as there is 10 enemies in the row
                //check if it has been kiiled before it touches the edge
                if (!rowOne[i].hasBeenHit()) {

                    //as long as it has not been killed it can touch the edge
                    //check for edge, if left border of the enemy is 30 px away from edge, edge has been touched
                    if (rowOne[i].getXPos() > (SpaceInvaders.width - Enemy.alienWidth-35)) {
                        //go left
                        movingRight = false;


                        //Set the new lower y positions
                        for (int y = 0; y < 10; y++) {
                            rowOne[y].setPosition(rowOne[y].getXPos(), rowOne[y].getYPos() + moveDistance);
                            rowTwo[y].setPosition(rowTwo[y].getXPos(), rowTwo[y].getYPos() + moveDistance);

                        }

                        return;

                    }
                }

                //check the same for the second row
                if (!rowTwo[i].hasBeenHit()) {

                    if (rowTwo[i].getXPos() > (SpaceInvaders.width - Enemy.alienWidth - 35)) {

                        movingRight = false;

                        for (int y = 0; y < 10; y++) {
                            rowOne[y].setPosition(rowOne[y].getXPos(), rowOne[y].getYPos() + moveDistance);
                            rowTwo[y].setPosition(rowTwo[y].getXPos(), rowTwo[y].getYPos() + moveDistance);

                        }
                        return;
                    }

                }

            }

            //Everyone moves to the right  (like the song)
            for (int i = 0; i < 10; i++) {
                rowOne[i].setPosition(rowOne[i].getXPos() + moveDistance, rowOne[i].getYPos());
                rowTwo[i].setPosition(rowTwo[i].getXPos() + moveDistance, rowTwo[i].getYPos());
            }




        } else {
            //Everyone moves to the left  (like the song)
            //like with the right edge, we need to check for the left edge touchdown
            for (int i = 0; i < 10; i++) {
                if (!rowOne[i].hasBeenHit()) {

                    if (rowOne[i].getXPos() < Enemy.alienWidth + 5) {  //using only alienWidth as it is 15, and if X is less the 15  left edge is touched
                        //go right, away from the edge
                        movingRight = true;


                        for (int y = 0; y < 10; y++) {
                            rowOne[y].setPosition(rowOne[y].getXPos(), rowOne[y].getYPos() + moveDistance);
                            rowTwo[y].setPosition(rowTwo[y].getXPos(), rowTwo[y].getYPos() + moveDistance);

                        }

                        return;
                    }

                }

                //check the same for the second row
                if (!rowTwo[i].hasBeenHit()) {

                    if (rowTwo[i].getXPos() < Enemy.alienWidth + 5) {

                        movingRight = true;

                        for (int y = 0; y < 10; y++) {
                            rowOne[y].setPosition(rowOne[y].getXPos(), rowOne[y].getYPos() + moveDistance);
                            rowTwo[y].setPosition(rowTwo[y].getXPos(), rowTwo[y].getYPos() + moveDistance);

                        }

                        return;//Return from this method, don't bother checking the rest.
                    }

                }
            }

            //Every moves to the left  (like the song)
            for (int i = 0; i < 10; i++) {
                rowOne[i].setPosition(rowOne[i].getXPos() - moveDistance, rowOne[i].getYPos());
                rowTwo[i].setPosition(rowTwo[i].getXPos() - moveDistance, rowTwo[i].getYPos());

            }

        }

        if(spaceInvaders.initialStart) {
            //don't fire
        } else {

            //Enemy firing at random
            Random generator = new Random();
            int rnd1 = generator.nextInt(10);
            int rnd2 = generator.nextInt(10);

            if (!rowOne[rnd1].hasBeenHit()) {
                EnemyShot as = new EnemyShot(rowOne[rnd1].getXPos() + (int) (Enemy.alienWidth / 2), rowOne[rnd1].getYPos(), ship);
                alienShots.addElement(as);
            }
            if (!rowOne[rnd2].hasBeenHit()) {
                EnemyShot as = new EnemyShot(rowTwo[rnd2].getXPos() + (int) (Enemy.alienWidth / 2), rowTwo[rnd2].getYPos(), ship);
                alienShots.addElement(as);
            }

        }

    }

    //drawing enemy army
    public void drawArmy(Graphics g) {
        //drawing the first row
        for (int i = 0; i < 10; i++) {
            rowOne[i].drawAlien(g);
            rowTwo[i].drawAlien(g); //Drawing the second row with the same loop

        }

        //Drawing the bullets that are eventualy randomly fired
        Vector tmp = new Vector();
        for (int i = 0; i < alienShots.size(); i++) {
            EnemyShot alienbullet = (EnemyShot)alienShots.elementAt(i);

            //Need to remove old shots at this point!
            if (alienbullet.getShotState()) {
                //This is NOT an old shot
                tmp.addElement(alienbullet);
            }
            alienbullet.drawShot(g);

        }
        alienShots = tmp;
    }

    //check if there is hit - bullet colided with ship
    public boolean checkShot(int x, int y) {
        for (int i = 0; i < 10; i++) {
            if (rowOne[i].hitAlien(x, y)) {
                spaceInvaders.hitAlienScore();
                return true;
            }
            if (rowTwo[i].hitAlien(x, y)) {
                spaceInvaders.hitAlienScore();
                return true;
            }

        }
        return false;
    }

}
