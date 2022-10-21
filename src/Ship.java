/*
This is file to configure players ship
 */
import java.awt.event.*;  //importing for the mouse events
import java.awt.*; //importing for the general graphics

public class Ship implements MouseListener, MouseMotionListener {

    //defining dimesions of a playyers ship
    public static int shipHeight = 25;
    public static int shipWidth  = 40;

    public static boolean mouseExit = false;

    private int x = 0;
    private int heightPosition = 0;

    private Image shipImage = null;

    SpaceInvaders spaceInvaders = null;




    //This allows only one shot at the time
    Shot shot = null; //null
    Shot shot2 = null; //null

    boolean hitState = false;

    public Ship(SpaceInvaders si) {
        spaceInvaders = si;

        //this defines starting ship position
        x = (int)((SpaceInvaders.width/2)+(shipWidth/2));   //half of window width dimensions + half of ship width is the center of the ship
        heightPosition = SpaceInvaders.height-shipHeight-30;  //window height - shiphHeight - 30 puts the position somewhat above the bottom of the screen but above the border
    }


    //mouse movement will move the players ship
    //newX is the mouse position and x is the ship center position
    public void mouseMoved(MouseEvent me) {
        int newX = me.getX();
        //if mouse goes to the edge, put ship at 10 near the edge
        if (newX > (SpaceInvaders.width-shipWidth-25)) {
            //Stop the ship moving right off the screen
            x = SpaceInvaders.width-shipWidth-25;
        } else if(newX < (shipWidth - 20)) {
            //Stop the ship moving left off the screen
            x = shipWidth - 20;
        } else {
            //set x, the ship, at the same position as the mouse, newX
            x = newX;
        }
    }

    //not used, but implemented with MouseListeners implements on top
    public void mouseDragged(MouseEvent me) {

    }

    // if the mouse returns to the game screen, continue the game
    public void mouseEntered(MouseEvent me) {
        spaceInvaders.pauseGame(false);
        spaceInvaders.initialStart = false;
        mouseExit = false;
    }

    // if the mouse is of the game screen, game is paused
    public void mouseExited(MouseEvent me) {
        spaceInvaders.pauseGame(true);
        mouseExit = true;
    }

    //not used, but implemented with implements on top
    public void mouseReleased(MouseEvent me) {

    }

    //not used, but implemented with implements on top
    public void mousePressed(MouseEvent me) {

    }

    //shooting
    public void mouseClicked(MouseEvent me) {
	    EnemyArmy army = spaceInvaders.getAlienArmy();  //calling enemies for what to shoot

        shot = new Shot(x+(int)(shipWidth - 3), heightPosition, army);  //bullet is positioned from the right wing
        shot2 = new Shot(x, heightPosition, army);  //bullet is positioned from the left wing
    }


    //drawing players ship on the game screen
    public void drawShip(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(x, heightPosition, shipWidth, shipHeight);
        shipImage = new javax.swing.ImageIcon("src/ship1.jpg").getImage();
        g.drawImage(shipImage, x, heightPosition, spaceInvaders);

        //if the bullet fired is still on the screen, draw it again
        //as the screen re-paint itself, it is necessary to be redrawn
        if ((shot != null) && (shot.getShotState())) {
            shot.drawShot(g);

        }

        if ((shot2 != null) && (shot2.getShotState())) {
            shot2.drawShot(g);
        }

    }

    //enemy fires a shot, does it hits the player
    //xShot & yShot are position of enemy bullet
    public boolean checkShot(int xShot, int yShot) {

        //compare enemy bullet position with ships position
        //first compare if x coordinate is inside ships width
        if ((xShot >= x) && (xShot <= (x+shipWidth))) {
            //if it x is ok, now compare y coordinate if it is inside ships height
            if ((yShot >= heightPosition) && (yShot <= (heightPosition+shipHeight))) {
                hitState = true;   //with both condition x & y, ship was shot
                return true;
            }
        }
        return false;
    }

    //what happens if is shot by an enemy
    public void hitByAlien() {
        spaceInvaders.shotShip();
    }

}


