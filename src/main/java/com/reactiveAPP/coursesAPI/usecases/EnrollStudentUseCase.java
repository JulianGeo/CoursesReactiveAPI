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

    private final ModelMapper mapper;
    private final CourseRepository courseRepository;

    @Override
    public Mono<CourseDTO> apply(StudentDTO studentDTO, String courseID) {
        System.out.println("use case used");
        System.out.println("courseID:" +courseID);
        System.out.println("studentName:" +studentDTO.getName());
        return this.courseRepository
                .findById(courseID)
                .switchIfEmpty(Mono.error(new Throwable("Course not found")))
                .flatMap(course -> {
                    Set<StudentDTO> students1 = course.getStudents();
                    System.out.println("course id inside the return"+courseID);
                    //System.out.printf(courseID);
                    students1.add(studentDTO);
                    course.setStudents(students1);
                    return this.courseRepository.save(course);
                }).map(course -> mapper.map(course, CourseDTO.class));
    }
}
