package com.kh.six.confirm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("confirm/service")
@Slf4j
public class ServiceConfirmViewController {

    // 파라미터로 selectTable 받기
    @GetMapping()
    public String list(){
        return "confirm/service/service";
    }

    @GetMapping("detail")
    public void detail(){}
}
