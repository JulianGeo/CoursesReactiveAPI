package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.consumer.StudentEvent;
import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.domain.student.StudentDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class EnrollStudentUseCase implements BiFunction<StudentDTO, String, Mono<CourseDTO>> {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;


    @Override
    public Mono<CourseDTO> apply(StudentDTO studentDTO, String courseID) {

        return this.courseRepository
                .findById(courseID)
                .switchIfEmpty(Mono.error(new Throwable("Course not found")))
                .flatMap(course -> {
                    Set<StudentDTO> students1 = course.getStudents();
                    course.addStudent(studentDTO);
                    return this.courseRepository.save(course);
                }).map(course -> mapper.map(course, CourseDTO.class));
    }
}
