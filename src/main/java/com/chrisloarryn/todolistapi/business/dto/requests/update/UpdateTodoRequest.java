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
    private String title;
    private String description;
    private Date dueDate;
    private State state;
    private Boolean active;
    private Priority priority;
    private String author;
}
