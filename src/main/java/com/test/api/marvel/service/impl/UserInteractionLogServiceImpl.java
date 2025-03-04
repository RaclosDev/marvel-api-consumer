package com.test.api.marvel.service.impl;

import com.test.api.marvel.dto.GetUserInteracionLogDto;
import com.test.api.marvel.mapper.UserInteractionLogMapper;
import com.test.api.marvel.persistence.repository.UserInteractionLogRepository;
import com.test.api.marvel.service.UserInteractionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInteractionLogServiceImpl implements UserInteractionLogService {

    private final UserInteractionLogRepository userInteractionLogRepository;


    @Override
    public Page<GetUserInteracionLogDto> findAll(Pageable pageable) {
        return userInteractionLogRepository.findAll(pageable).map(UserInteractionLogMapper::toDto);
    }

    @Override
    public Page<GetUserInteracionLogDto> findByUsername(Pageable pageable, String username) {
        return userInteractionLogRepository.findByUsername(pageable, username).map(UserInteractionLogMapper::toDto);
    }
}
