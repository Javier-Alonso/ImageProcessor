package es.ull.gui;

import javax.swing.*;
import java.awt.event.*;

public class ScaleInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField currentWidthTextField;
    private JTextField currentHeightTextField;

    private JLabel currentWidthLabel;
    private JLabel currentHeightLabel;

    private JLabel widthFactorLabel;
    private JLabel heightFactorLabel;

    private JTextField heightFactorTextField;
    private JTextField widthFactorTextField;

    private ButtonGroup buttonGroup;
    private JRadioButton neighborRadioButton;
    private JRadioButton bilinealRadioButton;

    private Float widthFactor = null;
    private Float heightFactor = null;
    private Integer interpolationID = null;

    public ScaleInputDialog(JFrame owner, int imageWidth, int imageHeight) {
        super(owner, "Nuevas Dimensiones", true);

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        currentHeightLabel.setText("Alto Original");
        currentWidthLabel.setText("Ancho Original");

        heightFactorLabel.setText("Nuevo Alto (%)");
        widthFactorLabel.setText("Nuevo Ancho (%)");

        currentWidthTextField.setText(Integer.toString(imageWidth));
        currentWidthTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentWidthTextField.setEditable(false);

        currentHeightTextField.setText(Integer.toString(imageHeight));
        currentHeightTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentHeightTextField.setEditable(false);

        widthFactorTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        heightFactorTextField.setHorizontalAlignment(SwingConstants.RIGHT);

        neighborRadioButton.setText("VMP");
        bilinealRadioButton.setText("Bilineal");
        neighborRadioButton.setSelected(true);
        buttonGroup.add(neighborRadioButton);
        buttonGroup.add(bilinealRadioButton);

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
        widthFactor = Float.parseFloat(widthFactorTextField.getText()) / 100;
        heightFactor = Float.parseFloat(heightFactorTextField.getText()) / 100;
        interpolationID = (neighborRadioButton.isSelected()? 1 : 2);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Float getWidthFactor() { return widthFactor; }
    public Float getHeightFactor() { return heightFactor; }
    public Integer getInterpolationID() { return  interpolationID; }

    public boolean validInput() {
        return widthFactor != null && heightFactor != null && interpolationID != null;
    }

    private void createUIComponents() {
        currentHeightLabel = new JLabel();
        currentWidthLabel = new JLabel();
        
        heightFactorLabel = new JLabel();
        widthFactorLabel = new JLabel();
        
        currentHeightTextField = new JTextField();
        currentWidthTextField = new JTextField();

        widthFactorTextField = new JTextField();
        heightFactorTextField = new JTextField();

        buttonGroup = new ButtonGroup();
        neighborRadioButton = new JRadioButton();
        bilinealRadioButton = new JRadioButton();
    }

    public void display() {
        this.pack();
        this.setVisible(true);
    }
}
