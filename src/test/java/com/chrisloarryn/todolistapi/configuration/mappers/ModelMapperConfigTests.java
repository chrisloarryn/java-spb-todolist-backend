package com.chrisloarryn.todolistapi.configuration.mappers;

import com.chrisloarryn.todolistapi.configuration.mappers.ModelMapperConfig;
import com.chrisloarryn.todolistapi.utils.mappers.ModelMapperManager;
import com.chrisloarryn.todolistapi.utils.mappers.ModelMapperService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelMapperConfigTests {

	private final ModelMapperConfig modelMapperConfig;

	public ModelMapperConfigTests() {
		this.modelMapperConfig = new ModelMapperConfig();
	}

	@Test
	public void testModelMapperBeanCreation() {
		ModelMapper modelMapper = modelMapperConfig.getModelMapper();
		assertNotNull(modelMapper);
	}

	@Test
	public void testModelMapperServiceBeanCreation() {
		ModelMapperService modelMapperService = modelMapperConfig.getModelMapperService(modelMapperConfig.getModelMapper());
		assertNotNull(modelMapperService);
		assertTrue(modelMapperService instanceof ModelMapperManager);
	}
}
