import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CyanGhost extends Ghost {
    
    public static final int INIT_POS_X = 400;
    public static final int INIT_POS_Y = 300;
    public static final String IMG_FILE = "files/cyanGhost.png";
    private static BufferedImage img;

    public CyanGhost(int courtWidth, int courtHeight) {
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
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth() - 7,
                this.getHeight() - 7, null);
    }

    // will reflect the laser!
    @Override
    public void handleLaser(Laser laser) {
        laser.setVx(laser.getVx() * -1);
        laser.setVy(laser.getVy() * -1);
        laser.setColor(Color.RED);
    }

    // will eat all your fuel!
    @Override
    public void handleAttack(Player player) {
        player.resetLCount();
    }

}