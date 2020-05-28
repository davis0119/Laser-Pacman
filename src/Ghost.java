
import java.awt.*;

public abstract class Ghost extends GameObj {
    public static final int SIZE = 35;
    public static final int INIT_VEL_X = 2;
    public static final int INIT_VEL_Y = 3;


    
    public Ghost(int px, int py, int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, px, py,
                SIZE, SIZE, courtWidth, courtHeight);
    }

    // how will the different ghosts handle lasers?
    public abstract void handleLaser(Laser laser);
    
    // how will the different ghosts attack?
    public abstract void handleAttack(Player player);
    
    @Override
    public void draw(Graphics g) {
    }
        
}