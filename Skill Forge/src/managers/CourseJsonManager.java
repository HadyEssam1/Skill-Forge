package managers;

import com.fasterxml.jackson.core.type.TypeReference;
import models.Course;
import java.util.List;

public class CourseJsonManager extends JsonManager<Course> {

    public CourseJsonManager() {
        super("data/courses.json", new TypeReference<List<Course>>() {});
    }

    public Course getById(int id) {
        for (Course c : data) {
            if (c.getCourseId() == id)
                return c;
        }
        return null;
    }

    public void update(Course updated) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCourseId() == updated.getCourseId()) {
                data.set(i, updated);
                save();
            }
        }
    }
}
