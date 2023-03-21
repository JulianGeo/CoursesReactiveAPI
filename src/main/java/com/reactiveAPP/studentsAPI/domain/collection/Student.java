package com.reactiveAPP.studentsAPI.domain.collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="students")
public class Student {

    @Id
    private String id =UUID.randomUUID().toString().substring(0,10);

    @NotBlank(message="Empty field error")
    @NotNull(message ="idNum is required")
    private String idNum;
    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    @Pattern(regexp="^[A-Z][a-z]*$", message="name format is required")
    private String name;
    @NotBlank(message="Empty field error")
    @NotNull(message ="lastname is required")
    private String lastname;
    //TODO: add email validation
    private String email;
    private String plan;
    private Set<String> courses = new HashSet<>();
}
