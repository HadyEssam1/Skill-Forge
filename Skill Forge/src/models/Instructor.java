package models;

import java.util.List;
import java.util.ArrayList;

public class Instructor extends User {
    private List<Integer> coursesTeaching; // course IDs


    public Instructor(int id, String username, String pass, String email) {
        super("instructor", id, username, pass, email);
        this.coursesTeaching = new ArrayList<>();

    }

    public List<Integer> getCoursesTeaching() {
        return new ArrayList<>(coursesTeaching);
    }
    

    public void assignCourse(int courseId) {
        if (!coursesTeaching.contains(courseId)) {
            coursesTeaching.add(courseId);

        }
    }

    public void leaveCourse(int courseId) {
        coursesTeaching.remove(Integer.valueOf(courseId));
    }
    public void createQuizForLesson(Lesson lesson,int quizId,int passMark){
        //validateQuizId(quizId);
        if(quizId<0){throw new IllegalArgumentException("quizId cannot be negative number");}
        //validatePassMark(passMark);
        if (passMark<0 ||passMark>100){throw new IllegalArgumentException("passMark must be between 0 and 100");}
        //validateLesson
        if(!coursesTeaching.contains(lesson.getCourseId())){throw new IllegalArgumentException("unauthorized attempt to create quiz!");}
        Quiz quiz=new Quiz( quizId,lesson.getLessonId(),passMark);
        lesson.setQuiz(quiz);
    }
    public void removeQuizFromLesson(Lesson lesson){
        if(!coursesTeaching.contains(lesson.getCourseId())){throw new IllegalArgumentException("unauthorized attempt to remove quiz!");}
        lesson.removeQuiz();
    }

}
