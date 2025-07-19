package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.dtos.UserDto;
import com.tonythomasndm.store.entities.User;
import com.tonythomasndm.store.mappers.UserMapper;
import com.tonythomasndm.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/users")// bcoz later we r honn apost, delete etc
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

//    // @RequestMapping("/users") - it by deafult uses get mapping
//    @GetMapping
//    // methoDs: GET, POST, PUT, DELETE
//    public Iterable<UserDto> getAllUsers() {
//        return userRepository.findAll()
//                .stream()
//                .map(userMapper::toDto)
//                .toList();
//    }
// get is for geteing data only
    // for sending actual data we need post - 3rd way to sned data  - request bpdy
    // opften used for creatinga nd updating objects - we dont need query oparmanter , mpost common type - json
    // 405 method not allowed - bcoz we havent implemetd the rnpoint taht reciveds post request


    @PostMapping
    public UserDto createUser(@RequestBody UserDto data) {// include requets body to reocve the requetsb bodyelse null values in backend - common error
        return data;
    }// if we use emailx instead of email, we get null, spring is unable to intialize
    // deserilaizationa dn serialization

    @GetMapping
    public Iterable<UserDto> getSortedUsers(
            //@RequestHeader(name= "x-auth-token", required = false) String authToken,// header variable si not vcase sensitive
            // by default it is necssray else abd request error 400, requird is false, it is optional
            @RequestParam(required = false, defaultValue = "", name="sort") String sort
    ) {
        //System.out.println(authToken);
        if (!Set.of("name","email").contains(sort)){
            sort = "name";
        }

        return userRepository.findAll(Sort.by(sort).ascending())
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

// if u chnsage the query paraneter -name

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

// query parameters - from apin request - in[put to a[pis, key vlue pairs, added to url
// type cna be anything covertible - runtime excep
// overloads - jparesposritoy
// string proerties -sort argumentr
// 500 error invalid error - valdiate it
// be deauflt it is requoied - else we bget abd request - 400
// we cna make it optriuonal - requied = fa;lse  we have runt iem excep 500 - null - internal error
// deafult value ="", sort provided no value ,

//prblem - renamed the parameter, still sorted by name, - null, thereofre, best pratcis always set teh anem attrivute
// so that our code doesnt break
// we preficx custom headers : x-