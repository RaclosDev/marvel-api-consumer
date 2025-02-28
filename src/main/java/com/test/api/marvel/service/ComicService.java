package com.test.api.marvel.service;

import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.dto.ComicDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComicService {
    List<ComicDto> findAll(MyPageable pageable, Long characterId);

    ComicDto findById(Long comicId);
}
