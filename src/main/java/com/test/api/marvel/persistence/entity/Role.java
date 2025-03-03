package com.test.api.marvel.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<GrantedPermission> grantedPermissions;

    public enum RoleEnum {
        ROLE_USER, ROLE_ADMIN
    }
}
