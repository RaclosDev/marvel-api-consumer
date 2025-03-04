package com.test.api.marvel.persistence.integration.marvel.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.test.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.test.api.marvel.persistence.integration.marvel.dto.ThumbnailDto;

import java.util.ArrayList;
import java.util.List;

public class ComicMapper {

    public static List<ComicDto> toDtoList(JsonNode rootNode) {
        ArrayNode resultsNode = getResultsNode(rootNode);

        List<ComicDto> comics = new ArrayList<>();
        resultsNode.elements().forEachRemaining(each -> comics.add(ComicMapper.toDto(each)));

        return comics;
    }

    public static ComicDto toDto(JsonNode comicNode) {
        if (comicNode == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }
        ComicDto comicDto = new ComicDto();

        ThumbnailDto thumbnailDto = new ThumbnailDto();
        thumbnailDto.setPath(comicNode.get("thumbnail").get("path").asText());
        thumbnailDto.setExtension(comicNode.get("thumbnail").get("extension").asText());

        comicDto.setThumbnail(thumbnailDto);
        comicDto.setId(comicNode.get("id").asLong());
        comicDto.setTitle(comicNode.get("title").asText());
        comicDto.setDescription(comicNode.get("description").asText());
        comicDto.setModified(comicNode.get("modified").asText());
        comicDto.setResourceURI(comicNode.get("resourceURI").asText());

        return comicDto;
    }


    private static ArrayNode getResultsNode(JsonNode rootNode) {
        if (rootNode == null) {
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        JsonNode dataNode = rootNode.get("data");
        return (ArrayNode) dataNode.get("results");
    }
}