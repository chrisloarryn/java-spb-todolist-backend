package com.chrisloarryn.todolistapi.business.abstracts;

import com.chrisloarryn.todolistapi.business.dto.requests.create.CreateTodoRequest;
import com.chrisloarryn.todolistapi.business.dto.responses.create.CreateTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetAllTodosResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.update.UpdateTodoResponse;

import java.util.List;
import java.util.UUID;

public interface TodoService {
    List<GetAllTodosResponse> getAll();
    GetTodoResponse getById(UUID id);
    CreateTodoResponse add(CreateTodoRequest todo);
    UpdateTodoResponse update(UUID id, CreateTodoRequest todo);
    void delete(UUID id);
}
