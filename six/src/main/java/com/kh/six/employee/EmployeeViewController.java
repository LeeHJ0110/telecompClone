package com.kh.six.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("employee")
@Slf4j
public class EmployeeViewController {

    @GetMapping("join")
    public void join(){}

    @GetMapping("login")
    public void login(){}

}