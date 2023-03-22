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
class GetCourseByCoachUseCaseTest {

    @Mock
    CourseRepository repoMock;
    ModelMapper mapper;
    GetCourseByCoachUseCase getCourseByCoachUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getCourseByCoachUseCase = new GetCourseByCoachUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getCourseByCoach_Success")
    void getCourseByCoach(){
        var fluxCourses = Flux.just(
                InstanceProvider.getCourses().get(0),
                InstanceProvider.getCourses().get(1),
                InstanceProvider.getCourses().get(2));

        Mockito.when(repoMock.findByCoach(ArgumentMatchers.anyString())).thenReturn(fluxCourses);

        var service = getCourseByCoachUseCase.apply("");

        StepVerifier.create(service)
                .expectNextMatches(courseDTO -> courseDTO.getCoach().equals("Raul"))
                .expectNextCount(2)
                .verifyComplete();
        Mockito.verify(repoMock).findByCoach(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("getCourseByNonExistingCoach_UnSuccess")
    void getCourseByNonExistingCoach(){

        Mockito.when(repoMock.findByCoach(ArgumentMatchers.anyString())).thenReturn(Flux.empty());

        var service = getCourseByCoachUseCase.apply("");

        StepVerifier.create(service)
                .expectErrorMessage("No courses found")
                .verify();
        Mockito.verify(repoMock).findByCoach(ArgumentMatchers.anyString());
    }
}