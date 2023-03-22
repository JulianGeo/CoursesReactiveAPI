package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetCourseByCoachUseCase implements Function<String, Flux<CourseDTO>>{
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public Flux<CourseDTO> apply(String coachName) {
        return this.courseRepository
                .findByCoach(coachName)
                .switchIfEmpty(Flux.error(new Throwable("No courses found")))
                .map(course -> mapper.map(course, CourseDTO.class))
                .onErrorResume(throwable -> Flux.error(throwable));
    }
}