package es.ull.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ImageInternalFrame extends JInternalFrame implements MouseMotionListener {

    private final Picture image;

    private final JLabel mouseInfoLabel = new JLabel("Coordinates");

    public ImageInternalFrame(String title, Picture image) {
        super(title);
        this.image = image;

        JLabel labelImage = new JLabel(new ImageIcon(image.getData()));
        labelImage.addMouseMotionListener(this);

        setLayout(new BorderLayout());
        this.add(mouseInfoLabel, BorderLayout.PAGE_END);
        this.add(labelImage);

        this.setClosable(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        printMouseInfo(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        printMouseInfo(e.getX(), e.getY());
    }

    private void printMouseInfo(int x, int y) {
        mouseInfoLabel.setText("(" + x + ", " + y + "): " + image.getPixelValue(x, y));
    }

    public void display() {
        this.pack();
        this.setVisible(true);
    }

    public Picture getImage() {
        return image;
    }
}