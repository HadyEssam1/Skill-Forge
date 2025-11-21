package service;

import managers.CourseJsonManager;
import managers.UserJsonManager;
import models.Course;

import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private CourseJsonManager courseManager;
    private UserJsonManager userManager;
    public AdminService(CourseJsonManager courseManager, UserJsonManager userManager)
    {
        this.courseManager = courseManager;
        this.userManager = userManager;
    }
    public List<Course> reviewCreatedCourses(){
        List<Course> cm =courseManager.getAll();
        List<Course> pending = new ArrayList<>();
        for (Course c: cm )
        {
            if (c.getStatus()== Course.CourseStatus.PENDING)
            {
                pending.add(c);
            }
        }
       return  pending;
    }
    public void approveCreatedCourse(int courseId) throws Exception {
        Course c = courseManager.getById(courseId);
        c.setStatus(Course.CourseStatus.APPROVED);
        courseManager.save();
    }
    public void rejectCreatedCourse(int courseId) throws Exception {
        Course c = courseManager.getById(courseId);
        c.setStatus(Course.CourseStatus.REJECTED);
        courseManager.save();
    }
}
