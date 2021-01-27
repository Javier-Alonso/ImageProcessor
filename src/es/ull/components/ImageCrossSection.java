package es.ull.components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ImageCrossSection extends JFrame {

    private final JFreeChart chart;
    private final Picture image;
    private final XYDataset dataset;
    private final XYItemRenderer renderer;

    public ImageCrossSection(Picture image, int[] arrayData) {
        super("Perfil");
        this.image = image;
        this.dataset = createDataset(arrayData);
        chart = ChartFactory.createXYLineChart(
                "Perfil de la Imagen",
                "Pixels del Segmento",
                "Nivel de Gris",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        renderer = plot.getRenderer();

        Paint[] paintArray = {Color.RED, Color.MAGENTA, Color.BLUE, Color.GRAY};
        plot.setDrawingSupplier(new DefaultDrawingSupplier(
                paintArray,
                DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
    }

    private XYDataset createDataset(int[] arrayData) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Perfil");
        XYSeries derivativeSeries = new XYSeries("Derivada del Perfil");
        XYSeries smoothedSeries = new XYSeries("Perfil Suavizado");
        XYSeries derivativeSmoothedSeries = new XYSeries("Derivada del Perfil Suavizado");

        int[] derivativeData = getDerivative(arrayData);
        int[] smoothedData = getSmoothed(arrayData);
        int[] derivativeSmoothedData = getDerivative(smoothedData);

        for(int i = 0; i < arrayData.length; i++) {
            series.add(i, arrayData[i]);
            derivativeSeries.add(i, derivativeData[i]);
            smoothedSeries.add(i, smoothedData[i]);
            derivativeSmoothedSeries.add(i, derivativeSmoothedData[i]);
        }
        dataset.addSeries(series);
        dataset.addSeries(derivativeSeries);
        dataset.addSeries(smoothedSeries);
        dataset.addSeries(derivativeSmoothedSeries);

        return dataset;
    }

    private int[] getDerivative(int[] arrayData) {
        int[] derivativeData = new int[arrayData.length];
        int last = arrayData.length - 1;

        for(int i = 0; i < last; i++) {
            derivativeData[i] = arrayData[i + 1] - arrayData[i];
        }
        derivativeData[last] = arrayData[last] - arrayData[last - 1];

        return derivativeData;
    }

    private int[] getSmoothed(int[] arrayData) {
        int[] smoothedData = new int[arrayData.length];
        int last = arrayData.length - 1;

        smoothedData[0] = Math.round(((float) arrayData[0] + arrayData[1]) / 2f);
        for(int i = 1; i < last; i++) {
            smoothedData[i] = Math.round(((float) arrayData[i-1] + arrayData[i] + arrayData[i+1]) / 3f);
        }
        smoothedData[last] = Math.round(((float) arrayData[last] + arrayData[last - 1]) / 2f);

        return smoothedData;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.add(new JCheckBox(new VisibleAction(0)));
        panel.add(new JCheckBox(new VisibleAction(1)));
        panel.add(new JCheckBox(new VisibleAction(2)));
        panel.add(new JCheckBox(new VisibleAction(3)));
        return panel;
    }

    private class VisibleAction extends AbstractAction {

        private final int i;

        public VisibleAction(int i) {
            this.i = i;
            this.putValue(NAME, dataset.getSeriesKey(i));
            this.putValue(SELECTED_KEY, true);
            renderer.setSeriesVisible(i, true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            renderer.setSeriesVisible(i, !renderer.getSeriesVisible(i));
        }
    }

    public void display() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        this.setPreferredSize(new Dimension(image.getWidth()*2, image.getHeight()));
        this.add(chartPanel);
        this.add(createControlPanel(), BorderLayout.SOUTH);
        this.add(new JLabel(new ImageIcon(image.getData())), BorderLayout.EAST);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
