package com.reactiveAPP.studentsAPI.repository;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {

}
