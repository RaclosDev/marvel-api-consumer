package com.test.api.marvel.persistence.entity;

import jakarta.persistence.*;

public class GrantedPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany()
    @JoinColumn(name = "permission_id")
    private Permission permission;

}
