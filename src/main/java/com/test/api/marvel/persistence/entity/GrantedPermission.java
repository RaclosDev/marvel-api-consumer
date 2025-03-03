package com.test.api.marvel.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GrantedPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne()
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
