package com.kh.six.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
@Slf4j
public class MemberViewController {
    @GetMapping("join")
    public String join(){
        return "/member/memberJoin";
    }

    @GetMapping("login")
    public String login(){
        return "/member/memberLogin";
    }


}
