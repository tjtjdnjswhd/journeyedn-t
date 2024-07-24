package com.example.journeyednt.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }

    public void updateRole(String name) {
        this.name = name;
    }
}
