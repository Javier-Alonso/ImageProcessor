package es.ull.operations;

import java.awt.image.BufferedImage;

public interface Interpolations {
    int interpolate(BufferedImage vIn, float x, float y);

    static int getNearestNeighbor(BufferedImage vIn, float x, float y) {

        int nearestX = (int) x;
        int nearestY = (int) y;

        return vIn.getRGB(nearestX, nearestY);
    }

    static int getBilinealAdjust(BufferedImage vIn, float gx, float gy) {
        int gxi = (int) gx;
        int gyi = (int) gy;
        int gxi1 = Math.min(gxi + 1, vIn.getWidth() - 1);
        int gyi1 = Math.min(gyi + 1, vIn.getHeight() - 1);
        int grey = 0;

        int c00 = vIn.getRGB(gxi, gyi);
        int c10 = vIn.getRGB(gxi1, gyi);
        int c01 = vIn.getRGB(gxi, gyi1);
        int c11 = vIn.getRGB(gxi1, gyi1);

        for (int i = 0; i <= 2; ++i) {
            float b00 = get(c00, i);
            float b10 = get(c10, i);
            float b01 = get(c01, i);
            float b11 = get(c11, i);
            int ble = ((int) blerp(b00, b10, b01, b11, gx - gxi, gy - gyi)) << (8 * i);
            grey = grey | ble;
        }
        return grey;
    }

    // Gets the 'n'th byte of a 4-byte integer
    private static int get(int self, int n) {
        return (self >> (n * 8)) & 0xFF;
    }

    // Calculates lineal interpolation
    private static float lerp(float s, float e, float t) {
        return s + (e - s) * t;
    }

    // Calculates bilineal interpolation
    private static float blerp(final Float c00, float c10, float c01, float c11, float tx, float ty) {
        return lerp(lerp(c00, c10, tx), lerp(c01, c11, tx), ty);
    }
}
