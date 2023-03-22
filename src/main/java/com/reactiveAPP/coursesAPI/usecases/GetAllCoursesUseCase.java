package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.config.MongoConfig;
import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllCoursesUseCase implements Supplier<Flux<CourseDTO>> {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public Flux<CourseDTO> get() {
        return this.courseRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No courses available")))
                .map(course -> mapper.map(course, CourseDTO.class))
                .onErrorResume(Mono::error);
    }
}
