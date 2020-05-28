import java.awt.*;

public class Wall extends GameObj {
    
    public static final int SIZE = 20;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    private int health = 3;

    private Color color;
    
    public Wall(int px, int py, int width, int height,
            int courtWidth, int courtHeight) {
        
        super(INIT_VEL_X, INIT_VEL_Y, px, py,
                width, height, courtWidth, courtHeight);
        this.color = Color.BLUE;
    }

    public int getHealth() {
        return this.health;
    }
    
    public void depleteHealth() {
        this.health--;
        if (this.health == 3) { // full health
            this.color = Color.BLUE;
        } else if (this.health == 2) { // half health
            this.color = Color.YELLOW; 
        }  else if (this.health == 1) { // low health
            this.color = Color.RED;
        }
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());   
    }
    
    
}
