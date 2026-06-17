package com.kh.six.addService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("add-service")
public class AddServiceViewController {

    @GetMapping("list/{no}")
    public String selectList(){
        return "addService/asList";
    }

    @GetMapping("admin/update")
    public String update(){
        return "addService/asUpdate";
    }
    @GetMapping("admin/insert")
    public String insert(){
        return "addService/asInsert";
    }
}
