package com.test.api.marvel.service;

import com.test.api.marvel.dto.GetUserInteracionLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface UserInteractionLogService {
    Page<GetUserInteracionLogDto> findAll(Pageable pageable);

    Page<GetUserInteracionLogDto> findByUsername(Pageable pageable, String username);
}
