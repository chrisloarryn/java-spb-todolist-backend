package personclient.business.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import personclient.entities.ClientNotFoundException;
import personclient.repository.ClientRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TodoBusinessRulesTests {

	private ClientBusinessRules todoBusinessRules;

	@Mock
	private ClientRepository repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		todoBusinessRules = new ClientBusinessRules(repository);
	}

	@Test
	public void testCheckIfTodoExists_WhenTodoExists_ShouldNotThrowException() {
		UUID id = UUID.randomUUID();
		given(repository.existsById(id)).willReturn(true);

		// The method should not throw an exception
		todoBusinessRules.checkIfTodoExists(id);

		verify(repository, times(1)).existsById(id);
	}

	@Test
	public void testCheckIfTodoExists_WhenTodoDoesNotExist_ShouldThrowTodoNotFoundException() {
		UUID id = UUID.randomUUID();
		given(repository.existsById(id)).willReturn(false);

		// The method should throw TodoNotFoundException
		assertThrows(ClientNotFoundException.class, () -> {
			todoBusinessRules.checkIfTodoExists(id);
		});

		verify(repository, times(1)).existsById(id);
	}
}
