package com.test.api.marvel.controller;

import com.test.api.marvel.dto.MyPageable;
import com.test.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.test.api.marvel.service.ComicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comics")
public class ComicController {

    ComicService comicService;

    @PreAuthorize("hasAnyAuthority('comic:read-all')")
    @GetMapping()
    public ResponseEntity<List<ComicDto>> findAll(@RequestParam(required = false) Long characterId, @RequestParam(defaultValue = "0") long offset, @RequestParam(defaultValue = "10") long limit) {
        MyPageable pageable = new MyPageable(offset, limit);

        return ResponseEntity.ok(comicService.findAll(pageable, characterId));
    }

    @PreAuthorize("hasAnyAuthority('comic:read-by-id')")
    @GetMapping("/{comicId}")
    public ResponseEntity<ComicDto> findById(@PathVariable Long comicId) {
        return ResponseEntity.ok(comicService.findById(comicId));
    }

}
