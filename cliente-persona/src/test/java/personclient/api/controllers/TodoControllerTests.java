package personclient.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import personclient.api.controllers.ClientController;
import personclient.business.abstracts.ClientService;
import personclient.business.dto.requests.create.CreateClientRequest;
import personclient.business.dto.requests.update.UpdateClientRequest;
import personclient.business.dto.responses.create.CreateClientResponse;
import personclient.business.dto.responses.get.GetAllClientsResponse;
import personclient.business.dto.responses.get.GetClientResponse;
import personclient.business.dto.responses.update.UpdateClientResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TodoControllerTests {

    private ClientController todoController;

    @Mock
    private ClientService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        todoController = new ClientController(todoService);
    }

    @Test
    public void testGetAll() {
        List<GetAllClientsResponse> todos = new ArrayList<>();
        given(todoService.getAll()).willReturn(todos);

        List<GetAllClientsResponse> result = todoController.getAll();

        assertEquals(todos, result);
    }

    @Test
    public void testGetById() {
        UUID id = UUID.randomUUID();
        GetClientResponse todoResponse = new GetClientResponse();
        given(todoService.getById(id)).willReturn(todoResponse);

        GetClientResponse result = todoController.getById(id);

        assertEquals(todoResponse, result);
    }

    @Test
    public void testAdd() throws InterruptedException {
        CreateClientRequest request = new CreateClientRequest();
        CreateClientResponse response = new CreateClientResponse();
        given(todoService.add(request)).willReturn(response);

        CreateClientResponse result = todoController.add(request);

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
        UpdateClientRequest request = new UpdateClientRequest();
        UpdateClientResponse response = new UpdateClientResponse();
        given(todoService.update(id, request)).willReturn(response);

        UpdateClientResponse result = todoController.update(id, request);

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
