package com.test.api.marvel.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UserInteractionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 750)
    private String url;
    private String method;
    private String username;
    private LocalDateTime timestamp;
    private String remoteAddress;

}
