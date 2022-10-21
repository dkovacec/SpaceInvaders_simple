import java.awt.*;  //importing Abstract Window Toolkit needed for GUI
import javax.swing.*; //importing Abstract Window Toolkit needed for buttons
import java.awt.image.BufferedImage;



public class SpaceInvaders extends JFrame implements Runnable {


    public static int width = 600;//The width of the frame
    public static int height = 500;//The height of the frame

    private int gameSpeed = 100;

    EnemyArmy army = null;

    Ship ship = null;

    //boolean needed for the mouse events, by default it is false as game is running
    private boolean paused = false;
    public boolean initialStart = true;

    private int score = 0;

    Graphics offscreen_high;
    BufferedImage offscreen;

    Image backGroundImage = null;
    Image pauseImage = null;
    Image alienImage = null;

    Image winImage = null;
    Image lostImage = null;

    Image splashImage = null;



    public SpaceInvaders(String frameTitle) {
        super(frameTitle);

        // terminate the game if window is closed
        // adding windowlistener
        this.addWindowListener (new java.awt.event.WindowAdapter() {
            //add event for window closure
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    System.exit(0);
                }
            }
        );

        //defining game background image
        backGroundImage = new javax.swing.ImageIcon("src/backgroundbordered.jpg").getImage();

        //defining game splash image
        splashImage = new javax.swing.ImageIcon("src/splashScreen.jpg").getImage();

        //defining game pause image
        pauseImage = new javax.swing.ImageIcon("src/pause.jpg").getImage();

        //defining the looks of an enemy ships
        alienImage = new javax.swing.ImageIcon("src/enemySmall3.jpg").getImage();

        //defining game won image
        winImage = new javax.swing.ImageIcon("src/win.jpg").getImage();

        //defining game lost image
        lostImage = new javax.swing.ImageIcon("src/lost.jpg").getImage();

        //Constructor that creates the player ship
        this.ship = new Ship(this);

        //Constructor that creates enemyships
        army = new EnemyArmy(ship, this, alienImage);

        //Ship is controled by mouse, as it is more effective than keyboard
        //adding mouseListener from awt - this react to click of the mouse
        addMouseListener(ship);
        //Playership should be moved as mouse move
        addMouseMotionListener(ship);

        //offscreen = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        offscreen = new BufferedImage(width, height,1);
        offscreen_high = offscreen.createGraphics();

        //set background color
        setBackground(Color.black);
        setSize(width, height);
        setVisible(true);
        startGame();
    }

    //if the mouse is initialy off the screen, game is paused
    public void pauseGame(boolean state) {
        paused = state;
    }

    //For each hit to the enemy you get points
    public void hitAlienScore() {
        //also add points to the score
        score += 10;
        System.out.println("Score = "+score);  //just to console to track what is happening

        Enemy.enemiesKilled = Enemy.enemiesKilled + 1;
        System.out.println("Enemies killed so far: " + Enemy.enemiesKilled);  //just to console to see what is hapening
    }

    //for each shot received from enemy player loose points
    public void shotShip() {
        //removes points from the score
        score -= 20;
        System.out.println("Score = "+score); //just to console to track what is happening
    }

    // using threads to update the screen
    public void startGame() {
        //These two lines look confusing to me but basically they start the
        //game process, i.e. update the display screen every 100ms.
        Thread thread = new Thread(this);
        thread.start();

        // change the state of the game ???
    }

    //creating black filled rectangle as game screen, method from awt
    public void paint(Graphics g) {

        offscreen_high.setColor(Color.black);                   //create color to fill
        offscreen_high.fillRect(0,0, width, height);        //fill rectangle with color from 0.0 till width.height
        offscreen_high.drawImage(backGroundImage, 0, 0, this);  //should draw the image for background


        army.drawArmy(offscreen_high);      //draw enemies
        ship.drawShip(offscreen_high);      //draw players ship
        g.drawImage(offscreen,0,0,this);

        //drawing score on the screen
        Graphics2D g2 = (Graphics2D)g;
        Font font = new Font("Arial", Font.BOLD, 14);
        Text screenScore = new Text("Energy: " + score, new Font("Tahoma", Font.BOLD, 14), 500, 55);
        Text enemyScore = new Text("Enemies destroyed: " + Enemy.enemiesKilled, new Font("Tahoma", Font.BOLD, 14), 30, 55);
        screenScore.drawText(g2);
        enemyScore.drawText(g2);

        if(initialStart) {

            g.drawImage(splashImage, 0, 0, this);  //should draw the image for pause status

        }


        if (Ship.mouseExit) {
            //drawing pause text on the screen

            //Text pausedGame = new Text("PAUSED", new Font("Tahoma", Font.BOLD, 14), 150, 380);
            //Text pausedGame2 = new Text("It will continue when cursor is back.", new Font("Tahoma", Font.BOLD, 10), 150, 400);
            //Text pausedGame3 = new Text("SPACE INVADERS", new Font("Tahoma", Font.BOLD, 25), 150, 350);
            g.drawImage(pauseImage, 0, 0, this);  //should draw the image for pause status

            //pausedGame.drawText(g2);
            //pausedGame2.drawText(g2);
            //pausedGame3.drawText(g2);
        }


        //if the player lost the game by being shot too many times
        if (score < 0) {
            paused = true;
            g.drawImage(lostImage, 0, 0, this);  //should draw the image for loosing game
//            Text looseText = new Text("You lost!", new Font("Tahoma", Font.BOLD, 30), 200, 250);
//            Text looseText2 = new Text("Your score is bellow 0.", new Font("Tahoma", Font.BOLD, 14), 200, 300);
//            looseText.drawText(g2);
//            looseText2.drawText(g2);
        }

        if (Enemy.enemiesKilled > 19) {
            paused = true;
            g.drawImage(winImage, 0, 0, this);  //should draw the image for loosing game
//            Text winText = new Text("You won!", new Font("Tahoma", Font.BOLD, 30), 200, 250);
//            winText.drawText(g2);
        }


    }

    //
    public void update(Graphics g) {
        paint(g);
    }

    //moving enemies
    public void moveEnemies() {
        army.moveArmy();

    }

    //run the game
    public void run() {
        int count = 0;
        while(true) {
            try {
                Thread.sleep(gameSpeed);
            } catch(InterruptedException ie) {
                //Ignore this exception
            }
            //if the game is not paused, move the enemies
            if (!paused) {
                if (count >= 5) {
                   moveEnemies();
                    count = 0;
                }
            }
            repaint();   // re-paints the game screen as to see changes
            count ++;

        }
    }

    // calling enemies
    public EnemyArmy getAlienArmy() {
        return army;
    }

    //main method to start the game itself
    public static void main(String []args) {
        SpaceInvaders invaders = new SpaceInvaders("Space Invaders");
    }

    public int getScore() {
        return score;
    }

    public boolean isInitialStart() {
        return initialStart;
    }

    public void setInitialStart(boolean initalStart) {
        this.initialStart = initalStart;
    }
}

