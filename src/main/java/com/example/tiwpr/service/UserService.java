package com.example.tiwpr.service;

import com.example.tiwpr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void getAllUsersPageable(Pageable pageable) {
        userRepository.findAll(pageable);
    }
}
