package com.reactiveAPP.coursesAPI.util;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import com.reactiveAPP.coursesAPI.domain.student.StudentDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InstanceProvider {
    public static List<Course> getCourses(){
        return List.of(
                new Course("ID1", "Angular1", "Angular course", "Raul",  "3", getStudents()),
                new Course("ID2", "SpringBoot1", "Springboot course", "Mishel", "3", getStudents()),
                new Course("ID3", "React1","React course" ,"Adryan", "3", getStudents())
                );
    }

    public static Course getCourse(){
        return new Course("ID3", "React1","React course" ,"Adryan", "3", getStudents());
    }

    public static Course getCourseToUpdate(){
        return new Course("ID2", "SpringBoot1", "Springboot course", "Mishel", "3", getStudents());
    }

    public static Set<StudentDTO> getStudents(){
        return Set.of(
                new StudentDTO("Id1", "idNum1", "Elvis", "Crespo", "elvis@gmail.com","basic",new HashSet<>()),
                new StudentDTO("Id2", "idNum2", "Elvis", "Presley", "elvis@gmail.com","basic",new HashSet<>()),
                new StudentDTO("Id3", "idNum3", "Elvis", "Costello", "elvis@gmail.com","basic",new HashSet<>())
        );
    }
}
