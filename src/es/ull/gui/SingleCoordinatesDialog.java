package es.ull.gui;

import javax.swing.*;
import java.awt.*;

public class SingleCoordinatesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JSpinner xSpinner;
    private JSpinner ySpinner;
    private JLabel xLabel;
    private JLabel yLabel;

    private Point coordinates = null;

    public SingleCoordinatesDialog(String title, JFrame owner) {
        super(owner, title, true);
        setContentPane(contentPane);
        setPreferredSize(new Dimension(200, 200));
        getRootPane().setDefaultButton(buttonOK);

        xLabel.setText("X");
        yLabel.setText("Y");

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK() {
        coordinates = new Point((Integer) xSpinner.getValue(), (Integer) ySpinner.getValue());
        dispose();
    }

    private void createUIComponents() {
        xLabel = new JLabel();
        yLabel = new JLabel();
        xSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        ySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
    }

    public Point getCoordinates() { return coordinates; }

    public boolean validInput() {
        return coordinates != null;
    }

    public void display() {
        this.pack();
        this.setVisible(true);
    }
}
