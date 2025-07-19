package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.dtos.UserDto;
import com.tonythomasndm.store.entities.User;
import com.tonythomasndm.store.mappers.UserMapper;
import com.tonythomasndm.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/users")// bcoz later we r honn apost, delete etc
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // @RequestMapping("/users") - it by deafult uses get mapping
    @GetMapping
    // methoDs: GET, POST, PUT, DELETE
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();// static factpory methods - further cutomize the headers
        }

        // return new ResponseEntity<>(user, HttpStatus.OK);
        return ResponseEntity.ok(userMapper.toDto(user));

        // by dfeaiu;lt it is tsring
        // by default spring onverts that id string into long, error if it is not conevrrtable
        // null if id not present - not restful, by convention it should be 404
    }
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Long id) {
//        return userRepository.findById(id).orElse(null);
//        // by dfeaiu;lt it is tsring
//        // by default spring onverts that id string into long, error if it is not conevrrtable
//        // null if id not present - not restful, by convention it should be 404
//    }
}
// keep apis as stable as possible - verisoningh
// mapping libareis - model ampper and mapstruct
// modelmapper - reflection, slower, harder to debug
// maspstruct - balzinf fast, time save, free of runbtime overheads, makes mapping code at compielr itme