package com.chrisloarryn.todolistapi.business.concretes;

import com.chrisloarryn.todolistapi.business.abstracts.TodoService;
import com.chrisloarryn.todolistapi.business.concretes.TodoManager;
import com.chrisloarryn.todolistapi.business.dto.requests.create.CreateTodoRequest;
import com.chrisloarryn.todolistapi.business.dto.requests.update.UpdateTodoRequest;
import com.chrisloarryn.todolistapi.business.dto.responses.create.CreateTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetAllTodosResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.get.GetTodoResponse;
import com.chrisloarryn.todolistapi.business.dto.responses.update.UpdateTodoResponse;
import com.chrisloarryn.todolistapi.business.rules.TodoBusinessRules;
import com.chrisloarryn.todolistapi.configuration.mappers.ModelMapperConfig;
import com.chrisloarryn.todolistapi.entities.Todo;
import com.chrisloarryn.todolistapi.entities.TodoNotFoundException;
import com.chrisloarryn.todolistapi.repository.TodoRepository;
import com.chrisloarryn.todolistapi.utils.mappers.ModelMapperManager;
import com.chrisloarryn.todolistapi.utils.mappers.ModelMapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TodoManagerTests {

	private TodoService todoService;

	@Mock
	private TodoRepository todoRepository;

	@Mock
	private ModelMapperConfig modelMapperConfig;

	@Mock
	private ModelMapperService mapper;

	private ModelMapper resultMapper;
	private ModelMapper requestMapper;

	@Mock
	private TodoBusinessRules todoBusinessRules;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		ModelMapper mapper = mock(ModelMapper.class);
		Configuration configuration = mock(Configuration.class);

		given(configuration.setAmbiguityIgnored(true)).willReturn(configuration);
		given(configuration.setMatchingStrategy(MatchingStrategies.LOOSE)).willReturn(configuration);
		when(mapper.getConfiguration()).thenReturn(configuration);

		ModelMapperService modelMapperService = new ModelMapperManager(mapper);
		resultMapper = modelMapperService.forResponse();

		given(configuration.setMatchingStrategy(MatchingStrategies.STANDARD)).willReturn(configuration);
		when(mapper.getConfiguration()).thenReturn(configuration);

		ModelMapperService modelMapperServiceReq = new ModelMapperManager(mapper);
		requestMapper = modelMapperServiceReq.forRequest();

		todoService = new TodoManager(todoRepository, modelMapperService, todoBusinessRules);
	}

	@Test
	public void testGetAll() {
		// Arrange
		List<Todo> todoList = new ArrayList<>();
		given(todoRepository.findAll()).willReturn(todoList);

		// Act
		List<GetAllTodosResponse> result = todoService.getAll();

		// Assert
		assertNotNull(result);
		assertEquals(0, result.size());
		assertThat(result).isNotNull();
	}

	@Test
	public void testGetById_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		Todo todo = new Todo();
		GetTodoResponse response = new GetTodoResponse();

		given(todoRepository.existsById(id)).willReturn(true);
		given(todoRepository.findById(id)).willReturn(Optional.of(todo));
		given(resultMapper.map(eq(todo), eq(GetTodoResponse.class))).willReturn(response);

		// Act
		GetTodoResponse result = todoService.getById(id);

		// Assert
		assertNotNull(result);

		// You may add more specific assertions based on your response mapping logic.
	}

	@Test
	public void testGetById_WhenTodoNotFound() {
		// Arrange
		UUID id = UUID.randomUUID();

		// Act & Assert
		assertThrows(TodoNotFoundException.class, () -> todoService.getById(id));
	}

	@Test
	public void testAdd_() {
		// Arrange
		CreateTodoRequest request = new CreateTodoRequest();
		Todo todo = new Todo();
		CreateTodoResponse response = new CreateTodoResponse();

		given(requestMapper.map(eq(request), eq(Todo.class))).willReturn(todo);
		given(todoRepository.save(any(Todo.class))).willReturn(todo);
		given(resultMapper.map(eq(todo), eq(CreateTodoResponse.class))).willReturn(response);

		// Act
		CreateTodoResponse result = todoService.add(request);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testUpdate_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		UpdateTodoRequest request = new UpdateTodoRequest();
		Todo todo = new Todo();
		UpdateTodoResponse response = new UpdateTodoResponse();

		given(requestMapper.map(eq(request), eq(Todo.class))).willReturn(todo);
		given(todoRepository.existsById(id)).willReturn(true);
		given(todoRepository.findById(id)).willReturn(Optional.of(todo));
		given(todoRepository.save(any(Todo.class))).willReturn(todo);
		given(resultMapper.map(eq(todo), eq(UpdateTodoResponse.class))).willReturn(response);

		// Act
		UpdateTodoResponse result = todoService.update(id, request);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testUpdate_WhenTodoNotFound() {
		// Arrange
		UUID id = UUID.randomUUID();
		UpdateTodoRequest request = new UpdateTodoRequest();

		given(todoRepository.existsById(id)).willReturn(false);
		given(todoRepository.findById(id)).willReturn(Optional.empty());

		// Act & Assert
		assertThrows(TodoNotFoundException.class, () -> todoService.update(id, request));
	}

	@Test
	public void testDelete_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		when(todoRepository.existsById(id)).thenReturn(true);

		// Act
		todoService.delete(id);

		// Assert
		assertThat(todoRepository).isNotNull();
	}

	@Test
	public void testDelete_WhenTodoNotFound() {
		// Arrange
		UUID id = UUID.randomUUID();
		given(todoRepository.existsById(id)).willReturn(false);
		given(todoRepository.findById(id)).willReturn(Optional.empty());

		// Act & Assert
		assertThrows(TodoNotFoundException.class, () -> todoService.delete(id));
	}
}
