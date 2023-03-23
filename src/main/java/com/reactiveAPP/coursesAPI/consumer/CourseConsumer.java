package com.reactiveAPP.coursesAPI.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactiveAPP.coursesAPI.usecases.EnrollStudentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseConsumer {
    private final ObjectMapper objectMapper;
    private final EnrollStudentUseCase enrollStudentUseCase;

    @RabbitListener(queues = "students.queue")
    public void receiveEventBook(String message) throws JsonProcessingException {
        StudentEvent event = objectMapper.readValue(message, StudentEvent.class);
        System.out.println("student id"+event.getStudentID());
        enrollStudentUseCase.apply(event.getStudentID(), event.getCourseID()).subscribe();
    }

}


