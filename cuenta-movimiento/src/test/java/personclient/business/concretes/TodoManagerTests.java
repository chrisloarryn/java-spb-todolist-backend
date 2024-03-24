package personclient.business.concretes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import personclient.business.abstracts.ClientService;
import personclient.business.dto.requests.create.CreateClientRequest;
import personclient.business.dto.requests.update.UpdateClientRequest;
import personclient.business.dto.responses.create.CreateClientResponse;
import personclient.business.dto.responses.get.GetAllClientsResponse;
import personclient.business.dto.responses.get.GetClientResponse;
import personclient.business.dto.responses.update.UpdateClientResponse;
import personclient.business.rules.ClientBusinessRules;
import personclient.configuration.mappers.ModelMapperConfig;
import personclient.entities.Client;
import personclient.entities.ClientNotFoundException;
import personclient.repository.ClientRepository;
import personclient.utils.mappers.ModelMapperManager;
import personclient.utils.mappers.ModelMapperService;

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

	private ClientService todoService;

	@Mock
	private ClientRepository todoRepository;

	@Mock
	private ModelMapperConfig modelMapperConfig;

	@Mock
	private ModelMapperService mapper;

	private ModelMapper resultMapper;
	private ModelMapper requestMapper;

	@Mock
	private ClientBusinessRules todoBusinessRules;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		ModelMapper mapper = mock(ModelMapper.class);
		Configuration configuration = mock(Configuration.class);

		given(configuration.setAmbiguityIgnored(true)).willReturn(configuration);
		given(configuration.setMatchingStrategy(MatchingStrategies.LOOSE)).willReturn(configuration);
		given(mapper.getConfiguration()).willReturn(configuration);

		ModelMapperService modelMapperService = new ModelMapperManager(mapper);
		resultMapper = modelMapperService.forResponse();

		given(configuration.setMatchingStrategy(MatchingStrategies.STANDARD)).willReturn(configuration);
		given(mapper.getConfiguration()).willReturn(configuration);

		ModelMapperService modelMapperServiceReq = new ModelMapperManager(mapper);
		requestMapper = modelMapperServiceReq.forRequest();

		todoService = new ClientManager(todoRepository, modelMapperService, todoBusinessRules);
	}

	@Test
	public void testGetAll() {
		// Arrange
		List<Client> todoList = new ArrayList<>();
		given(todoRepository.findAll()).willReturn(todoList);

		// Act
		List<GetAllClientsResponse> result = todoService.getAll();

		// Assert
		assertNotNull(result);
		assertEquals(0, result.size());
		assertThat(result).isNotNull();
	}

	@Test
	public void testGetById_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		Client todo = new Client();
		GetClientResponse response = new GetClientResponse();

		given(todoRepository.existsById(id)).willReturn(true);
		given(todoRepository.findById(id)).willReturn(Optional.of(todo));
		given(resultMapper.map(eq(todo), eq(GetClientResponse.class))).willReturn(response);

		// Act
		GetClientResponse result = todoService.getById(id);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testGetById_WhenTodoNotFound() {
		// Arrange
		UUID id = UUID.randomUUID();

		// Act & Assert
		assertThrows(ClientNotFoundException.class, () -> todoService.getById(id));
	}

	@Test
	public void testAdd_() {
		// Arrange
		CreateClientRequest request = new CreateClientRequest();
		Client todo = new Client();
		CreateClientResponse response = new CreateClientResponse();

		given(requestMapper.map(eq(request), eq(Client.class))).willReturn(todo);
		given(todoRepository.save(any(Client.class))).willReturn(todo);
		given(resultMapper.map(eq(todo), eq(CreateClientResponse.class))).willReturn(response);

		// Act
		CreateClientResponse result = todoService.add(request);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testUpdate_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		UpdateClientRequest request = new UpdateClientRequest();
		Client todo = new Client();
		UpdateClientResponse response = new UpdateClientResponse();

		given(requestMapper.map(eq(request), eq(Client.class))).willReturn(todo);
		given(todoRepository.existsById(id)).willReturn(true);
		given(todoRepository.findById(id)).willReturn(Optional.of(todo));
		given(todoRepository.save(any(Client.class))).willReturn(todo);
		given(resultMapper.map(eq(todo), eq(UpdateClientResponse.class))).willReturn(response);

		// Act
		UpdateClientResponse result = todoService.update(id, request);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testUpdate_WhenTodoNotFound() {
		// Arrange
		UUID id = UUID.randomUUID();
		UpdateClientRequest request = new UpdateClientRequest();

		given(todoRepository.existsById(id)).willReturn(false);
		given(todoRepository.findById(id)).willReturn(Optional.empty());

		// Act & Assert
		assertThrows(ClientNotFoundException.class, () -> todoService.update(id, request));
	}

	@Test
	public void testDelete_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		given(todoRepository.existsById(id)).willReturn(true);

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
		assertThrows(ClientNotFoundException.class, () -> todoService.delete(id));
	}
}
