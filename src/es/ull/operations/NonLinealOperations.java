package es.ull.operations;

import es.ull.components.ImageCrossSection;
import es.ull.components.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NonLinealOperations extends Operations {

    private NonLinealOperations() {}

    public static Picture histogramEqualization(Picture vIn) {
        BufferedImage vOut = copyImage(vIn.getData());
        float M = 256;

        int[] LUT = new int[256];

        for(int i = 0; i < LUT.length; i++) {
            int round = Math.round(M * (float) vIn.getCumulativeHistogram()[i] / (float) vIn.getSize()) - 1;
            LUT[i] = Math.round((float) (Math.max(0, clamp(round, 0, 255))));
        }

        return setPictureWithLUT(vIn, vOut, LUT);
    }

    public static Picture histogramSpecification(Picture vIn, Picture imgReference) {
        BufferedImage vOut = copyImage(vIn.getData());
        float M = 256;

        int[] LUT = new int[256];

        int[] imgNormalized= new int[256];
        int[] imgReferenceNormalized= new int[256];

        for(int i = 0; i < imgNormalized.length; i++) {
            imgNormalized[i] = Math.round(((float) vIn.getCumulativeHistogram()[i] / (float) vIn.getSize()) * (M - 1));
        }

        for(int i = 0; i < imgReferenceNormalized.length; i++) {
            imgReferenceNormalized[i] = Math.round(((float) imgReference.getCumulativeHistogram()[i] / (float) imgReference.getSize()) * (M - 1));
        }

        for(int i = 0; i < LUT.length; i++) {
            int j = LUT.length;
            do {
                LUT[i] = j;
                j--;
            } while(j >= 0 && imgNormalized[i] <= imgReferenceNormalized[j]);
        }

        return setPictureWithLUT(vIn, vOut, LUT);
    }

    public static Picture gammaCorrection(Picture vIn, double gamma) {
        BufferedImage vOut = copyImage(vIn.getData());

        int[] LUT = new int[256];

        for(int i = 0; i < LUT.length; i++) {
            LUT[i] = Math.round((float) (255 * Math.pow((double) i / (double) 255, gamma)));
        }

        return setPictureWithLUT(vIn, vOut, LUT);
    }

    public static Picture imageDifference(Picture img1, Picture img2) {
        BufferedImage difference = copyImage(img1.getData());

        for (int i = 0; i < img1.getWidth(); i++) {
            for (int j = 0; j < img1.getHeight(); j++) {
                int grey = Math.abs(img2.getPixelValue(i, j) - img1.getPixelValue(i, j));
                difference.setRGB(i, j, new Color(grey, grey, grey).getRGB());
            }
        }
        return new Picture(difference);
    }

    public static Picture differenceMap(Picture differenceImage, double threshold) {
        Picture differenceMap = new Picture(copyImage(differenceImage.getData()));

        for(int i = 0; i < differenceImage.getWidth(); i++) {
            for(int j = 0; j < differenceImage.getHeight(); j++) {
                if(differenceImage.getPixelValue(i, j) > threshold) {
                    differenceMap.setPixelValue(i, j, Color.RED.getRGB());
                }
            }
        }
        return differenceMap;
    }

    public static ImageCrossSection imageCrossSection(Picture vIn, Point pI, Point pF) {
        Picture vOut = new Picture(copyImage(vIn.getData()));
        int xI = pI.x;
        int yI = pI.y;

        int xF = pF.x;
        int yF = pF.y;

        // Calcular ecuación de la recta que une pI y pF
        float m = (float) (yF - yI) / (float) (xF - xI);
        float c = yI - m*xI;

        // Calcular variación para decidir variable dependiente
        int xDelta = Math.abs(xF - xI);
        int yDelta = Math.abs(yF - yI);
        int[] coordinatesTable;

        // Ver pixeles que conforman la recta
        if(xDelta >= yDelta) {
            // y = mx + c
            coordinatesTable = new int[xDelta + 1];

            for(int x = Math.min(xI, xF), j = 0; x <= Math.max(xI, xF); x++, j++) {
                int y = Math.round(m*x + c);                        // Calculate point
                coordinatesTable[j] = vIn.getPixelValue(x, y);      // Get pixel color
                vOut.setPixelValue(x, y, Color.RED.getRGB());       // Paint line
            }
        }
        else {
            // x = (y - c) / m
            coordinatesTable = new int[yDelta + 1];

            for(int y = Math.min(yI, yF), j = 0; y <= Math.max(yI, yF); y++, j++) {
                int x = Math.round((y - c) / m);                    // Calculate point
                coordinatesTable[j] = vIn.getPixelValue(x, y);      // Get pixel color
                vOut.setPixelValue(x, y, Color.RED.getRGB());       // Paint line
            }
        }

        // Generar grafica: x(pixels_segmento) / y(nivel_gris)
        return new ImageCrossSection(vOut, coordinatesTable);
    }

    public static Picture digitize(Picture vIn, int samples, int bits) {
        BufferedImage vOut = copyImage(vIn.getData());
        int[] LUT = new int[256];

        // Quantifying
        for(int i = 0; i < LUT.length; i++) {
            LUT[i] = i*(1<<bits)/255;
            LUT[i] *= ((float) 255 / ((1<<bits)-1));
        }

        // Sampling
        for(int x = 0; x < vOut.getWidth() - samples; x += samples) {
            for(int y = 0; y < vOut.getHeight() - samples; y += samples) {
                colorSample(vOut, new Point(x, y), samples, LUT[vIn.getPixelValue(x, y)]);
            }
        }

        return new Picture(vOut);
    }

    private static void colorSample(BufferedImage vOut, Point upperLeft, int samples, int grey) {
        for(int x = upperLeft.x; x < upperLeft.x + samples; x++) {
            for(int y = upperLeft.y; y < upperLeft.y + samples; y++) {
                vOut.setRGB(x, y, new Color(grey, grey, grey).getRGB());
            }
        }
    }
}
