package es.ull.gui;

import javax.swing.*;
import java.awt.event.*;

public class LinealAdjustDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner brightnessSpinner;
    private JSpinner contrastSpinner;
    private JLabel brightnessLabel;
    private JLabel contrastLabel;

    private double brightness;
    private double contrast;

    public LinealAdjustDialog(JFrame owner) {
        super(owner, "Introduce los campos", true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

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

    public double getBrightness() { return brightness; }
    public double getContrast() { return contrast; }

    private void onOK() {
        brightness = (double) brightnessSpinner.getValue();
        contrast = (double) contrastSpinner.getValue();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        brightnessLabel = new JLabel("Brillo");
        contrastLabel = new JLabel("Contraste");

        brightnessSpinner = new JSpinner(new SpinnerNumberModel(0.0,0.0,500.0,0.1));
        contrastSpinner = new JSpinner(new SpinnerNumberModel(0.0,0.0,500.0,0.1));
    }

    public void display() {
        this.pack();
        this.setVisible(true);
    }
}
