/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.Graphics;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Player player; // the player, squareman
    private LaserFuel fuel; // fuel for squareman
    private RedGhost redGhost;
    private CyanGhost cyanGhost;
    private OrangeGhost orangeGhost;
    private PinkGhost pinkGhost;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    
    private Laser laser;
    private int countToWin;
    
    private boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 700;
    public static final int COURT_HEIGHT = 400;
    public static final int PLAYER_VELOCITY = 4;
    private static LaserFuel[][] fuelGrid = new LaserFuel[10][6];
    private static ArrayList<Wall> walls = new ArrayList<Wall>();

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the Player to move as long as an arrow key is pressed, by
        // changing the Player's velocity accordingly. (The tick method below actually moves the
        // player.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setVx(-PLAYER_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setVx(PLAYER_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.setVy(PLAYER_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player.setVy(-PLAYER_VELOCITY);
                } else if (player.getLCount() >= 1) {
                    
                    if (e.getKeyCode() == KeyEvent.VK_W && laser == null) {
                        player.decLCount(1);
                        laser = new Laser(0, -20, 
                            player.getPx(), player.getPy(), 20, 20,
                            COURT_WIDTH, COURT_HEIGHT);
                    } else if (e.getKeyCode() == KeyEvent.VK_A && laser == null) {
                        player.decLCount(1);
                        laser = new Laser(-20, 0, 
                            player.getPx(), player.getPy(), 20, 20,
                            COURT_WIDTH, COURT_HEIGHT);
                    } else if (e.getKeyCode() == KeyEvent.VK_S && laser == null) {
                        player.decLCount(1);
                        laser = new Laser(0, 20, 
                            player.getPx(), player.getPy(), 20, 20,
                            COURT_WIDTH, COURT_HEIGHT);
                    } else if (e.getKeyCode() == KeyEvent.VK_D && laser == null) {
                        player.decLCount(1);
                        laser = new Laser(20, 0, 
                            player.getPx(), player.getPy(), 20, 20,
                            COURT_WIDTH, COURT_HEIGHT);
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                player.setVx(0);
                player.setVy(0);
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        // make player, ghosts, walls, and spread the laser fuel
        makeEntities(); 
        playing = true;
        status.setText("Game is on!   Health: " + player.getHealth() +   
                "   Lasers Charged: " + player.getLCount());
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // advance the Player in their current direction.
            player.move();
            // move the ghosts
            for (int i = 0; i < ghosts.size(); i++) {
                ghosts.get(i).move();
            }
            
            status.setText("Game is on!   Health: " + player.getHealth() +   
                    "   Lasers Charged: " + player.getLCount()); 
            player.setColor(false);
            
            // is the laser in motion?
            if (laser != null) {
                laser.move();
            }
            
            // bounce on the walls
            for (int i = 0; i < ghosts.size(); i++) {
                ghosts.get(i).bounce(ghosts.get(i).hitWall());
            }
            
            // which ghost is hitting you?
            for (int i = 0; i < ghosts.size(); i++) {
                if (player.intersects(ghosts.get(i))) {
                    if (player.intersects(redGhost)) {
                        status.setText("This hurts a lot more than usual..." +
                                " Health: " + player.getHealth() + 
                                "   Laser Count: " + player.getLCount()); 
                        ghosts.get(i).handleAttack(player);
                    } else if (player.intersects(cyanGhost)) {
                        status.setText("It took my Laser Fuel!" +
                                " Health: " + player.getHealth() + 
                                "   Laser Count: " + player.getLCount()); 
                        ghosts.get(i).handleAttack(player);
                    } else if (player.intersects(orangeGhost)) {
                        status.setText("Ow! It's stealing something too!" +
                                " Health: " + player.getHealth() + 
                                "   Laser Count: " + player.getLCount()); 
                        ghosts.get(i).handleAttack(player);
                    } else if (player.intersects(pinkGhost)) {
                        status.setText("OUCH! But hey that's cool too." +
                                " Health: " + player.getHealth() + 
                                "   Laser Count: " + player.getLCount()); 
                        ghosts.get(i).handleAttack(player);
                    } 
                }
            }
           
            // is the laser hitting a wall?
            for (int i = 0; i < walls.size(); i++) {
                if (laser != null && laser.intersects(walls.get(i))) {
                    laser = null;
                    walls.get(i).depleteHealth();
                    if (walls.get(i).getHealth() == 0) {
                        walls.remove(i);
                        break;
                    } 
                } else if (laser != null && laser.intersects(player)) { // hit the player?
                    if (laser.getColor() == Color.RED) {
                        hurtPlayer(1);
                    }
                } else if (laser != null && (laser.getPx() >= 680 || // out of bounds?
                      laser.getPx() <= 10 || laser.getPy() >= 380 ||
                      laser.getPy() <= 10)) {
                    laser = null;
                } else if (player.intersects(walls.get(i))) { // don't touch the walls!
                    status.setText("ow ow ow!" +
                                " Health: " + player.getHealth() + 
                                "   Laser Count: " + player.getLCount());
                    player.bounce(player.hitObj(walls.get(i)));
                    hurtPlayer(1);
                    player.setColor(player.intersects(walls.get(i)));
                } // hit a ghost with the laser?
                for (int j = 0; j < ghosts.size(); j++) {
                    if (ghosts.get(j).intersects(walls.get(i))) {
                        ghosts.get(j).bounce(ghosts.get(j).hitObj(walls.get(i)));
                    }  else if (laser != null && laser.intersects(ghosts.get(j))) {
                        ghosts.get(j).handleLaser(laser);
                        ghosts.remove(ghosts.get(j));
                    }
                }
            }
            
            // did you collect fuel?
            for (int i = 0; i < fuelGrid.length; i++) {
                for (int j = 0; j < fuelGrid[i].length; j++) {
                    if (player.intersects(fuelGrid[i][j])) {
                        fuelGrid[i][j] = null;
                        if (fuelGrid[i][j] == null) {
                            countToWin++;
                            player.incLCount();
                        }
                    }
                }
            }
            // update the display
            playing = !gameOver();
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw everything!
        player.draw(g);
        for (int i = 0; i < ghosts.size(); i++) {
            ghosts.get(i).draw(g);
        }
        if (laser != null) {
            laser.draw(g);
        }
        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).draw(g);
        }
        for (int i = 0; i < fuelGrid.length; i++) {
            for (int j = 0; j < fuelGrid[i].length; j++) {
                if (fuelGrid[i][j] != null) {
                    fuelGrid[i][j].draw(g);
                }
            }
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    public int getCountToWin() {
        return this.countToWin;
    }
    
    public void setCountToWin(int x) {
        this.countToWin = x;
    }
    
    public LaserFuel[][] getFuelGrid() {
        LaserFuel[][] copy = new LaserFuel[fuelGrid.length][fuelGrid[0].length];
        for (int i = 0; i < GameCourt.fuelGrid.length; i++) {
            for (int j = 0; j < GameCourt.fuelGrid[i].length; j++) {
                copy[i][j] = GameCourt.fuelGrid[i][j];
            }
        }
        return copy;
    }
    
    public boolean gameOver() {
        if (this.countToWin == 60) {
            playing = false;
            status.setText("You win!");
            return true;
        } else if (player.getHealth() <= 0) { // oh...
            playing = false;
            status.setText("You have fallen...");
            return true;
        }
        return false;
    }
    
    public Player getPlayer() {
        Player copy = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        copy.setLCount(this.player.getLCount());
        copy.setPx(this.player.getPx());
        copy.setPy(this.player.getPy());
        copy.setVx(this.player.getVx());
        copy.setVy(this.player.getVy());
        copy.decreaseHealth(100 - this.player.getHealth());
        return copy;
    }
    
    public void hurtPlayer(int x) {
        this.player.decreaseHealth(x);
    }
    
    public ArrayList<Ghost> getGhosts() {
        ArrayList<Ghost> copy = new ArrayList<Ghost>(this.ghosts);
        return copy;
    }
    
    public ArrayList<Wall> getWalls() {
        ArrayList<Wall> wCopy = new ArrayList<Wall>(GameCourt.walls);
        return wCopy;
    }
    
    public void makeEntities() {
        // make the player
        player = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        // make the ghosts
        redGhost = new RedGhost(COURT_WIDTH, COURT_HEIGHT);
        cyanGhost = new CyanGhost(COURT_WIDTH, COURT_HEIGHT);
        orangeGhost = new OrangeGhost(COURT_WIDTH, COURT_HEIGHT);
        pinkGhost = new PinkGhost(COURT_WIDTH, COURT_HEIGHT);
        
        // reset the ghost list
        ghosts.removeAll(ghosts);
        // fill the ghost list
        ghosts.add(redGhost);
        ghosts.add(cyanGhost);
        ghosts.add(orangeGhost);
        ghosts.add(pinkGhost);
        this.laser = null;
        player.resetLCount();
        this.countToWin = 0;
        walls.removeAll(walls);

        // bottom left and bottom right walls
        Wall wall1 = new Wall(60, 200, 20, 140, COURT_WIDTH, COURT_HEIGHT);
        Wall wall2 = new Wall(620, 200, 20, 140, COURT_WIDTH, COURT_HEIGHT);
        // bottom middle
        Wall wall3 = new Wall(190, 330, 40, 50, COURT_WIDTH, COURT_HEIGHT);
        Wall wall4 = new Wall(470, 330, 40, 50, COURT_WIDTH, COURT_HEIGHT);
        Wall wall5 = new Wall(270, 315, 160, 20, COURT_WIDTH, COURT_HEIGHT);
        // left, right walls
        Wall wall6 = new Wall(130, 150, 20, 175, COURT_WIDTH, COURT_HEIGHT);
        Wall wall7 = new Wall(550, 150, 20, 175, COURT_WIDTH, COURT_HEIGHT);
        // top top
        Wall wall8 = new Wall(70, 75, 140, 20, COURT_WIDTH, COURT_HEIGHT);
        Wall wall9 = new Wall(490, 75, 140, 20, COURT_WIDTH, COURT_HEIGHT);
        Wall wall10 = new Wall(280, 75, 140, 20, COURT_WIDTH, COURT_HEIGHT);
        Wall wall11 = new Wall(335, 20, 35, 55, COURT_WIDTH, COURT_HEIGHT);
        // middle box
        Wall wall12 = new Wall(270, 150, 20, 100, COURT_WIDTH, COURT_HEIGHT);
        Wall wall13 = new Wall(410, 150, 20, 100, COURT_WIDTH, COURT_HEIGHT);
        Wall wall14 = new Wall(270, 250, 160, 20, COURT_WIDTH, COURT_HEIGHT);
        // connect to middle box
        Wall wall15 = new Wall(150, 190, 120, 20, COURT_WIDTH, COURT_HEIGHT);
        Wall wall16 = new Wall(430, 190, 120, 20, COURT_WIDTH, COURT_HEIGHT);
        // upper wings
        Wall wall17 = new Wall(200, 75, 20, 70, COURT_WIDTH, COURT_HEIGHT);
        Wall wall18 = new Wall(480, 75, 20, 70, COURT_WIDTH, COURT_HEIGHT);
        
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
        walls.add(wall5);
        walls.add(wall6);
        walls.add(wall7);
        walls.add(wall8);
        walls.add(wall9);
        walls.add(wall10);
        walls.add(wall11);
        walls.add(wall12);
        walls.add(wall13);
        walls.add(wall14);
        walls.add(wall15);
        walls.add(wall16);
        walls.add(wall17);
        walls.add(wall18);
        
        // place the little fuels around
        for (int i = 0; i < fuelGrid.length; i++) {
            int xCounter = i * 70 + 30;
            for (int j = 0; j < fuelGrid[i].length; j++) {
                int yCounter = j * 60 + 50;
                fuel =  new LaserFuel(COURT_WIDTH, COURT_HEIGHT,
                        xCounter, yCounter, Color.WHITE);
                fuelGrid[i][j] = fuel;
            }
        }
    }
}