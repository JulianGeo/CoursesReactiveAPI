package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import com.reactiveAPP.coursesAPI.util.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveCourseUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    SaveCourseUseCase saveCourseUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveCourseUseCase = new SaveCourseUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("saveCourse_Success")
    void SaveCourse(){
        var courseDTO =mapper.map(InstanceProvider.getCourse(), CourseDTO.class);

        Mockito.when(repoMock.save(mapper.map(courseDTO, Course.class)))
                .thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        var service = saveCourseUseCase.apply(courseDTO);

        StepVerifier.create(service)
                //Test to check next object as a whole
                .expectNext(mapper.map(InstanceProvider.getCourses().get(2), CourseDTO.class))
                //Custom and specific test for the expected object
                //.expectNextMatches(studentDTO1 -> studentDTO1.getName().equals("Wout"))
                //Test to check the size of the stream
                //.expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).save(mapper.map(courseDTO, Course.class));
    }

}