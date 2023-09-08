package com.chrisloarryn.todolistapi.business.dto.requests.create;

import com.chrisloarryn.todolistapi.entities.enums.Priority;
import com.chrisloarryn.todolistapi.entities.enums.State;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoRequest {
    @NonNull
    private String title;
    @NonNull
    private String description;
    private Date dueDate;
    @NonNull
    private State state = State.TODO;
    @NonNull
    private Boolean active = true;
    @NonNull
    private Priority priority = Priority.LOW;
    @NonNull
    private String author;
}
