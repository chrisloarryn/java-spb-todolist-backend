package accounttransaction.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import accounttransaction.api.controllers.AccountController;
import accounttransaction.business.abstracts.AccountService;
import accounttransaction.business.dto.requests.create.CreateAccountRequest;
import accounttransaction.business.dto.requests.update.UpdateAccountRequest;
import accounttransaction.business.dto.responses.create.CreateAccountResponse;
import accounttransaction.business.dto.responses.get.GetAllAccountsResponse;
import accounttransaction.business.dto.responses.get.GetAccountResponse;
import accounttransaction.business.dto.responses.update.UpdateAccountResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class AccountControllerTests {

    private AccountController todoController;

    @Mock
    private AccountService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        todoController = new AccountController(todoService);
    }

    @Test
    public void testGetAll() {
        List<GetAllAccountsResponse> todos = new ArrayList<>();
        given(todoService.getAll()).willReturn(todos);

        List<GetAllAccountsResponse> result = todoController.getAll();

        assertEquals(todos, result);
    }

    @Test
    public void testGetById() {
        UUID id = UUID.randomUUID();
        GetAccountResponse todoResponse = new GetAccountResponse();
        given(todoService.getById(id)).willReturn(todoResponse);

        GetAccountResponse result = todoController.getById(id);

        assertEquals(todoResponse, result);
    }

    @Test
    public void testAdd() throws InterruptedException {
        CreateAccountRequest request = new CreateAccountRequest();
        CreateAccountResponse response = new CreateAccountResponse();
        given(todoService.add(request)).willReturn(response);

        CreateAccountResponse result = todoController.add(request);

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
        UpdateAccountRequest request = new UpdateAccountRequest();
        UpdateAccountResponse response = new UpdateAccountResponse();
        given(todoService.update(id, request)).willReturn(response);

        UpdateAccountResponse result = todoController.update(id, request);

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
