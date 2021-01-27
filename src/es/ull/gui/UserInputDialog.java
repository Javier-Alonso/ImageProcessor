package es.ull.gui;

import javax.swing.*;
import java.awt.event.*;

public class UserInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner inputSpinner;
    private JLabel label;

    private Double inputValue = null;

    public UserInputDialog(String labelText, JFrame owner) {
        super(owner, "Input", true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        label.setText(labelText);
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
        inputValue = (Double) inputSpinner.getValue();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public Double getInputValue() { return inputValue; }

    public boolean validInput() { return inputValue != null; }

    public void display() {
        this.pack();
        this.setVisible(true);
    }

    private void createUIComponents() {
        inputSpinner = new JSpinner(new SpinnerNumberModel(0.0,0.0,999.9,0.1));
        label = new JLabel();
    }
}
