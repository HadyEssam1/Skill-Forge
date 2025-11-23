package models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Certificate {
    private int certificateID;
    private LocalDateTime issueDate;
    private int studentID;
    private int courseID;

    public Certificate(int certificateID,LocalDateTime issueDate,int studentID,int courseID){
        this.certificateID=certificateID;
        this.issueDate=issueDate;
        this.studentID=studentID;
        this.courseID=courseID;
    }

    public int getCertificateID() {
        return certificateID;
    }
    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }
}
