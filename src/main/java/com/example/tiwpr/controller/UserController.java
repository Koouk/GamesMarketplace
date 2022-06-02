package com.example.tiwpr.controller;

import com.example.tiwpr.dto.UserDto;
import com.example.tiwpr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserDto> getAllUsers(Pageable pageable) {
        userService.getAllUsersPageable(pageable);
        return null;
    }

    @PostMapping
    public ResponseEntity<UserDto> createNewUser(@RequestParam String token, @RequestBody @Valid UserDto user) {

        return null;
    }
}
