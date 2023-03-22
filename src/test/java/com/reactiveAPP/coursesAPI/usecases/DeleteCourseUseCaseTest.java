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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteCourseUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    DeleteCourseUseCase deleteCourseUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        deleteCourseUseCase = new DeleteCourseUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("deleteCourseByID_Success")
    void deleteStudent(){
        var courseID = "ID1";
        var course = Mono.just(InstanceProvider.getCourse());

        Mockito.when(repoMock.findById(courseID)).thenReturn(course);
        Mockito.when(repoMock.deleteById(courseID)).thenReturn(Mono.empty());

        var service = deleteCourseUseCase.apply(courseID);

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(repoMock).deleteById(courseID);
    }

}