package com.chrisloarryn.todolistapi.business.dto.requests.update;

import com.chrisloarryn.todolistapi.entities.enums.Priority;
import com.chrisloarryn.todolistapi.entities.enums.State;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTodoRequest {
    @NonNull
    private String title;
    @NonNull
    private String description;
    private Date dueDate;
    @NonNull
    private State state;
    @NonNull
    private Boolean active;
    @NonNull
    private Priority priority;
    @NonNull
    private String author;
}
