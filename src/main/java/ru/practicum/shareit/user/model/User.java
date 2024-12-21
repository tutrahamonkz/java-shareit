package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Builder
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    //@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "name", nullable = false)
    private String name;

    //@Column(name = "email", nullable = false, unique = true)
    private String email;
}