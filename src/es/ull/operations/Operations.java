package es.ull.operations;

import es.ull.components.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Operations {

    protected static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    protected static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics2D g = b.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    protected static Picture setPictureWithLUT(Picture vIn, BufferedImage vOut, int[] LUT) {
        for(int i = 0; i < vOut.getWidth(); i++) {
            for(int j = 0; j < vOut.getHeight(); j++) {
                int grey = LUT[vIn.getPixelValue(i, j)];
                vOut.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }
        return new Picture(vOut);
    }
}
