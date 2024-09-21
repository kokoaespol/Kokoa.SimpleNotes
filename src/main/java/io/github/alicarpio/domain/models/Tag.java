package io.github.alicarpio.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Table(name = "tag")
@Entity
public class Tag {

    @Id
    @Column(name = "tag_id")
    private int id;

    private String name;

    private String color;

    @ManyToMany(mappedBy = "tags")
    private List<Note> notes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
