package frontend;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Map;

public class ChartGenerator {

    public static JFreeChart createLessonAverageChart(String courseName, Map<String, Double> lessonScores) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String lessonName : lessonScores.keySet()) {
            dataset.setValue(lessonScores.get(lessonName), "Average Score", lessonName);
        }
        return ChartFactory.createBarChart(
                "Quiz Averages for " + courseName,
                "Lessons",
                "Average Score",
                dataset
        );
    }
}
