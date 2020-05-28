import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PinkGhost extends Ghost {
    
    public static final int INIT_POS_X = (int) (Math.random() * 200 + 400);
    public static final int INIT_POS_Y = (int) (Math.random() * 100);
    public static final String IMG_FILE = "files/PinkGhost.png";
    private static BufferedImage img;

    public PinkGhost(int courtWidth, int courtHeight) {
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

    // will make you beg for your life, but gives you fuel!
    @Override
    public void handleAttack(Player player) {
        player.vergeHealth();
        player.setLCount(7);
    }

}