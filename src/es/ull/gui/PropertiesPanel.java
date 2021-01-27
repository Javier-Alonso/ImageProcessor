package es.ull.gui;

import es.ull.components.Picture;

import javax.swing.*;
import java.util.Arrays;

public class PropertiesPanel extends JFrame {
    private JPanel panelProperties;
    private JLabel labelBrightness;
    private JLabel labelContrast;
    private JLabel labelEntropy;
    private JLabel labelSize;
    private JLabel labelRange;

    private JTextField textFieldBrightness;
    private JTextField textFieldContrast;
    private JTextField textFieldEntropy;
    private JTextField textFieldSize;
    private JTextField textFieldRange;
    private JLabel labelExtension;
    private JTextField textFieldExtension;

    public PropertiesPanel(Picture picture) {
        this.setTitle("Propiedades");
        setContentPane(panelProperties);

        textFieldBrightness.setText(Double.toString(picture.getBrightness()));
        textFieldContrast.setText(Double.toString(picture.getContrast()));
        textFieldEntropy.setText(Double.toString(picture.getEntropy()));
        textFieldSize.setText(picture.getHeight() + "x" + picture.getWidth());
        textFieldRange.setText(Arrays.toString(picture.getRange()));
        textFieldExtension.setText(picture.getExtension());

        labelSize.setText("Dimensiones");
        labelRange.setText("Rango");
        labelEntropy.setText("Entropía");
        labelContrast.setText("Contraste");
        labelBrightness.setText("Brillo");
        labelExtension.setText("Extensión");

        textFieldBrightness.setEditable(false);
        textFieldContrast.setEditable(false);
        textFieldEntropy.setEditable(false);
        textFieldRange.setEditable(false);
        textFieldSize.setEditable(false);
        textFieldExtension.setEditable(false);
    }

    public void display() {
        this.pack();
        this.setVisible(true);
    }

    private void createUIComponents() {
        labelBrightness = new JLabel();
        labelContrast = new JLabel();
        labelEntropy = new JLabel();
        labelRange = new JLabel();
        labelSize = new JLabel();
        labelExtension = new JLabel();

        textFieldBrightness = new JTextField();
        textFieldContrast = new JTextField();
        textFieldEntropy = new JTextField();
        textFieldRange = new JTextField();
        textFieldSize = new JTextField();
        textFieldExtension = new JTextField();
    }
}
