package com.reactiveAPP.coursesAPI.router;

import com.reactiveAPP.coursesAPI.domain.dto.CourseDTO;
import com.reactiveAPP.coursesAPI.usecases.DeleteCourseUseCase;
import com.reactiveAPP.coursesAPI.usecases.GetAllCoursesUseCase;
import com.reactiveAPP.coursesAPI.usecases.SaveCourseUseCase;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@AllArgsConstructor
public class CourseRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllCourses(GetAllCoursesUseCase getAllCoursesUseCase){
        return route(GET("/api/courses"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCoursesUseCase.get(), CourseDTO.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> saveCourse(SaveCourseUseCase saveCourseUseCase){
        return route(POST("/api/courses").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(CourseDTO.class)
                        .flatMap(courseDTO -> saveCourseUseCase.apply(courseDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteCourseById(DeleteCourseUseCase deleteCourseUseCase){
        return route(DELETE("api/courses/{id}"),
                request -> deleteCourseUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Course with ID: "+request.pathVariable("id") +", was deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));

    }

}
