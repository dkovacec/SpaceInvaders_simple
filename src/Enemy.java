/*
This is file to configure enemies
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*; //importing for the general graphics
import java.awt.image.BufferedImage;
import java.io.*;

public class Enemy {

    //defining dimensions of a enemy ship
    public static int alienHeight = 35;  //25
    public static int alienWidth  = 25;  //20

    //variable for inital position
    //initial position if from the left, as the enemies will move to the right
    private int leftPosition = 0;
    private int heightPosition = 0;

    //check state of enemy ship, by default it is not killed
    private boolean hitState = false;

    private Image alienImage = null;



    SpaceInvaders spaceInvaders = null;

    public static int enemiesKilled = 0;

    //images
    public Enemy(Image ai, SpaceInvaders si) {
        alienImage = ai;
        spaceInvaders = si;

        try {
            BufferedImage img = ImageIO.read(new File("src/enemySmall3.jpg"));
            ImageIcon imageI = new ImageIcon(img);
            JLabel label = new JLabel();
            label.setIcon(imageI);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        InputStream image = null;
//        try {
//            image = new FileInputStream("src/enemy2.jpg");
//            Image eImage = new Image(image);
//            ImageView vImage = new ImageView(eImage);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//

    }

    //return if the enemy has been shot
    public boolean hasBeenHit() {
        return hitState;
    }

    //x & y are positions of a bullet
    public boolean hitAlien(int x, int y) {

        //If the enemy is still alive, than it has not been shot, so it must be false
        if (hitState) {
            return false;
        }

        //checking if bullet positions coresponds with enemy position

        if ((x >= leftPosition) && (x <= (leftPosition+alienWidth))) {
            //X is ok, now lets check the Y range
            if ((y >= heightPosition) && (y <= (heightPosition+alienHeight))) {
                //both x & y are same as enemy positions
                hitState = true;
//                enemiesKilled = enemiesKilled + 1;
//                System.out.println("Enemies killed so far: " + enemiesKilled);  //just to console to see what is hapening
                return true;

            }
        }
        return false;
    }

    //puts enemy on the screen
    public void setPosition(int x, int y) {
        leftPosition = x;
        heightPosition = y;
    }

    //getter for the x position
    public int getXPos() {
        return leftPosition;
    }

    //getter for y position
    public int getYPos() {
        return heightPosition;
    }

    //draw the image of the enemy
    public void drawAlien(Graphics g) {
        //first checking if it is not shot
        if (!hitState) {
            g.setColor(Color.red); //let put them red from the start
            g.fillRect(leftPosition, heightPosition, alienWidth, alienHeight);
            g.drawImage(alienImage, leftPosition, heightPosition, spaceInvaders);  //spaceInvaders this should draw image for enemy but it is not working
        }
    }

}
