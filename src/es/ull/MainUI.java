package es.ull;

import es.ull.components.*;
import es.ull.gui.*;
import es.ull.operations.GeometricOperations;
import es.ull.operations.Interpolations;
import es.ull.operations.LinealOperations;
import es.ull.operations.NonLinealOperations;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainUI extends JFrame {

    private JDesktopPane desktop;

    private JMenuBar mainToolBar;

    private JMenu menuFile;
    private JMenu menuView;
    private JMenu menuSelect;
    private JMenu menuHistogram;
    private JMenu menuOperation;
    private JMenu menuLinealOperation;
    private JMenu menuNoLinealOperation;
    private JMenu menuGeometricOperation;
    private JMenu menuRotate;

    private JMenuItem itemOpen;
    private JMenuItem itemSave;
    private JMenuItem itemExit;

    private JMenuItem itemProperties;
    private JMenuItem itemAbsoluteHistogram;
    private JMenuItem itemCumulativeHistogram;

    private JMenuItem itemRegionOfInterest;

    private JMenuItem itemLinealAdjust;
    private JMenuItem itemLinealSectionTransformation;

    private JMenuItem itemEqualizeHistogram;
    private JMenuItem itemSpecificationHistogram;
    private JMenuItem itemGammaCorrection;
    private JMenuItem itemImageDifference;
    private JMenuItem itemMapDifference;
    private JMenuItem itemImageCrossSection;
    private JMenuItem itemDigitize;

    private JMenuItem itemFlipVertical;
    private JMenuItem itemFlipHorizontal;
    private JMenuItem itemTranspose;
    private JMenuItem itemScale;

    private JMenuItem itemRotate90;
    private JMenuItem itemRotate180;
    private JMenuItem itemRotate270;
    private JMenuItem itemRotateCustom;

    public MainUI(String title) {
        super(title);

        createUIComponents();

        this.setContentPane(desktop);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        setLayout(null);
        setJMenuBar(mainToolBar);

        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemExit);

        menuHistogram.add(itemAbsoluteHistogram);
        menuHistogram.add(itemCumulativeHistogram);

        menuOperation.add(menuLinealOperation);
        menuOperation.add(menuNoLinealOperation);
        menuOperation.add(menuGeometricOperation);

        menuView.add(itemLinealAdjust);
        menuView.add(itemProperties);
        menuView.add(menuHistogram);

        menuSelect.add(itemRegionOfInterest);

        menuLinealOperation.add(itemLinealAdjust);
        menuLinealOperation.add(itemLinealSectionTransformation);

        menuNoLinealOperation.add(itemEqualizeHistogram);
        menuNoLinealOperation.add(itemSpecificationHistogram);
        menuNoLinealOperation.add(itemGammaCorrection);
        menuNoLinealOperation.add(itemImageDifference);
        menuNoLinealOperation.add(itemMapDifference);
        menuNoLinealOperation.add(itemImageCrossSection);
        menuNoLinealOperation.add(itemDigitize);

        menuRotate.add(itemRotate90);
        menuRotate.add(itemRotate180);
        menuRotate.add(itemRotate270);
        menuRotate.add(itemRotateCustom);

        menuGeometricOperation.add(itemFlipVertical);
        menuGeometricOperation.add(itemFlipHorizontal);
        menuGeometricOperation.add(itemTranspose);
        menuGeometricOperation.add(itemScale);
        menuGeometricOperation.add(menuRotate);

        mainToolBar.add(menuFile);
        mainToolBar.add(menuView);
        mainToolBar.add(menuSelect);
        mainToolBar.add(menuOperation);

        createShortcuts();
        createListeners();
    }

    private void createUIComponents() {
        desktop = new JDesktopPane();

        mainToolBar = new JMenuBar();

        menuFile = new JMenu("Archivo");
        menuView = new JMenu("Ver");
        menuSelect = new JMenu("Seleccionar");
        menuOperation = new JMenu("Operaciones");

        itemOpen = new JMenuItem("Abrir", new ImageIcon("resources/open.png"));
        itemSave = new JMenuItem("Guardar", new ImageIcon("resources/save.png"));
        itemExit = new JMenuItem("Salir", new ImageIcon("resources/exit.png"));

        menuHistogram = new JMenu("Histograma");
        menuRotate = new JMenu("Rotar");
        menuLinealOperation = new JMenu("Operaciones Lineales");
        menuNoLinealOperation = new JMenu("Operaciones no Lineales");
        menuGeometricOperation = new JMenu("Operaciones Geométricas");

        itemProperties = new JMenuItem("Propiedades");

        itemAbsoluteHistogram = new JMenuItem("Absoluto");
        itemCumulativeHistogram = new JMenuItem("Acumulativo");

        itemRegionOfInterest = new JMenuItem("Región de Interés");

        itemLinealAdjust = new JMenuItem("Ajuste Lineal de brillo y contraste");
        itemLinealSectionTransformation = new JMenuItem("Ajuste lineal por tramos");

        itemEqualizeHistogram = new JMenuItem("Ecualizar histograma");
        itemSpecificationHistogram = new JMenuItem("Especificación del histograma");
        itemGammaCorrection = new JMenuItem("Corrección Gamma");
        itemImageDifference = new JMenuItem("Imagen Diferencia");
        itemMapDifference = new JMenuItem("Mapa de Cambios");
        itemImageCrossSection = new JMenuItem("Perfil");
        itemDigitize = new JMenuItem("Digitalizar");

        itemFlipHorizontal = new JMenuItem("Voltear Horizontal");
        itemFlipVertical = new JMenuItem("Voltear Vertical");
        itemTranspose = new JMenuItem("Traspuesta");
        itemScale = new JMenuItem("Escalar");

        itemRotate90 = new JMenuItem("Rotar 90º");
        itemRotate180 = new JMenuItem("Rotar 180º");
        itemRotate270 = new JMenuItem("Rotar 270º");
        itemRotateCustom = new JMenuItem("Rotar...");
    }

    private void createShortcuts() {
        itemOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemExit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemProperties.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        itemAbsoluteHistogram.setAccelerator(KeyStroke.getKeyStroke('H', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        itemCumulativeHistogram.setAccelerator(KeyStroke.getKeyStroke('H', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | KeyEvent.SHIFT_DOWN_MASK));

        itemRotateCustom.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        itemScale.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
    }

    private void createListeners() {
        itemOpen.addActionListener(e -> {
            JInternalFrame parentFrame = new JInternalFrame();
            JFileChooser fileChooser = createFileChooser("Abrir");

            if(fileChooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                File rawFile = new File(fileChooser.getSelectedFile().toString());
                try {
                    Picture newPicture = new Picture(ImageIO.read(rawFile));
                    newPicture.setExtension(getFileExtension(rawFile));
                    createImageFrame(rawFile.getName(), newPicture);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        itemSave.addActionListener(e -> {
            JInternalFrame parentFrame = new JInternalFrame();
            JFileChooser fileChooser = createFileChooser("Guardar");

            if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                try {
                    String ext = fileChooser.getFileFilter().getDescription();
                    File file = new File(fileChooser.getSelectedFile() + ext);

                    ImageIO.write(getActiveImage().getData(), ext.substring(1), file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        itemExit.addActionListener(e -> dispose());

        itemProperties.addActionListener(e -> {
            PropertiesPanel propertiesPanel = new PropertiesPanel(getActiveImage());
            propertiesPanel.display();
        });

        itemAbsoluteHistogram.addActionListener(e -> {
            Histogram chart = new Histogram("Absoluto", getActiveImage().getAbsoluteHistogram());
            chart.display();
        });

        itemCumulativeHistogram.addActionListener(e -> {
            Histogram chart = new Histogram("Acumulado", getActiveImage().getCumulativeHistogram());
            chart.display();
        });

        itemRegionOfInterest.addActionListener(e -> {
            CoordinatesInputDialog.xMax = getActiveImage().getWidth();
            CoordinatesInputDialog.yMax = getActiveImage().getHeight();
            CoordinatesInputDialog dialog = new CoordinatesInputDialog(this);
            dialog.display();

            if(dialog.validInput()) {
                Point pI = new Point(dialog.getInitialCoordinates());
                Point pF = new Point(dialog.getFinalCoordinates());

                Point upperLeft = new Point(Math.min(pI.x, pF.x), Math.min(pI.y, pF.y));
                int w = Math.abs(pF.x - pI.x);
                int h = Math.abs(pF.y - pI.y);

                BufferedImage roi = getActiveImage().getData().getSubimage(upperLeft.x, upperLeft.y, w, h);

                createImageFrame("Región de Interés", new Picture(roi));
            }
        });

        itemLinealAdjust.addActionListener(e -> {
            LinealAdjustDialog dialog = new LinealAdjustDialog(this);
            dialog.display();

            double mu = dialog.getBrightness();
            double sigma = dialog.getContrast();

            Picture result = LinealOperations.brightnessContrastAdjust(getActiveImage(), mu, sigma);

            createImageFrame("Imagen Ajustada", result);
        });

        itemLinealSectionTransformation.addActionListener(e -> {
            UserInputDialog dialog = new UserInputDialog("Tramos", this);
            dialog.display();
            Double nSections = dialog.getInputValue();
            Point[] sections = new Point[nSections.intValue() + 1];

            for(int i = 0; i < sections.length; i++) {
                SingleCoordinatesDialog input = new SingleCoordinatesDialog("Punto " + i, this);
                input.display();
                if(input.validInput()) {
                    sections[i] = input.getCoordinates();
                }
            }

            Histogram chart = new Histogram("Tramo Lineal", sections);
            chart.display();
            Picture result = LinealOperations.sectionLinealTransformation(getActiveImage(), sections);
            createImageFrame("Imagen Ajustada", result);
        });

        itemEqualizeHistogram.addActionListener(e -> {
            Picture result = NonLinealOperations.histogramEqualization(getActiveImage());

            createImageFrame("Imagen Ecualizada", result);
        });

        itemSpecificationHistogram.addActionListener(e -> {
            JInternalFrame parentFrame = new JInternalFrame();
            JFileChooser fileChooser = createFileChooser("Abrir");

            if(fileChooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                File rawFile = new File(fileChooser.getSelectedFile().toString());
                try {
                    Picture referenceImage = new Picture(ImageIO.read(rawFile));
                    Picture result = NonLinealOperations.histogramSpecification(getActiveImage(), referenceImage);

                    createImageFrame("Imagen Referencia", referenceImage);
                    createImageFrame("Imagen con Histograma Especificado", result);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        itemGammaCorrection.addActionListener(e -> {
            UserInputDialog dialog = new UserInputDialog("Valor Gamma", this);
            dialog.display();

            if(dialog.validInput()) {
                double gamma = dialog.getInputValue();

                Picture result = NonLinealOperations.gammaCorrection(getActiveImage(), gamma);
                createImageFrame("Corrección Gamma", result);
            }
        });

        itemImageDifference.addActionListener(e -> {
            JInternalFrame parentFrame = new JInternalFrame();
            JFileChooser fileChooser = createFileChooser("Abrir");

            if(fileChooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                File rawFile = new File(fileChooser.getSelectedFile().toString());
                try {
                    Picture referenceImage = new Picture(ImageIO.read(rawFile));
                    Picture result = NonLinealOperations.imageDifference(getActiveImage(), referenceImage);

                    createImageFrame("Imagen Referencia", referenceImage);
                    createImageFrame("Imagen Diferencia", result);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        itemMapDifference.addActionListener(e -> {
            UserInputDialog dialog = new UserInputDialog("Valor Umbral", this);
            dialog.display();

            if(dialog.validInput()) {
                Picture result = NonLinealOperations.differenceMap(getActiveImage(), dialog.getInputValue());
                createImageFrame("Mapa de Cambios", result);
            }
        });

        itemImageCrossSection.addActionListener(e -> {
            CoordinatesInputDialog.xMax = getActiveImage().getWidth();
            CoordinatesInputDialog.yMax = getActiveImage().getHeight();
            CoordinatesInputDialog dialog = new CoordinatesInputDialog(this);
            dialog.display();

            if(dialog.validInput()) {
                Point pI = new Point(dialog.getInitialCoordinates());
                Point pF = new Point(dialog.getFinalCoordinates());
                ImageCrossSection chart = NonLinealOperations.imageCrossSection(getActiveImage(), pI, pF);
                chart.display();
            }
        });

        itemDigitize.addActionListener(e -> {
            UserInputDialog dialog = new UserInputDialog("Muestras", this);
            dialog.display();

            int samples = dialog.getInputValue().intValue();

            dialog = new UserInputDialog("Bits", this);
            dialog.display();

            int bits = dialog.getInputValue().intValue();

            Picture result = NonLinealOperations.digitize(getActiveImage(), samples, bits);
            createImageFrame("Imagen Digitalizada", result);
        });

        itemFlipVertical.addActionListener(e -> {
            Picture result = GeometricOperations.flipVertically(getActiveImage());
            createImageFrame("Imagen Volteada", result);
        });

        itemFlipHorizontal.addActionListener(e -> {
            Picture result = GeometricOperations.flipHorizontally(getActiveImage());
            createImageFrame("Imagen Volteada", result);
        });

        itemTranspose.addActionListener(e -> {
            Picture result = GeometricOperations.transpose(getActiveImage());
            createImageFrame("Imagen Traspuesta", result);
        });

        itemRotate90.addActionListener(e -> {
            Picture result = GeometricOperations.rotateClockwise90(getActiveImage());
            createImageFrame("Imagen Rotada", result);
        });

        itemRotate180.addActionListener(e -> {
            Picture result = getActiveImage();
            for(int i = 0; i < 2; i++)
                result = GeometricOperations.rotateClockwise90(result);
            createImageFrame("Imagen Rotada", result);
        });

        itemRotate270.addActionListener(e -> {
            Picture result = getActiveImage();
            for(int i = 0; i < 3; i++)
                result = GeometricOperations.rotateClockwise90(result);
            createImageFrame("Imagen Rotada", result);
        });

        itemRotateCustom.addActionListener(e -> {
            RotationInputDialog dialog = new RotationInputDialog(this);
            dialog.display();

            if(dialog.validInput()) {
                Picture result;
                double angle = dialog.getAngle();

                if(dialog.getInterpolationID() == 1) {
                    result = GeometricOperations.rotate(getActiveImage(), angle, Interpolations::getNearestNeighbor);
                }
                else {
                    result = GeometricOperations.rotate(getActiveImage(), angle, Interpolations::getBilinealAdjust);
                }
                createImageFrame("Imagen Rotada", result);
            }
        });

        itemScale.addActionListener(e -> {
            ScaleInputDialog dialog = new ScaleInputDialog(this, getActiveImage().getWidth(), getActiveImage().getHeight());
            dialog.display();

            if(dialog.validInput()) {
                float scaleX = dialog.getWidthFactor();
                float scaleY = dialog.getHeightFactor();
                Picture result;

                if(dialog.getInterpolationID() == 1) {
                    result = GeometricOperations.scale(getActiveImage(), scaleX, scaleY, Interpolations::getNearestNeighbor);
                }
                else {
                    result = GeometricOperations.scale(getActiveImage(), scaleX, scaleY, Interpolations::getBilinealAdjust);
                }
                createImageFrame("Imagen Escalada", result);
            }
        });
    }

    private void createImageFrame(String title, Picture image) {
        ImageInternalFrame newImageFrame = new ImageInternalFrame(title, image);
        newImageFrame.display();
        desktop.add(newImageFrame);
        this.repaint();
    }

    private ImageInternalFrame getActiveFrame() {
        return (ImageInternalFrame) desktop.getSelectedFrame();
    }

    private Picture getActiveImage() {
        return getActiveFrame().getImage();
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    private JFileChooser createFileChooser(String title) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(title);
        fileChooser.addChoosableFileFilter(new PNGSaveFilter());
        fileChooser.addChoosableFileFilter(new JPGSaveFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        return fileChooser;
    }

    public static void main(String[] args) {
        JFrame mainFrame = new MainUI("My Photoshop");

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //fullscreen
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
    }
}