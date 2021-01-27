package es.ull.operations;

import es.ull.components.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LinealOperations extends Operations {

    private LinealOperations() {}

    public static Picture brightnessContrastAdjust(Picture vIn, double mu, double sigma) {
        BufferedImage vOut = copyImage(vIn.getData());

        double A = sigma / vIn.getContrast();
        double B = mu - A * vIn.getBrightness();

        int[] LUT = new int[256];

        for(int i = 0; i < LUT.length; i++) {
            LUT[i] = Math.round((float) clamp(A * i + B, 0, 255));
        }

        return setPictureWithLUT(vIn, vOut, LUT);
    }

    public static Picture sectionLinealTransformation(Picture vIn, Point[] sections) {
        BufferedImage vOut = copyImage(vIn.getData());

        int[] LUT = new int[256];
        // A = (YF - YI) / (XF - XI)
        // B = YI * A - XI
        for(int i = 0; i < sections.length - 1; i++) {
            int xI = sections[i].x;
            int yI = sections[i].y;

            int xF = sections[i+1].x;
            int yF = sections[i+1].y;

            float m = (float) (yF - yI) / (float) (xF - xI);
            float c = yI - m*xI;

            for(int x = xI; x < xF; x++) {
                int y = Math.round(m*x + c);
                LUT[x] = (int) clamp(y, 0, 255);
            }
        }

        return setPictureWithLUT(vIn, vOut, LUT);
    }
}
