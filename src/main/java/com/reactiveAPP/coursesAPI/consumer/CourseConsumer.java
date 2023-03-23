package com.reactiveAPP.coursesAPI.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactiveAPP.coursesAPI.usecases.EnrollStudentUseCase;
import com.reactiveAPP.coursesAPI.usecases.UnenrollCourseUseCase;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseConsumer {
    private final ObjectMapper objectMapper;
    private final EnrollStudentUseCase enrollStudentUseCase;
    private final UnenrollCourseUseCase unenrollCourseUseCase;

    @RabbitListener(queues = "students.queue")
    public void receiveEventBook(String message) throws JsonProcessingException {
        StudentEvent event = objectMapper.readValue(message, StudentEvent.class);
        if (event.getEventType().equals("enroll")){
            enrollStudentUseCase.apply(event.getStudentID(), event.getCourseID()).subscribe();
        }
        if (event.getEventType().equals("unenroll")){
            unenrollCourseUseCase.apply(event.getStudentID(), event.getCourseID()).subscribe();
        }
    }

}


