package com.tonythomasndm.store.users;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")// bcoz later we r honn apost, delete etc
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

// when we succesffully create a request we chould giev tehrb eponse of 201
    // methodargumentnOTVALIDEXCEPTION
    // pelaced with ? to make it more flexible
    @PostMapping
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {// include requets body to reocve the requetsb bodyelse null values in backend - common error
        // always do pone baby step at a time

        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(
                    Map.of("email","Email is already regsitered.")
            );
        }

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);// we dont have it in the usermapper techncially - bcoz it is magic implemenatation
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);// created giuve sa bopdybuilder
        //return userMapper.toDto(userRepository.save(userMapper.toEntity(request)));
    }// if we use invalid property, emailx instead of email, we get null, spring is unable to intialize
    // deserilaizationa dn serialization, id id null bcoz it was not provided
    // http response also ghave headers

    // u dont need to give bodydto - it is fine for internal apis- they donts et statutus 201
    // but for public apis - best tof oloow standard restful conventions

    // for updating the resource, we should set teh requets to put or patch - ref a particular resource
    // put for updtaing teh entire resource - often used
    // patch for updating or one or more properties only

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name="id") Long id,
            @RequestBody UpdateUserRequest request
            ){

        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        userMapper.updateEntity(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
           @PathVariable Long id
    ){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(name="id") Long id,
            @RequestBody ChangePasswordRequest request
    ){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword())){
            return new  ResponseEntity<>(HttpStatus.UNAUTHORIZED);//401
        }else{
            user.setPassword(request.getNewPassword());// we should sue mapper when dealing with alrge objects
            userRepository.save(user);
        }
        return ResponseEntity.noContent().build();//204
        // currently we are storing the apssword by text we should avoid it
    }

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