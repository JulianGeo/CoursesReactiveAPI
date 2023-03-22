package com.reactiveAPP.coursesAPI.domain.student;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String id;
    private String idNum;
    private String name;
    private String lastname;
    private String email;
    private String plan;
    private Set<Course> courses;
}
