package com.reactiveAPP.coursesAPI.domain.collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="students")
public class Course {

    @Id
    private String id =UUID.randomUUID().toString().substring(0,10);

    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String name;

    @NotBlank(message="Empty field error")
    @NotNull(message ="lastname is required")
    private String description;

    //TODO: add all validations
    private String coach;
    private String level;
    private Set<String> students = new HashSet<>();
}
