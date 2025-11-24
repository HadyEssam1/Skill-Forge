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

    // 👉 NEW METHOD (Line Chart)
    public static JFreeChart createCompletionChart(String courseName, double completionPercentage) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(completionPercentage, "Completion %", courseName);

        return ChartFactory.createLineChart(
                "Completion Percentage for " + courseName,
                "Course",
                "Completion (%)",
                dataset
        );
    }
    public static JFreeChart createStudentProgressChart(String courseName, Map<String, Double> studentProgress) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String studentName : studentProgress.keySet()) {
            dataset.addValue(studentProgress.get(studentName), "Progress %", studentName);
        }

        return ChartFactory.createLineChart(
                "Student Progress in " + courseName,
                "Student",
                "Progress (%)",
                dataset
        );
    }

}
