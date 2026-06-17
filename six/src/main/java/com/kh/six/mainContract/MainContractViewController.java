package com.kh.six.mainContract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main-contract")
@Slf4j
public class MainContractViewController {
    @GetMapping("admin/insert")
    public String insert(){
        return "mainContract/mcInsert";
    }

    @GetMapping("list")
    public String slectList(){
        return "mainContract/mcList";
    }
    @GetMapping("admin/update")
    public String update(){
        return "mainContract/mcUpdate";
    }


}
