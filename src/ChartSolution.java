import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * Created by kolbusz on 30.05.15.
 */
public class ChartSolution extends JFrame {
    private JFreeChart chart;
    public ArrayList<Integer> bestFitness;
    public ArrayList<Integer> worstFitness;
    public ArrayList<Double> avFitness;
    public Integer maxDomain;

    public ChartSolution(String applicationTitle) {
        super(applicationTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }
    
    public void createChart(String chartTitle){
        this.chart = ChartFactory.createXYLineChart(chartTitle, "Iteracja", "Odległość", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        final XYItemRenderer renderer = plot.getRenderer();
        
        renderer.setSeriesStroke(0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
                
        );
        renderer.setSeriesStroke(2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );
        
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.MAGENTA);
        
        ChartPanel chartPanel = new ChartPanel( this.chart );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset(){
         
        XYSeries bestSeries = new XYSeries("Najlepsze dopasowanie");
        XYSeries worstSeries = new XYSeries("Najgorsze dopasowanie");
        XYSeries avSeries = new XYSeries("Średnie dopasowanie");
        final XYSeriesCollection dataset = new XYSeriesCollection();
        
        for(int i=0; i < Math.min(avFitness.size(),Math.min(bestFitness.size(), worstFitness.size())); i++){
            bestSeries.add(i,bestFitness.get(i));
            worstSeries.add(i,worstFitness.get(i));
            avSeries.add(i,avFitness.get(i));
        }
        
        dataset.addSeries(avSeries);
        dataset.addSeries(worstSeries);
        dataset.addSeries(bestSeries);

        return dataset;
    }


}
