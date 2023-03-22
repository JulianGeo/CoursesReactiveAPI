package com.reactiveAPP.coursesAPI.util;

import com.reactiveAPP.coursesAPI.domain.collection.Course;

import java.util.List;
import java.util.Set;

public class InstanceProvider {
    public static List<Course> getCourses(){
        return List.of(
                new Course("ID1", "Angular1", "Angular course", "Raul",  "3", Set.of("Student1", "Student2")),
                new Course("ID2", "SpringBoot1", "Springboot course", "Mishel", "3", Set.of("Student1", "Student2")),
                new Course("ID3", "React1","React course" ,"Adryan", "3", Set.of("Student1", "Student2"))
                );
    }

    public static Course getCourse(){
        return new Course("ID3", "React1","React course" ,"Adryan", "3", Set.of("Student1", "Student2"));
    }

    public static Course getCourseToUpdate(){
        return new Course("ID3", "React5","React course" ,"Julian", "5", Set.of("Student1", "Student2"));
    }
}
