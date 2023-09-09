package com.chrisloarryn.todolistapi.business.rules;

import com.chrisloarryn.todolistapi.entities.TodoNotFoundException;
import com.chrisloarryn.todolistapi.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TodoBusinessRulesTests {

	private TodoBusinessRules todoBusinessRules;

	@Mock
	private TodoRepository repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		todoBusinessRules = new TodoBusinessRules(repository);
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
		assertThrows(TodoNotFoundException.class, () -> {
			todoBusinessRules.checkIfTodoExists(id);
		});

		verify(repository, times(1)).existsById(id);
	}
}
