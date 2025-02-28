package com.test.api.marvel.persistence.integration.marvel.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterInfoDto;
import com.test.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.test.api.marvel.persistence.integration.marvel.dto.ThumbnailDto;

import java.util.ArrayList;
import java.util.List;

public class ComicMapper {

    public static List<ComicDto> toDtoList(JsonNode jsonNode) {
        ArrayNode results = extractResultsNode(jsonNode);

        List<ComicDto> comicsDtoList = new ArrayList<>();
        results.forEach(comic -> {
            comicsDtoList.add(toDto(comic));
        });

        return comicsDtoList;
    }

    public static ComicDto toDto(JsonNode comic) {
        if (comic == null) {
            throw new IllegalArgumentException("Comic es null");
        }

        ArrayNode results = extractResultsNode(comic);
        JsonNode comicNode = results.get(0);
        ComicDto comicDto = new ComicDto();

        comicDto.setId(comicNode.get("id").longValue());
        comicDto.setTitle(comicNode.get("title").asText());
        comicDto.setDescription(comicNode.get("description").asText());
        comicDto.setModified(comicNode.get("modified").asText());
        comicDto.setResourceURI(comicNode.get("resourceURI").asText());
        ThumbnailDto thumbnailDto = new ThumbnailDto();
        thumbnailDto.setPath(comicNode.get("thumbnail").get("path").asText());
        thumbnailDto.setExtension(comicNode.get("thumbnail").get("extension").asText());
        comicDto.setThumbnail(thumbnailDto);


        return comicDto;
    }

    private static ArrayNode extractResultsNode(JsonNode jsonNode) {

        if (jsonNode == null) {
            throw new IllegalArgumentException("Json es null");
        }

        JsonNode data = jsonNode.get("data");
        return (ArrayNode) data.get("results");
    }

    public static CharacterInfoDto toInfoDto(JsonNode response) {

        if (response == null) {
            throw new IllegalArgumentException("Response es null");
        }

        CharacterInfoDto characterInfoDtoDto = new CharacterInfoDto();

        characterInfoDtoDto.setDescription(response.get("description").textValue());
        characterInfoDtoDto.setImagePath(response.get("thumbnail").get("path").textValue());

        return characterInfoDtoDto;
    }

}
