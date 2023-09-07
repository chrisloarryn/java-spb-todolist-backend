package com.chrisloarryn.todolistapi.entities;

import com.chrisloarryn.todolistapi.entities.enums.Priority;
import com.chrisloarryn.todolistapi.entities.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity //imzalama işlemi -> kısıt kullanılmış oluyor
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private Date dueDate;
    private State state;

    private Boolean active;
    private Priority priority;
    private String author;
}
