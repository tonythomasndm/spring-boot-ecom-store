package com.tonythomasndm.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller//this is bean
public class HomeController {
    @RequestMapping("/")
    public String index(Model model){// homepage
        model.addAttribute("name", "Tony");
        return "index";//view
    }

//    @RequestMapping("/hello")// ssr working
//    public String sayHello(){
//        return "hello.html";// static html page - no synamic content
//    }
}
