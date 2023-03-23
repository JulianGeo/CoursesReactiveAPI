package com.reactiveAPP.coursesAPI.consumer;

import com.reactiveAPP.coursesAPI.domain.student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StudentEvent {
    private String studentID;
    private String courseID;
    private String eventType;
}