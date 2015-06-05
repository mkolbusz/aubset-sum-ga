import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * Created by kolbusz on 30.05.15.
 */
public class ChartSolution extends JFrame {
    private JFreeChart chart;

    public ChartSolution(String applicationTitle, String chartTitle, ArrayList<Integer> data) {
        super(applicationTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.chart = ChartFactory.createLineChart(chartTitle, "Iteracja", "Odległość", createDataset(data), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel( this.chart );
        setContentPane( chartPanel );
    }

    private CategoryDataset createDataset(ArrayList<Integer> data){

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        for(int i=0; i < data.size(); i++){
            dataset.addValue(data.get(i), "Odległość", String.valueOf(i));
        }

        return dataset;
    }


}
