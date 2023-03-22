package com.reactiveAPP.coursesAPI.usecases;

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
class GetCourseByIdUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    GetCourseByIdUseCase getCourseByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getCourseByIdUseCase = new GetCourseByIdUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getCourseById_Success")
    void getStudentById(){
        var courseID = "ID1";
        var course = Mono.just(InstanceProvider.getCourse());

        Mockito.when(repoMock.findById(courseID)).thenReturn(course);

        var service = getCourseByIdUseCase.apply(courseID);

        StepVerifier.create(service)
                //.expectNextCount(1)
                .expectNext(mapper.map(InstanceProvider.getCourses().get(2), CourseDTO.class))
                .verifyComplete();
        Mockito.verify(repoMock).findById(courseID);
    }

    @Test
    @DisplayName("getStudentById_NonSuccess")
    void getStudentByNonExistingID(){
        var courseID = "ID1";

        Mockito.when(repoMock.findById(courseID)).thenReturn(Mono.empty());

        var service = getCourseByIdUseCase.apply(courseID);

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(repoMock).findById(courseID);
    }
}