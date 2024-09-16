package io.github.alicarpio.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "user_nt")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
