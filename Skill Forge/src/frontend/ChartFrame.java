package frontend;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.*;

public class ChartFrame extends JFrame {

    public ChartFrame(String title, JFreeChart chart) {
        super(title);
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
