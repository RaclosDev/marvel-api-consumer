package com.test.api.marvel.controller;

import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.test.api.marvel.persistence.integration.marvel.dto.CharacterInfoDto;
import com.test.api.marvel.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    CharacterService characterService;

    @PreAuthorize("hasAnyAuthority('character:read-all')")
    @GetMapping
    public ResponseEntity<List<CharacterDto>> findAll(@RequestParam(required = false) String name, @RequestParam(required = false) ArrayList<?> comics, @RequestParam(required = false) ArrayList<?> series, @RequestParam(defaultValue = "0") long offset, @RequestParam(defaultValue = "10") long limit) {
        MyPageable pageable = new MyPageable(offset, limit);

        return ResponseEntity.ok(characterService.findAll(pageable, name, comics, series));
    }

    @PreAuthorize("hasAnyAuthority('character:read-detail')")
    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterInfoDto> findInfoById(@PathVariable Long characterId) {
        return ResponseEntity.ok(characterService.findInfoById(characterId));
    }

}
