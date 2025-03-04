package com.test.api.marvel.service.impl;

import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterInfoDto;
import com.test.api.marvel.persistence.integration.marvel.repository.CharacterRepository;
import com.test.api.marvel.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Override
    public List<CharacterDto> findAll(MyPageable pageable, String name, ArrayList<?> comics, ArrayList<?> series) {
        return characterRepository.findAll(pageable, name, comics, series);
    }

    @Override
    public CharacterInfoDto findInfoById(Long characterId) {
        return characterRepository.findInfoById(characterId);
    }
}
