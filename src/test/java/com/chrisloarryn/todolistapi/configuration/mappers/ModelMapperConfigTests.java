package com.chrisloarryn.todolistapi.configuration.mappers;

import com.chrisloarryn.todolistapi.configuration.mappers.ModelMapperConfig;
import com.chrisloarryn.todolistapi.utils.mappers.ModelMapperManager;
import com.chrisloarryn.todolistapi.utils.mappers.ModelMapperService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ModelMapperConfigTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testModelMapperBeanCreation() {
		ModelMapper modelMapper = applicationContext.getBean(ModelMapper.class);
		assertNotNull(modelMapper);
	}

	@Test
	public void testModelMapperServiceBeanCreation() {
		ModelMapperService modelMapperService = applicationContext.getBean(ModelMapperService.class);
		assertNotNull(modelMapperService);

		// Ensure that the ModelMapperManager is used, which depends on the ModelMapper
		// bean.
		assertTrue(modelMapperService instanceof ModelMapperManager);
	}
}
