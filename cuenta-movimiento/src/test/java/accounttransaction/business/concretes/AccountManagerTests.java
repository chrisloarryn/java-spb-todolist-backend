package accounttransaction.business.concretes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import accounttransaction.business.abstracts.AccountService;
import accounttransaction.business.concretes.AccountManager;
import accounttransaction.business.dto.requests.create.CreateAccountRequest;
import accounttransaction.business.dto.requests.update.UpdateAccountRequest;
import accounttransaction.business.dto.responses.create.CreateAccountResponse;
import accounttransaction.business.dto.responses.get.GetAllAccountsResponse;
import accounttransaction.business.dto.responses.get.GetAccountResponse;
import accounttransaction.business.dto.responses.update.UpdateAccountResponse;
import accounttransaction.business.rules.AccountBusinessRules;
import accounttransaction.configuration.mappers.ModelMapperConfig;
import accounttransaction.entities.Account;
import accounttransaction.entities.AccountNotFoundException;
import accounttransaction.repository.AccountRepository;
import accounttransaction.utils.mappers.ModelMapperManager;
import accounttransaction.utils.mappers.ModelMapperService;

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

public class AccountManagerTests {

	private AccountService todoService;

	@Mock
	private AccountRepository todoRepository;

	@Mock
	private ModelMapperConfig modelMapperConfig;

	@Mock
	private ModelMapperService mapper;

	private ModelMapper resultMapper;
	private ModelMapper requestMapper;

	@Mock
	private AccountBusinessRules todoBusinessRules;

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

		todoService = new AccountManager(todoRepository, modelMapperService, todoBusinessRules);
	}

	@Test
	public void testGetAll() {
		// Arrange
		List<Account> todoList = new ArrayList<>();
		given(todoRepository.findAll()).willReturn(todoList);

		// Act
		List<GetAllAccountsResponse> result = todoService.getAll();

		// Assert
		assertNotNull(result);
		assertEquals(0, result.size());
		assertThat(result).isNotNull();
	}

	@Test
	public void testGetById_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		Account todo = new Account();
		GetAccountResponse response = new GetAccountResponse();

		given(todoRepository.existsById(id)).willReturn(true);
		given(todoRepository.findById(id)).willReturn(Optional.of(todo));
		given(resultMapper.map(eq(todo), eq(GetAccountResponse.class))).willReturn(response);

		// Act
		GetAccountResponse result = todoService.getById(id);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testGetById_WhenTodoNotFound() {
		// Arrange
		UUID id = UUID.randomUUID();

		// Act & Assert
		assertThrows(AccountNotFoundException.class, () -> todoService.getById(id));
	}

	@Test
	public void testAdd_() {
		// Arrange
		CreateAccountRequest request = new CreateAccountRequest();
		Account todo = new Account();
		CreateAccountResponse response = new CreateAccountResponse();

		given(requestMapper.map(eq(request), eq(Account.class))).willReturn(todo);
		given(todoRepository.save(any(Account.class))).willReturn(todo);
		given(resultMapper.map(eq(todo), eq(CreateAccountResponse.class))).willReturn(response);

		// Act
		CreateAccountResponse result = todoService.add(request);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testUpdate_WhenTodoExists() {
		// Arrange
		UUID id = UUID.randomUUID();
		UpdateAccountRequest request = new UpdateAccountRequest();
		Account todo = new Account();
		UpdateAccountResponse response = new UpdateAccountResponse();

		given(requestMapper.map(eq(request), eq(Account.class))).willReturn(todo);
		given(todoRepository.existsById(id)).willReturn(true);
		given(todoRepository.findById(id)).willReturn(Optional.of(todo));
		given(todoRepository.save(any(Account.class))).willReturn(todo);
		given(resultMapper.map(eq(todo), eq(UpdateAccountResponse.class))).willReturn(response);

		// Act
		UpdateAccountResponse result = todoService.update(id, request);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testUpdate_WhenTodoNotFound() {
		// Arrange
		UUID id = UUID.randomUUID();
		UpdateAccountRequest request = new UpdateAccountRequest();

		given(todoRepository.existsById(id)).willReturn(false);
		given(todoRepository.findById(id)).willReturn(Optional.empty());

		// Act & Assert
		assertThrows(AccountNotFoundException.class, () -> todoService.update(id, request));
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
		assertThrows(AccountNotFoundException.class, () -> todoService.delete(id));
	}
}
