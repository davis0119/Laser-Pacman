import java.awt.Color;
import java.awt.Graphics;

public class Laser extends GameObj {
    
    public static final int SIZE = 20;

    private Color color;

    public Laser(int vx, int vy, int px, int py, int width, int height,
            int courtWidth, int courtHeight) {
        super(vx, vy, px, py, SIZE, SIZE, courtWidth, courtHeight);
        
        this.color = Color.CYAN;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

}
