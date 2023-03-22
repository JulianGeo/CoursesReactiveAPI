package com.reactiveAPP.coursesAPI.repository;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CourseRepository extends ReactiveMongoRepository<Course, String> {

    Flux<Object> findByName(String name);
}
