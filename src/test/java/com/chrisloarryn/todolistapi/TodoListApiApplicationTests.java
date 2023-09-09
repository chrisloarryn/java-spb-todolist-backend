package com.chrisloarryn.todolistapi;

import com.chrisloarryn.todolistapi.TodoListApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TodoListApiApplication.class)
@ActiveProfiles("test")
class TodoListApiApplicationTests {

	@Test
	public void contextLoads() {
		// This test method is empty because it is used to check if the Spring context
		// loads successfully.
	}
}
