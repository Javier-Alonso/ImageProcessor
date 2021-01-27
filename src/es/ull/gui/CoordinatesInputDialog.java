package es.ull.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CoordinatesInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel finalPointLabel;
    private JLabel firstPointLabel;
    private JSpinner xFirstSpinner;
    private JSpinner xFinalSpinner;
    private JSpinner yFirstSpinner;
    private JSpinner yFinalSpinner;

    public static int xMax;
    public static int yMax;

    private Point pI = null;
    private Point pF = null;

    public CoordinatesInputDialog(JFrame owner) {
        super(owner, "Introduce las Coordenadas", true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        firstPointLabel.setText("Punto Inicial");
        finalPointLabel.setText("Punto Final");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        pI = new Point((Integer) xFirstSpinner.getValue(), (Integer) yFirstSpinner.getValue());
        pF = new Point((Integer) xFinalSpinner.getValue(), (Integer) yFinalSpinner.getValue());
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        firstPointLabel = new JLabel();
        finalPointLabel = new JLabel();

        xFirstSpinner = new JSpinner(new SpinnerNumberModel(0, 0, xMax, 1));
        xFinalSpinner = new JSpinner(new SpinnerNumberModel(0, 0, xMax, 1));
        yFirstSpinner = new JSpinner(new SpinnerNumberModel(0, 0, yMax, 1));
        yFinalSpinner = new JSpinner(new SpinnerNumberModel(0, 0, yMax, 1));
    }

    public Point getInitialCoordinates() {
        return pI;
    }
    public Point getFinalCoordinates() { return pF; }
    
    public boolean validInput() {
        return pI != null && pF != null;
    }

    public void display() {
        this.pack();
        this.setVisible(true);
    }
}
