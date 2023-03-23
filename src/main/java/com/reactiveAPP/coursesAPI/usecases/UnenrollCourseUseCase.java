package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class UnenrollCourseUseCase implements BiFunction<String, String, Mono<CourseDTO>> {

    private final ModelMapper mapper;
    private final CourseRepository courseRepository;

    @Override
    public Mono<CourseDTO> apply(String studentID, String courseID) {
/*        System.out.println("use case used");
        System.out.println("courseID:" +courseID);*/
        return this.courseRepository
                .findById(courseID)
                .switchIfEmpty(Mono.error(new Throwable("Course not found")))
                .flatMap(course -> {
                    Set<String> students1 = course.getStudentDTOSID();
                    if (!students1.contains(studentID)){
                        return Mono.error(new Throwable("Student not enrolled yet"))     ;
                    }
                    students1.remove(studentID);
                    course.setStudentDTOSID(students1);
                    return this.courseRepository.save(course);
                }).map(course -> mapper.map(course, CourseDTO.class));
    }
}
