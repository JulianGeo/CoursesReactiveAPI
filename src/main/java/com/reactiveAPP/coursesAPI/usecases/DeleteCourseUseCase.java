package com.reactiveAPP.coursesAPI.usecases;

import com.reactiveAPP.coursesAPI.repository.CourseRepository;
import com.reactiveAPP.coursesAPI.router.CourseRouter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteCourseUseCase implements Function<String, Mono<Void>> {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<Void> apply(String Id) {
        return this.courseRepository
                .findById(Id)
                .switchIfEmpty(Mono.error(new ClassNotFoundException("Course not found")))
                .flatMap(course -> this.courseRepository.deleteById(Id))
                //TODO: fix it to catch the error
                .onErrorResume(throwable -> Mono.error(throwable));
    }
}
