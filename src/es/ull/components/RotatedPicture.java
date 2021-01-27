package es.ull.components;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotatedPicture extends Picture {

    public static int size;

    public RotatedPicture(BufferedImage vIn) {
        super(vIn);
    }

    @Override
    protected void setAbsoluteHistogram() {
        for(int i = 0; i < picture.getWidth(); i++) {
            for(int j = 0; j < picture.getHeight(); j++) {
                if(picture.getRGB(i, j) != Color.BLUE.getRGB()) {
                    absoluteHistogram[getPixelValue(i, j)]++;
                }
            }
        }
    }

    @Override
    protected void toPAL() {
        for(int i = 0; i < picture.getWidth(); i++) {
            for(int j = 0; j < picture.getHeight(); j++) {
                Color color = new Color(picture.getRGB(i, j));
                if(color.getRGB() != Color.BLUE.getRGB()) {
                    int gris = Math.round((float) (0.222*color.getRed() + 0.707*color.getGreen() + 0.071*color.getBlue()));
                    picture.setRGB(i, j, new Color(gris, gris, gris).getRGB());
                }
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }
}
