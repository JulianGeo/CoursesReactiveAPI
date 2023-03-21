package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SaveCourseUseCase implements Function<CourseDTO, Mono<CourseDTO>> {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<CourseDTO> apply(CourseDTO courseDTO) {
        //TODO: do i need a switch if empty here?
        return this.courseRepository
                .save(mapper.map(courseDTO, Course.class))
                .map(student -> mapper.map(courseDTO, CourseDTO.class))
                .onErrorResume(Mono::error);
    }
}
