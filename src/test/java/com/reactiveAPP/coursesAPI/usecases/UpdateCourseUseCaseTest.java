package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateCourseUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    UpdateCourseUseCase updateCourseUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateCourseUseCase = new UpdateCourseUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("updateCourse_Success")
    void updateCourse(){
        var courseID = "ID1";
        var oldCourse = Mono.just(InstanceProvider.getCourse());
        var newCourse = InstanceProvider.getCourseToUpdate();
        var monoNewCourse = Mono.just(InstanceProvider.getCourseToUpdate());

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(oldCourse);
        Mockito.when(repoMock.save(ArgumentMatchers.any(Course.class))).thenReturn(monoNewCourse);

        var service = updateCourseUseCase.apply(courseID,
                mapper.map(newCourse, CourseDTO.class));

        StepVerifier.create(service)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).findById(courseID);
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Course.class));
    }

    @Test
    @DisplayName("updateCourse_NonSuccess")
    void updateCourseByNonExistingID(){
        var courseID = "ID1";
        var newCourse = InstanceProvider.getCourseToUpdate();
        var monoNewCourse = Mono.just(InstanceProvider.getCourseToUpdate());

        Mockito.when(repoMock.findById(courseID)).thenReturn(Mono.empty());

        var service = updateCourseUseCase.apply(courseID,
                mapper.map(newCourse, CourseDTO.class));

        StepVerifier.create(service)
                //Test to check that the expected stream is of 0 size
                //.expectNextCount(0)
                //Test to check an expected error
                .expectError(IllegalArgumentException.class)
                .verify();
        Mockito.verify(repoMock).findById(courseID);

        //Mockito.verify(repoMock).save(ArgumentMatchers.any(Student.class));
    }
}