package com.reactiveAPP.coursesAPI.usecases;

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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetCourseByNameUseCaseTest {


    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    GetCourseByNameUseCase getCourseByNameUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getCourseByNameUseCase = new GetCourseByNameUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getCourseByName_Success")
    void getBooksByName(){
        var fluxCourses = Flux.just(
                InstanceProvider.getCourses().get(0),
                InstanceProvider.getCourses().get(1),
                InstanceProvider.getCourses().get(2));

        Mockito.when(repoMock.findByName(ArgumentMatchers.anyString())).thenReturn(fluxCourses);

        var service = getCourseByNameUseCase.apply("");

        StepVerifier.create(service)
                .expectNextMatches(courseDTO -> courseDTO.getName().equals("Angular1"))
                .expectNextCount(2)
                .verifyComplete();
        Mockito.verify(repoMock).findByName(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("getCourseByNonExistingName_UnSuccess")
    void getBooksByNonExistingName(){

        Mockito.when(repoMock.findByName(ArgumentMatchers.anyString())).thenReturn(Flux.empty());

        var service = getCourseByNameUseCase.apply("");

        StepVerifier.create(service)
                .expectErrorMessage("No courses found")
                .verify();
        Mockito.verify(repoMock).findByName(ArgumentMatchers.anyString());
    }


}