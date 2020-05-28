import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OrangeGhost extends Ghost {
    
    public static final int INIT_POS_X = 350;
    public static final int INIT_POS_Y = 200;
    public static final String IMG_FILE = "files/orangeGhost.png";
    private static BufferedImage img;

    public OrangeGhost(int courtWidth, int courtHeight) {
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
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth() - 6,
                this.getHeight() - 6, null);
    }

    @Override
    public void handleLaser(Laser laser) {
    }

    // will sap laser fuel and hurt you!
    @Override
    public void handleAttack(Player player) {
        player.decLCount(0.5);
        player.decreaseHealth(2);
    }

}