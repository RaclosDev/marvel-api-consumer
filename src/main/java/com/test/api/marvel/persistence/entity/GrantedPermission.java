package com.test.api.marvel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // Importa esta clase
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
    @JsonBackReference // Evita la serialización recursiva en esta relación
    private Role role;

    @ManyToOne()
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @Override
    public String toString() {
        return "GrantedPermission{id=" + id + ", permission=" + (permission != null ? permission.getName() : "null") + "}";
    }

}
