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
class UnenrollCourseUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    UnenrollCourseUseCase unenrollCourseUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        unenrollCourseUseCase = new UnenrollCourseUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("unenrollStudent_Success")
    void unenrollStudent(){
        var courseID = "ID1";
        var monoCourse = Mono.just(InstanceProvider.getCourse());
        var newCourse = InstanceProvider.getCourseToUpdate();
        var studentDTO = InstanceProvider.getStudents().iterator().next();

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(monoCourse);
        Mockito.when(repoMock.save(ArgumentMatchers.any(Course.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        var service = unenrollCourseUseCase.apply(studentDTO,
                courseID);

        StepVerifier.create(service)
                .expectNext(mapper.map(InstanceProvider.getCourseToUpdate(), CourseDTO.class))
                .verifyComplete();
        Mockito.verify(repoMock).findById(courseID);
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Course.class));
    }
}