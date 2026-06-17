package com.kh.six.confirm.plan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("confirm/plan")
public class ConfirmResultViewController {

    // 파라미터로 selectTable 받기
    @GetMapping()
    public String list(){
        return "confirm/plan/plan";
    }

}
