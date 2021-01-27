package es.ull.components;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Picture {

    protected final BufferedImage picture;
    protected  final int[] absoluteHistogram = new int[256];
    protected final int[] cumulativeHistogram = new int[256];

    protected int[] range;

    protected double brightness;
    protected double contrast;
    protected double entropy;

    protected String extension;

    public Picture(BufferedImage img) {
        picture = img;
        this.toPAL();

        this.setAbsoluteHistogram();
        this.setCumulativeHistogram();

        this.setRange();

        this.setBrightness();
        this.setContrast();
        this.setEntropy();
    }

    public BufferedImage getData() { return picture; }

    public int getHeight() { return picture.getHeight(); }
    public int getWidth() { return picture.getWidth(); }
    public int getSize() {
        return picture.getHeight()* picture.getWidth();
    }

    public int[] getRange() {
        return range;
    }

    public double getBrightness() { return brightness; }
    public double getContrast() { return contrast; }
    public double getEntropy() { return entropy; }
    public String getExtension() { return extension; }

    public int[] getAbsoluteHistogram() { return absoluteHistogram; }
    public int[] getCumulativeHistogram() { return cumulativeHistogram; }

    public int getPixelValue(int i, int j) {
        return new Color(picture.getRGB(i,j)).getRed();
    }

    public void setPixelValue(int i, int j, int color) {
        picture.setRGB(i, j, color);
    }

    protected void setBrightness() {
        int acc = 0;
        for(int i = 0; i < absoluteHistogram.length; i++) {
            acc += absoluteHistogram[i]*i;
        }
        brightness = (double) acc/getSize() ;
    }

    protected void setContrast() {
        int acc = 0;
        for(int i = 0; i < absoluteHistogram.length; i++) {
            acc += absoluteHistogram[i]*Math.pow(i- getBrightness(), 2);
        }
        contrast = Math.sqrt((double) acc/getSize());
    }

    protected void setEntropy() {
        double acc = 0;

        for (int i : absoluteHistogram) {
            double p = (double) i / getSize();
            if(p != 0) {
                double log = Math.log(p) / Math.log(2);
                acc += p * log;
            }
        }
        entropy = acc*-1;
    }

    protected void setRange() {
        int minIndex = -1;
        for(int i = 0; i < absoluteHistogram.length; i++) {
            if(absoluteHistogram[i] != 0) {
                minIndex = i;
                break;
            }
        }

        int maxIndex = -1;
        for(int i = absoluteHistogram.length - 1; i >= 0; i--) {
            if(absoluteHistogram[i] != 0) {
                maxIndex = i;
                break;
            }
        }
        range = new int[] {minIndex, maxIndex};
    }

    public void setExtension(String extension) {
        this.extension = extension.substring(1);
    }

    protected void setAbsoluteHistogram() {
        for(int i = 0; i < picture.getWidth(); i++) {
            for(int j = 0; j < picture.getHeight(); j++) {
                absoluteHistogram[getPixelValue(i,j)]++;
            }
        }
    }

    protected void setCumulativeHistogram() {
        cumulativeHistogram[0] = absoluteHistogram[0];
        for(int i = 1; i < cumulativeHistogram.length; i++) {
            cumulativeHistogram[i] = cumulativeHistogram[i-1] + absoluteHistogram[i];
        }
    }

    protected void toPAL() {
        for(int i = 0; i < picture.getWidth(); i++) {
            for(int j = 0; j < picture.getHeight(); j++) {
                Color color = new Color(picture.getRGB(i, j));
                int gris = Math.round((float) (0.222*color.getRed() + 0.707*color.getGreen() + 0.071*color.getBlue()));
                picture.setRGB(i, j, new Color(gris, gris, gris).getRGB());
            }
        }
    }
}
