package com.test.api.marvel.mapper;

import com.test.api.marvel.dto.GetUserInteracionLogDto;
import com.test.api.marvel.persistence.entity.UserInteractionLog;

public class UserInteractionLogMapper {

    public static GetUserInteracionLogDto toDto(UserInteractionLog userInteractionLog) {
        if (userInteractionLog == null) return null;

        GetUserInteracionLogDto getUserInteracionLogDto = new GetUserInteracionLogDto();

        getUserInteracionLogDto.setId(userInteractionLog.getId());
        getUserInteracionLogDto.setUrl(userInteractionLog.getUrl());
        getUserInteracionLogDto.setUsername(userInteractionLog.getUsername());
        getUserInteracionLogDto.setTimestamp(userInteractionLog.getTimestamp());
        getUserInteracionLogDto.setUrlMethod(userInteractionLog.getMethod());
        getUserInteracionLogDto.setRemoteAdress(userInteractionLog.getRemoteAddress());

        return getUserInteracionLogDto;
    }
}
