import java.awt.*;

public class LaserFuel extends GameObj  {
    
    public static final int SIZE = 10;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private Color color;

    public LaserFuel(int courtWidth, int courtHeight,
            int addX, int addY, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X + addX,
                INIT_POS_Y + addY, SIZE, SIZE, courtWidth, courtHeight);
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

}
