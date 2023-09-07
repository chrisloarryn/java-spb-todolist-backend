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
    private String description;
    private Date dueDate;
    private State state = State.TODO;
    private Boolean active = true;
    private Priority priority = Priority.LOW;
    private String author;
}
