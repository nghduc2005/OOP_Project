package app.model;
import java.util.*;
public class Classes {
    private String  class_id,teacher_id,total_student,subject_name;
    private int maxnumberStudent;
    public Classes(String class_id, String total_student, String subject_name,
                   int m ){
        this.maxnumberStudent = m;
        this.class_id = class_id;
        this.total_student = total_student;
        this.subject_name=subject_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public int getMaxNumberStudent() {
        return maxnumberStudent;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getTotal_student() {
        return total_student;
    }
}
