package com.test.api.marvel.persistence.integration.marvel.dto;

import lombok.Data;

@Data
public class ComicDto {
    private Long id;
    private String title;
    private String description;
    private String modified;
    private String resourceURI;
    private ThumbnailDto thumbnail;
}
