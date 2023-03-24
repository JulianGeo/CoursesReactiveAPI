package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetCourseByIdUseCase implements Function<String, Mono<CourseDTO>> {

private final CourseRepository courseRepository;
private final ModelMapper mapper;

@Override
public Mono<CourseDTO> apply(String ID) {
        return this.courseRepository
        .findById(ID)
        .switchIfEmpty(Mono.error(new Throwable("Course not found")))
        .map(course -> mapper.map(course, CourseDTO.class));
        }
}