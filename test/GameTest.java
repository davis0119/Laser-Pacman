import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;


/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {

    private GameCourt court;
    @BeforeEach
    public void setUp() {
        JLabel status = new JLabel("Running...");
        court = new GameCourt(status);
    }
    
    public static final int COURT_WIDTH = 700;
    public static final int COURT_HEIGHT = 400;
    
    @Test
    public void testGetHealthPlayer() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        assertEquals(100, s.getHealth());
    }
    
    @Test
    public void testGetHealthPlayerEncap() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        int x = s.getHealth();
        x = 42;
        assertNotEquals(x, s.getHealth());
        assertEquals(100, s.getHealth());
    }
    
    @Test
    public void testVergeHealth() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        s.vergeHealth();
        assertEquals(1, s.getHealth());
    }
    
    @Test 
    public void testGetLCount() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        assertEquals(0, s.getLCount());
    }
    
    @Test 
    public void testGetLCountEncap() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        double v = s.getLCount();
        v = 6000;
        assertEquals(0, s.getLCount());
        assertNotEquals(v, s.getLCount());
    }

    @Test 
    public void testIncLCount() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        s.incLCount();
        assertEquals(0.5, s.getLCount());
    }
    
    @Test 
    public void testDecLCount() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        s.setLCount(10);
        s.decLCount(1);
        assertEquals(9, s.getLCount());
    }
    
    @Test 
    public void testDecLCountNegative() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        s.decLCount(1);
        assertEquals(0, s.getLCount());
    }
    
    @Test 
    public void testSetLCount() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        s.setLCount(10);
        assertEquals(10, s.getLCount());
    }
    
    @Test 
    public void testResetLCount() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        s.setLCount(10);
        s.resetLCount();
        assertEquals(0, s.getLCount());
    }
    
    @Test
    public void testDecHealthPlayer() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        assertEquals(100, s.getHealth());
        s.decreaseHealth(1);
        assertEquals(99, s.getHealth());
    }
    
    @Test 
    public void testGetColor() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        assertEquals(Color.YELLOW, s.getColor());
    }
    
    @Test 
    public void testGetColorEncap() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        Color c = s.getColor();
        c = Color.magenta;
        assertNotEquals(c, s.getColor());
        assertEquals(Color.YELLOW, s.getColor());
    }
    
    @Test 
    public void testSetColor() {
        Player s = new Player(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        s.setColor(true);
        assertEquals(Color.RED, s.getColor());
    }
    
    @Test
    public void testLaserGetColor() {
        Laser l = new Laser(1, 1, 1, 1, 1, 1, 200, 300);
        assertEquals(Color.CYAN, l.getColor());
    }
    
    @Test
    public void testLaserGetColorEncap() {
        Laser l = new Laser(1, 1, 1, 1, 1, 1, 200, 300);
        Color c = l.getColor();
        c = Color.BLUE;
        assertNotEquals(c, l.getColor());
        assertEquals(Color.CYAN, l.getColor());
    }
    
    @Test
    public void testLaserSetColor() {
        Laser l = new Laser(1, 1, 1, 1, 1, 1, 200, 300);
        l.setColor(Color.GREEN);
        assertEquals(Color.GREEN, l.getColor());
    }
    
    @Test
    public void testGetCountToWin() {
        assertEquals(0, court.getCountToWin());
    }
    
    @Test
    public void testGetCountToWinEncap() {
        int x = court.getCountToWin();
        x = 1000000000;
        assertNotEquals(x, court.getCountToWin());
        assertEquals(0, court.getCountToWin());
    }
    
    @Test
    public void testGetFuelGrid() {
        court.getFuelGrid();
        assertEquals(10, court.getFuelGrid().length);
        assertEquals(6, court.getFuelGrid()[0].length);
    }
    
    @Test
    public void testGetFuelGridEncap() {
        LaserFuel[][] f = court.getFuelGrid();
        LaserFuel fuel =  new LaserFuel(COURT_WIDTH, COURT_HEIGHT,
                        20, 20, Color.WHITE);
        f[0][1] = fuel;
        assertNotEquals(f[0][1], court.getFuelGrid()[0][1]);
    }
    
    @Test
    public void testGetWalls() {
        court.makeEntities();
        assertEquals(18, court.getWalls().size());
    }
    
    @Test
    public void testGetWallsEncap() {
        court.makeEntities();
        Wall w = new Wall(140, 130, 41, 51, COURT_WIDTH, COURT_HEIGHT);
        ArrayList<Wall> wMal = court.getWalls();
        wMal.add(w);
        assertNotEquals(wMal, court.getWalls());
        assertEquals(18, court.getWalls().size());
    }
    
    @Test
    public void testSetCountToWin() {
        court.setCountToWin(20);
        assertEquals(20, court.getCountToWin());
    }
    
    @Test
    public void testGameOver1() {
        court.setCountToWin(60);
        assertTrue(court.gameOver());
    }
    
    @Test
    public void testGameOver2() {
        court.makeEntities();
        court.hurtPlayer(100);
        assertTrue(court.gameOver());
    }
    
    @Test
    public void testHurtPlayer() {
        court.makeEntities();
        court.hurtPlayer(60);
        assertEquals(40, court.getPlayer().getHealth());
    }
    
    @Test
    public void testGetPlayer() {
        court.makeEntities();
        court.hurtPlayer(20);
        Player c = court.getPlayer();
        assertEquals(80, c.getHealth());
        assertEquals(0, c.getLCount());
        assertEquals(Color.YELLOW, c.getColor());
    }
    
    @Test
    public void testGetPlayerEncap() {
        court.makeEntities();
        Player c = court.getPlayer();
        court.hurtPlayer(20);
        c.decreaseHealth(99);
        assertNotEquals(80, c.getHealth());
        assertEquals(1, c.getHealth());
    }
    
    @Test
    public void testReset() {
        court.makeEntities();
        court.hurtPlayer(99);
        court.reset();
        assertEquals(100, court.getPlayer().getHealth());
    }
    
    @Test
    public void testGetGhosts() {
        court.makeEntities();
        assertTrue(court.getGhosts().get(0) instanceof RedGhost);
        assertTrue(court.getGhosts().get(1) instanceof CyanGhost);
        assertTrue(court.getGhosts().get(2) instanceof OrangeGhost);
        assertTrue(court.getGhosts().get(3) instanceof PinkGhost);
    }
    
    @Test
    public void testGetGhostsEncap() {
        court.makeEntities();
        Ghost pinky = new PinkGhost(2020, 2019);
        ArrayList<Ghost> gCopy = court.getGhosts();
        gCopy.add(pinky);
        assertNotEquals(gCopy, court.getGhosts());
        assertEquals(4, court.getGhosts().size());
    }
    
    @Test
    public void testMakeEntities() {
        court.makeEntities();
        Player c = court.getPlayer();
        assertEquals(100, c.getHealth());
        assertEquals(0, c.getLCount());
        assertEquals(4, court.getGhosts().size());
        assertEquals(18, court.getWalls().size());
    }
    
}
