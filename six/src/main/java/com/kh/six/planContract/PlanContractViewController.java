package com.kh.six.planContract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("planContract")
public class PlanContractViewController {

    @GetMapping("/mypage/history")
    public String viewHistory(){
        return "/planContract/history";
    }
}
