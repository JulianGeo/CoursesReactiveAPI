package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class UpdateCourseUseCase implements BiFunction<String, CourseDTO, Mono<CourseDTO>>{
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<CourseDTO> apply(String id, CourseDTO courseDTO) {
        return this.courseRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No course matches the provided ID")))
                .flatMap(student ->{
                    courseDTO.setId(student.getId());
                    return courseRepository.save(mapper.map(courseDTO, Course.class));
                }).map(course -> mapper.map(course, CourseDTO.class))
                .onErrorResume(Mono::error);
    }
}



