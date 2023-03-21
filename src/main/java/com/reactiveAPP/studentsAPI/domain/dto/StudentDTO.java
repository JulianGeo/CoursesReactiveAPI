package com.reactiveAPP.studentsAPI.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StudentDTO {
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
