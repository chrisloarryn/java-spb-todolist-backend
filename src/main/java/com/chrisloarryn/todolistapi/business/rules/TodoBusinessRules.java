package com.chrisloarryn.todolistapi.business.rules;

import com.chrisloarryn.todolistapi.entities.TodoNotFoundException;
import com.chrisloarryn.todolistapi.repository.TodoRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TodoBusinessRules {
    private final TodoRepository repository;

    public void checkIfTodoExists(UUID id) {
        if (!repository.existsById(id)) {
            // log the id
            System.out.println(id);

            // TODO: BusinessException
            throw new TodoNotFoundException(
                    "Todo with id " + id + " does not exists");
        }
    }
}
