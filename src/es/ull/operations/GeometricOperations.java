package es.ull.operations;

import es.ull.components.Picture;
import es.ull.components.RotatedPicture;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class GeometricOperations extends Operations {

    private GeometricOperations() {}

    public static Picture flipVertically(Picture vIn) {
        BufferedImage vOut = copyImage(vIn.getData());

        for(int y = vIn.getHeight()-1; y > 0; y--) {
            for(int x = 0; x < vIn.getWidth(); x++) {
                int grey = vIn.getData().getRGB(x, y);
                vOut.setRGB(x, vIn.getHeight()-y, grey);
            }
        }
        return new Picture(vOut);
    }

    public static Picture flipHorizontally(Picture vIn) {
        BufferedImage vOut = copyImage(vIn.getData());

        for(int x = vIn.getWidth()-1; x > 0; x--) {
            for(int y = 0; y < vIn.getHeight(); y++) {
                int grey = vIn.getData().getRGB(x, y);
                vOut.setRGB(vIn.getWidth()-x, y, grey);
            }
        }
        return new Picture(vOut);
    }

    public static Picture transpose(Picture vIn) {
        BufferedImage vOut = new BufferedImage(vIn.getHeight(), vIn.getWidth(), 1);

        for (int x = 0; x < vIn.getWidth(); x++){
            for (int y = 0; y < vIn.getHeight(); y++) {
                int grey = vIn.getData().getRGB(x, y);
                vOut.setRGB(y, x, grey);
            }
        }
        return new Picture(vOut);
    }

    public static Picture rotateClockwise90(Picture vIn) {
        BufferedImage vOut = new BufferedImage(vIn.getHeight(), vIn.getWidth(), 1);

        for(int y = 0; y < vIn.getHeight(); y++) {
            for(int x = 0; x < vIn.getWidth(); x++) {
                int grey = vIn.getData().getRGB(x, y);
                vOut.setRGB(vIn.getHeight() - y - 1, x, grey);
            }
        }
        return new Picture(vOut);
    }

    public static Picture rotate(Picture vIn, double angle, Interpolations method) {
        // Definimos las 4 esquinas de la imagen conocida
        Point2D upperLeft = new Point2D.Double(0, 0);
        Point2D upperRight = new Point2D.Double(vIn.getWidth() - 1, 0);
        Point2D bottomLeft = new Point2D.Double(0, vIn.getHeight() - 1);
        Point2D bottomRight = new Point2D.Double(vIn.getWidth() - 1, vIn.getHeight() - 1);

        // Aplicamos transformación directa para obtener las esquinas del rectángulo mapeado
        // x' = xcosθ – ysenθ
        // y’ = xsenθ + ycosθ
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));

        upperRight.setLocation(upperRight.getX()*cos, upperRight.getX()*sin);
        bottomLeft.setLocation(-(bottomLeft.getY()*sin), bottomLeft.getY()*cos);
        bottomRight.setLocation(bottomRight.getX()*cos - bottomRight.getY()*sin, bottomRight.getX()*sin + bottomRight.getY()*cos);

        // Definimos el tamaño de la ventana de visualización de la imagen rotada
        double maxX = Math.max(Math.max(upperLeft.getX(), upperRight.getX()), Math.max(bottomLeft.getX(), bottomRight.getX()));
        double minX = Math.min(Math.min(upperLeft.getX(), upperRight.getX()), Math.min(bottomLeft.getX(), bottomRight.getX()));

        double maxY = Math.max(Math.max(upperLeft.getY(), upperRight.getY()), Math.max(bottomLeft.getY(), bottomRight.getY()));
        double minY = Math.min(Math.min(upperLeft.getY(), upperRight.getY()), Math.min(bottomLeft.getY(), bottomRight.getY()));

        // Calculamos el tamaño aproximando por exceso
        int newWidth = (int) Math.ceil(Math.abs(maxX - minX));
        int newHeight = (int) Math.ceil(Math.abs((maxY - minY)));

        // Definimos la ventana de visualización
        BufferedImage vOut = new BufferedImage(newWidth, newHeight, 1);

        // Mapear pixeles de la imagen original usando transformación inversa
        // x = x’cos(-θ) – y’sen(-θ) = x’cosθ + y’senθ
        // y = x’sen(-θ) + y’cos(-θ) = -x’senθ + y’cosθ

        for(int i = 0; i < newWidth; i++) {
            for(int j = 0; j < newHeight; j++) {
                // Convertir pareja de índices usando traslación
                // Coordenadas de los pixels de la imagen rotada respecto del sistema de referencia de la imagen original
                double X = i + minX;
                double Y = j + minY;
                // Coordenadas de los pixels de la imagen rotada después de “mapearlos” sobre la original
                float x = (float) (X*cos + Y*sin);
                float y = (float) (-X*sin + Y*cos);
                // Asignar nivel de gris
                if(x < 0 || x >= vIn.getWidth() || y < 0 || y >= vIn.getHeight()) {
                    vOut.setRGB(i, j, Color.BLUE.getRGB());
                }
                else {
                    int grey = method.interpolate(vIn.getData(), x, y);
                    vOut.setRGB(i, j, grey);
                }
            }
        }
        RotatedPicture.size = vIn.getSize();
        return new RotatedPicture(vOut);
    }

    public static Picture scale(Picture vIn, float scaleX, float scaleY, Interpolations method) {
        int width = (int) (vIn.getWidth() * scaleX);
        int height = (int) (vIn.getHeight() * scaleY);

        BufferedImage vOut = new BufferedImage(width, height, 1);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                float X = x / scaleX;
                float Y = y / scaleY;

                int grey = method.interpolate(vIn.getData(), X, Y);

                vOut.setRGB(x, y, grey);
            }
        }
        return new Picture(vOut);
    }
}
