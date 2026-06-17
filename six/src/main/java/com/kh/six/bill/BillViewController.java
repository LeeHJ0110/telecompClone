package com.kh.six.bill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("bill")
@Slf4j
public class BillViewController {

    @GetMapping
    public String bill(){
        return "/bill/bill";
    }

}
