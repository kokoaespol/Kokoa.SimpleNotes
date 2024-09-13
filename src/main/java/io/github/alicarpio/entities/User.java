package io.github.alicarpio.entities;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class User {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;

    public User(String name, String surname, String email, String password) {
        id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
