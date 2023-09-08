package com.chrisloarryn.todolistapi.api.controllers;

import com.chrisloarryn.todolistapi.api.controllers.TodoController;
import com.chrisloarryn.todolistapi.business.abstracts.TodoService;
import com.chrisloarryn.todolistapi.business.dto.requests.create.CreateTodoRequest;
import com.chrisloarryn.todolistapi.business.dto.requests.update.UpdateTodoRequest;
import com.chrisloarryn.todolistapi.business.dto.responses.create.CreateTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetAllTodosResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.update.UpdateTodoResponse;
import com.chrisloarryn.todolistapi.entities.TodoNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TodoControllerTests {

    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        todoController = new TodoController(todoService);
    }

    @Test
    public void testGetAll() {
        List<GetAllTodosResponse> todos = new ArrayList<>();
        when(todoService.getAll()).thenReturn(todos);

        List<GetAllTodosResponse> result = todoController.getAll();

        assertEquals(todos, result);
    }

    @Test
    public void testGetById() {
        UUID id = UUID.randomUUID();
        GetTodoResponse todoResponse = new GetTodoResponse();
        when(todoService.getById(id)).thenReturn(todoResponse);

        GetTodoResponse result = todoController.getById(id);

        assertEquals(todoResponse, result);
    }

    @Test
    public void testAdd() throws InterruptedException {
        CreateTodoRequest request = new CreateTodoRequest();
        CreateTodoResponse response = new CreateTodoResponse();
        when(todoService.add(request)).thenReturn(response);

        CreateTodoResponse result = todoController.add(request);

        assertEquals(response, result);

        // Verify that the service method was called
        verify(todoService, times(1)).add(request);

        // Verify that the service method was called with the correct argument
        verify(todoService, times(1)).add(request);

        // Verify that the service method was not called with the wrong argument
        verify(todoService, never()).add(null);

        // Verify that the service method was called with the correct argument only once
        verify(todoService, times(1)).add(request);
    }

    @Test
    public void shouldUpdate() {
        UUID id = UUID.randomUUID();
        UpdateTodoRequest request = new UpdateTodoRequest();
        UpdateTodoResponse response = new UpdateTodoResponse();
        given(todoService.update(id, request)).willReturn(response);

        UpdateTodoResponse result = todoController.update(id, request);

        assertEquals(response, result);

        // Verify that the service method was called
        verify(todoService, times(1)).update(id, request);

        // Verify that the service method was called with the correct argument
        verify(todoService, times(1)).update(id, request);

    }

    @Test
    public void shouldDelete() {
        UUID id = UUID.randomUUID();
        doNothing().when(todoService).delete(id);

        todoController.delete(id);

        // Verify that the service method was called
        verify(todoService, times(1)).delete(id);

        // Verify that the service method was called with the correct argument
        verify(todoService, times(1)).delete(id);
    }
}
