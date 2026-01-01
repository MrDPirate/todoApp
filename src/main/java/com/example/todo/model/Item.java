package com.example.todo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
}
