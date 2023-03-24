package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.domain.student.StudentDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import com.reactiveAPP.coursesAPI.util.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EnrollStudentUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    EnrollStudentUseCase enrollStudentUseCase;


    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        enrollStudentUseCase = new EnrollStudentUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("enrollStudent_Success")
    void enrollStudent(){
        StudentDTO studentDTO = InstanceProvider.getNewStudent();
        Course course = InstanceProvider.getCourse();
        var monoCourse =Mono.just(InstanceProvider.getCourse());
        String courseID = course.getId();

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(monoCourse);
        Mockito.when(repoMock.save(any(Course.class)))
                .thenAnswer(invocationOnMock -> {
                   return Mono.just(invocationOnMock.getArgument(0));
                });

        var service = enrollStudentUseCase.apply(studentDTO, courseID);

        StepVerifier.create(service)
                .expectNextMatches(courseDTO -> courseDTO.getName().equals(course.getName()))
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(repoMock).save(any(Course.class));
    }

}