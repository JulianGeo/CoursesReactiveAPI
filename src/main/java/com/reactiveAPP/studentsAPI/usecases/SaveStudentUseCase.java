package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SaveStudentUseCase  implements Function<StudentDTO, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> apply(StudentDTO studentDTO) {
        //TODO: do i need a switch if empty here?
        return this.studentRepository
                .save(mapper.map(studentDTO, Student.class))
                .map(student -> mapper.map(student, StudentDTO.class))
                .onErrorResume(Mono::error);
    }
}
