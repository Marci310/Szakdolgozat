package Charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class StockChart extends ApplicationFrame {

    public StockChart(String title, XYSeries series) {
        super(title);
        JFreeChart chart = createChart(series);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
        setContentPane(chartPanel);
    }

    private JFreeChart createChart(XYSeries series) {
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Stock Price Chart", // chart title
                "Date", // x-axis label
                "Price", // y-axis label
                dataset, // dataset
                false, // legend
                true, // tooltips
                false // urls
        );

        XYPlot plot = chart.getXYPlot();
        DateAxis dateAxis = new DateAxis("Date");
        NumberAxis valueAxis = new NumberAxis("Price");

        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        plot.setDomainAxis(dateAxis);
        plot.setRangeAxis(valueAxis);
        plot.setRenderer(renderer);
        return chart;
    }
}

