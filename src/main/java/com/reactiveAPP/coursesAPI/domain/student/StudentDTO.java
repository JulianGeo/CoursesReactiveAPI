package com.reactiveAPP.coursesAPI.domain.student;

import com.reactiveAPP.coursesAPI.domain.collection.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@ToString
public class StudentDTO {
    private String id;
    private String idNum;
    private String name;
    private String lastname;
    private String email;
    private String plan;
    private Set<String> courses;

    public StudentDTO(String id, String idNum, String name, String lastname, String email, String plan, Set<String> courses) {
        this.id=id;
        this.idNum=idNum;
        this.name=name;
        this.lastname=lastname;
        this.email=email;
        this.plan=plan;
        this.courses=courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (!(o instanceof StudentDTO)) return false;
        StudentDTO that=(StudentDTO) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

