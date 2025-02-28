package com.test.api.marvel.service;

import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterInfoDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CharacterService {
    List<CharacterDto> findAll(MyPageable pageable, String name, ArrayList<?> comics, ArrayList<?> series);
    CharacterInfoDto findInfoById(Long characterId);
}
