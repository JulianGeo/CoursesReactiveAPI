package com.reactiveAPP.coursesAPI.usecases;

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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllCoursesUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    GetAllCoursesUseCase getAllCoursesUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getAllCoursesUseCase = new GetAllCoursesUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getAllCourses_Success")
    void getAllBooks(){
        var fluxStudents = Flux.just(
                InstanceProvider.getCourses().get(0),
                InstanceProvider.getCourses().get(1),
                InstanceProvider.getCourses().get(2));

        Mockito.when(repoMock.findAll()).thenReturn(fluxStudents);

        var service = getAllCoursesUseCase.get();

        StepVerifier.create(service)
                .expectNextCount(3)
                .verifyComplete();
        Mockito.verify(repoMock).findAll();
    }

}