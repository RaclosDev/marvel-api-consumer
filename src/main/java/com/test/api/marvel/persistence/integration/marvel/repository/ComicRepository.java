package com.test.api.marvel.persistence.integration.marvel.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.MarvelAPIConfig;
import com.test.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.test.api.marvel.persistence.integration.marvel.mapper.ComicMapper;
import com.test.api.marvel.service.HttpClientService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ComicRepository {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private MarvelAPIConfig marvelAPIConfig;

    @Value("${marvel.api.base-path}")
    private String basePath;

    private String comicPath;

    @PostConstruct
    private void setCharacterPath() {
        comicPath = basePath + "/comics";
    }

    public List<ComicDto> findAll(MyPageable pageable, Long characterId) {
        Map<String, String> marvelQueryParams = new HashMap<>(marvelAPIConfig.getAuthParams());

        JsonNode response = httpClientService.doGet(comicPath, marvelQueryParams, JsonNode.class);

        return ComicMapper.toDtoList(response);
    }

    private Map<String, String> getQueryParamsForFindAll(MyPageable pageable, Long characterId) {
        Map<String, String> marvelQueryParams = new HashMap<>(marvelAPIConfig.getAuthParams());

        marvelQueryParams.put("offset", String.valueOf(pageable.offset()));
        marvelQueryParams.put("limit", String.valueOf(pageable.limit()));

        if (characterId != null) {
            marvelQueryParams.put("characters", String.valueOf(characterId));
        }

        return marvelQueryParams;
    }


    public ComicDto findById(Long comicId) {

        Map<String, String> marvelQueryParams = new HashMap<>(marvelAPIConfig.getAuthParams());

        String finalUrl = comicPath + "/" + comicId;

        JsonNode response = httpClientService.doGet(finalUrl, marvelQueryParams, JsonNode.class);

        return ComicMapper.toDto(response);
    }
}
