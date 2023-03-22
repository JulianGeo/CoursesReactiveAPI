package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetCourseByNameUseCase implements Function<String, Flux<CourseDTO>> {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public Flux<CourseDTO> apply(String name) {
        return this.courseRepository
                .findByName(name)
                .switchIfEmpty(Mono.empty())
                .map(course -> mapper.map(course, CourseDTO.class));
    }
}