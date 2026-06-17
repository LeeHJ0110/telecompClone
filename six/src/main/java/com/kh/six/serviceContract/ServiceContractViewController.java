package com.kh.six.serviceContract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("serviceContract")
@Slf4j
public class ServiceContractViewController {

    @GetMapping
    public String selectList(){
        return "serviceContract/selectList";
    }
}
