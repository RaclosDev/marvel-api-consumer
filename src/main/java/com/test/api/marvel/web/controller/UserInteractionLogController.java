package com.test.api.marvel.web.controller;

import com.test.api.marvel.dto.GetUserInteracionLogDto;
import com.test.api.marvel.service.UserInteractionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("user-interactions")
public class UserInteractionLogController {

    private final UserInteractionLogService userInteractionLogService;

    @GetMapping("findAll")
    public ResponseEntity<Page<GetUserInteracionLogDto>> findAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = buildPageable(offset, limit);

        return ResponseEntity.ok(userInteractionLogService.findAll(pageable));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<Page<GetUserInteracionLogDto>> findByUsername(
            @PathVariable String userName,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Pageable pageable = buildPageable(offset, limit);

        return ResponseEntity.ok(userInteractionLogService.findByUsername(pageable, userName));
    }


    private static Pageable buildPageable(int offset, int limit) {
        Pageable pageable;
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be less than 0");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit cannot be less than 0");
        }

        if (offset == 0) pageable = PageRequest.of(0, limit);
        else pageable = PageRequest.of(offset / limit, limit);
        return pageable;
    }

}
