package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @RequestMapping("/hello")
    public Message sayHello(){
        return new Message("Hello World");
    }
}
//sb automatically coibevrts java obj to json
// apis are preferred