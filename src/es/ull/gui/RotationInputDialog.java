package es.ull.gui;

import javax.swing.*;
import java.awt.event.*;

public class RotationInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField rotationTextField;

    private ButtonGroup buttonGroup;
    private JRadioButton vmpButton;
    private JRadioButton bilinealButton;

    private Double angle = null;
    private Integer interpolationID = null;

    public RotationInputDialog(JFrame owner) {
        super(owner, "Nueva Rotación", true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        rotationTextField.setHorizontalAlignment(SwingConstants.RIGHT);

        vmpButton.setText("VMP");
        bilinealButton.setText("Bilineal");
        vmpButton.setSelected(true);
        buttonGroup.add(vmpButton);
        buttonGroup.add(bilinealButton);

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
        angle = Double.parseDouble(rotationTextField.getText());
        interpolationID = (vmpButton.isSelected()? 1 : 2);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        rotationTextField = new JTextField();
        buttonGroup = new ButtonGroup();
        vmpButton = new JRadioButton();
        bilinealButton = new JRadioButton();
    }

    public Double getAngle() { return angle; }

    public Integer getInterpolationID() { return interpolationID; }

    public boolean validInput() { return angle != null && interpolationID != null; }

    public void display() {
        this.pack();
        this.setVisible(true);
    }
}
