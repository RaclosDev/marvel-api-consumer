package com.test.api.marvel.service.impl;

import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.test.api.marvel.persistence.integration.marvel.repository.ComicRepository;
import com.test.api.marvel.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicServiceImpl implements ComicService {

    @Autowired
    ComicRepository comicRepository;

    @Override
    public List<ComicDto> findAll(MyPageable pageable, Long characterId) {
        return comicRepository.findAll(pageable, characterId);
    }

    @Override
    public ComicDto findById(Long comicId) {
        return comicRepository.findById(comicId);
    }
}
