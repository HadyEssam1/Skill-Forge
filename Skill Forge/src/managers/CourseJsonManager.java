package managers;
import models.Course;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

    public class CourseJsonManager extends JsonManager {
        private List<Course> courses;
        private static final Type COURSE_LIST_TYPE = new TypeToken<List<Course>>() {
        }.getType();

        public CourseJsonManager(String filePath) {
            super(filePath);
            loadFromJson();
        }

        @Override
        public ArrayList<Course> loadFromJson() {
            this.courses = readFile(COURSE_LIST_TYPE);
            if (this.courses == null) {
                this.courses = new ArrayList<>();
            }
            return null;
        }

        @Override
        public void saveToJson() {
            writeFile(this.courses);
        }

        public Course findById(int courseId) {
            for (Course c : this.courses) {
                if (c.getCourseId() == courseId ) {
                    return c;
                }
            }
            return null;
        }

        public void addCourse(Course course) {
            this.courses.add(course);
            saveToJson();
        }

        public boolean deleteCourse(int courseId) {
            boolean deleted = this.courses.removeIf(c -> c.getCourseId().equals(courseId));
            if (deleted) {
                saveToJson();
            }

            return deleted;
        }

        public ArrayList<Course> getAll() {
            return new ArrayList<>(courses); // return copy for safety
        }
    }
