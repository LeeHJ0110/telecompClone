package com.kh.six.event;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("event")
public class EventViewController {

    @GetMapping("attendance")
    public String attendance(){
        return "event/attendance";
    }

}
