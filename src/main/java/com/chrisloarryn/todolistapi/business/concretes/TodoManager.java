package com.chrisloarryn.todolistapi.business.concretes;

import com.chrisloarryn.todolistapi.business.abstracts.TodoService;
import com.chrisloarryn.todolistapi.business.dto.requests.create.CreateTodoRequest;
import com.chrisloarryn.todolistapi.business.dto.requests.update.UpdateTodoRequest;
import com.chrisloarryn.todolistapi.business.dto.responses.create.CreateTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetAllTodosResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.update.UpdateTodoResponse;
import com.chrisloarryn.todolistapi.business.rules.TodoBusinessRules;
import com.chrisloarryn.todolistapi.entities.Todo;
import com.chrisloarryn.todolistapi.repository.TodoRepository;
import com.chrisloarryn.todolistapi.utils.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TodoManager implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapperService mapper;
    private final TodoBusinessRules rules;

    @Override
    public List<GetAllTodosResponse> getAll() {
        var todos = todoRepository.findAll();
        return todos
                .stream()
                .map(todo -> mapper.forResponse().map(todo, GetAllTodosResponse.class))
                .toList();
    }

    @Override
    public GetTodoResponse getById(UUID id) {
        rules.checkIfTodoExists(id);
        var car = todoRepository.findById(id).orElseThrow();
        return mapper.forResponse().map(car, GetTodoResponse.class);
    }

    @Override
    public CreateTodoResponse add(CreateTodoRequest todoRequest) {
        var todo = mapper.forRequest().map(todoRequest, Todo.class);
        var createdTodo = todoRepository.save(todo);
        return mapper.forResponse().map(createdTodo, CreateTodoResponse.class);
    }

    @Override
    public UpdateTodoResponse update(UUID id, UpdateTodoRequest todoRequest) {
        rules.checkIfTodoExists(id);
        var todo = mapper.forRequest().map(todoRequest, Todo.class);
        todo.setId(id);
        todoRepository.save(todo);
        return mapper.forResponse().map(todo, UpdateTodoResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfTodoExists(id);
        todoRepository.deleteById(id);
    }
}
