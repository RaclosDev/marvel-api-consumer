package com.test.api.marvel.persistence.integration.marvel.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.MarvelAPIConfig;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterInfoDto;
import com.test.api.marvel.persistence.integration.marvel.mapper.CharacterMapper;
import com.test.api.marvel.service.HttpClientService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CharacterRepository {

    @Autowired
    private MarvelAPIConfig marvelAPIConfig;

    @Autowired
    private HttpClientService httpClientService;

    @Value("${marvel.api.base-path}")
    private String basePath;

    private String characterPath;

    @PostConstruct
    private void setCharacterPath() {
        characterPath = basePath + "/characters";
    }

    public List<CharacterDto> findAll(MyPageable pageable, String name, ArrayList<?> comics, ArrayList<?> series) {

        Map<String, String> marvelQueryParams = getQueryParamsForFindAll(pageable, name, comics, series);

        JsonNode response = httpClientService.doGet(characterPath, marvelQueryParams, JsonNode.class);


        return CharacterMapper.toDtoList(response);
    }

    private Map<String, String> getQueryParamsForFindAll(MyPageable pageable, String name, ArrayList<?> comics, ArrayList<?> series) {
        Map<String, String> marvelQueryParams = new HashMap<>(marvelAPIConfig.getAuthParams());

        marvelQueryParams.put("offset", String.valueOf(pageable.offset()));
        marvelQueryParams.put("limit", String.valueOf(pageable.limit()));

        if (name != null) {
            marvelQueryParams.put("name", name);
        }
        if (comics != null && !comics.isEmpty()) {
            marvelQueryParams.put("comics", comics.stream().map(Object::toString).collect(Collectors.joining(",")));
        }
        if (series != null && !series.isEmpty()) {
            marvelQueryParams.put("series", series.stream().map(Object::toString).collect(Collectors.joining(",")));
        }

        return marvelQueryParams;
    }


    public CharacterInfoDto findInfoById(Long characterId) {
        Map<String, String> marvelQueryParams = new HashMap<>(marvelAPIConfig.getAuthParams());

        String finalUrl = characterPath + "/" + characterId;

        JsonNode response = httpClientService.doGet(finalUrl, marvelQueryParams, JsonNode.class);

        return CharacterMapper.toInfoDto(response);
    }
}
