package com.test.api.marvel.persistence.integration.marvel.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterInfoDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CharacterMapper {

    public static List<CharacterDto> toDtoList(JsonNode jsonNode) {
        ArrayNode results = extractResultsNode(jsonNode);

        List<CharacterDto> charactersDtoList = new ArrayList<>();
        results.forEach(character -> {
            charactersDtoList.add(toDto(character));
        });

        return charactersDtoList;
    }

    private static CharacterDto toDto(JsonNode character) {
        if (character == null) {
            throw new IllegalArgumentException("Character es null");
        }

        CharacterDto characterDto = new CharacterDto();

        characterDto.setId(character.get("id").longValue());
        characterDto.setName(character.get("name").textValue());
        characterDto.setDescription(character.get("description").textValue());
        characterDto.setModified(new Date(character.get("modified").asLong()));
        characterDto.setResourceURI(character.get("resourceURI").textValue());

        return characterDto;
    }

    private static ArrayNode extractResultsNode(JsonNode jsonNode) {

        if (jsonNode == null) {
            throw new IllegalArgumentException("Json es null");
        }

        JsonNode data = jsonNode.get("data");
        return (ArrayNode) data.get("results");
    }

    public static CharacterInfoDto toInfoDto(JsonNode response) {
        ArrayNode results = extractResultsNode(response);

        JsonNode characterNode = results.get(0);

        CharacterInfoDto characterInfoDto = new CharacterInfoDto();

        characterInfoDto.setDescription(characterNode.get("description").textValue());

        String path = characterNode.get("thumbnail").get("path").textValue();
        String extension = characterNode.get("thumbnail").get("extension").textValue();
        characterInfoDto.setImagePath(path + "." + extension);

        return characterInfoDto;
    }
}
