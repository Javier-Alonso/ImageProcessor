package es.ull.components;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class Histogram extends JFrame {

    private final JFreeChart histogramChart;

    public Histogram(String title, int[] arrayData) {
        super("Histograma");
        histogramChart = ChartFactory.createXYLineChart(
                title,
                "Nivel de gris",
                "Número de píxeles",
                createDataset(arrayData),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    public Histogram(String title, Point[] arrayData) {
        super("Histograma");
        histogramChart = ChartFactory.createXYLineChart(
                title,
                "Valor X (vIn)",
                "Valor Y (vOut)",
                createDataset(arrayData),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    public void display() {
        ChartPanel chartPanel = new ChartPanel(histogramChart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(560,367));
        setContentPane(chartPanel);

        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    private XYDataset createDataset(int[] arrayData) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Nivel de gris");

        for(int i = 0; i < arrayData.length; i++) {
            series.add(i, arrayData[i]);
        }
        dataset.addSeries(series);

        return dataset;
    }

    private XYDataset createDataset(Point[] arrayData) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Tramo Lineal");

        for (Point arrayDatum : arrayData) {
            series.add(arrayDatum.x, arrayDatum.y);
        }
        dataset.addSeries(series);

        return dataset;
    }
}
