package managers;

import com.fasterxml.jackson.core.type.TypeReference;
import models.Course;
import java.util.List;

public class CourseJsonManager extends JsonManager<Course> {

    public CourseJsonManager() throws Exception {
        super("data/courses.json", new TypeReference<List<Course>>() {});
    }

    public Course getById(int id) {
        for (Course c : data) {
            if (c.getCourseId() == id)
                return c;
        }
        return null;
    }
}
