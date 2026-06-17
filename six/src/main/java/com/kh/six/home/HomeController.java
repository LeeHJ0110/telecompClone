package com.kh.six.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping(value = {"/home" , "/"})
    public String home() {
        return "home";
    }

    @GetMapping("map")
    public String map(){
        return "common/map";
    }

    @GetMapping("event")
    public String event(){
        return "common/event";
    }
    @GetMapping("qr")
    public String work(){
        return "employee/qr";
    }
}
