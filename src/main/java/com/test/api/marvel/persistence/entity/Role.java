package com.test.api.marvel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    @JsonManagedReference  // Esto indica que esta relación será serializada
    private List<GrantedPermission> permissions;

    @Override
    public String getAuthority() {
        if (name == null) return null;
        return "ROLE_" + name.name();
    }

    public enum RoleEnum {
        CUSTOMER, AUDITOR
    }
    @Override
    public String toString() {
        return "Role{id=" + id + ", name=" + name + "}";
    }

}
