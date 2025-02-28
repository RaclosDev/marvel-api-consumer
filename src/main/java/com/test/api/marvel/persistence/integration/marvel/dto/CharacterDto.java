package com.test.api.marvel.persistence.integration.marvel.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CharacterDto {

    private CharacterInfoDto characterInfoDto;
    private long id;
    private String name;
    private String description;
    private Date modified;
    private String resourceURI;
}
