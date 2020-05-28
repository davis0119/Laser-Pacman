import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RedGhost extends Ghost {

    public static final int INIT_POS_X = 100;
    public static final int INIT_POS_Y = 300;
    public static final String IMG_FILE = "files/redGhost.png";
    private static BufferedImage img;
    
    public RedGhost(int courtWidth, int courtHeight) {
        super(INIT_POS_X, INIT_POS_Y, courtWidth, courtHeight);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth() - 1,
                this.getHeight() - 1, null);
    }

    @Override
    public void handleLaser(Laser laser) {
        
    }


    // dang... hurts you a lot!
    @Override
    public void handleAttack(Player player) {
        player.decreaseHealth(3);
        player.setColor(true);
    }

}
