package com.chrisloarryn.todolistapi.repository;

import com.chrisloarryn.todolistapi.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID>
{
}