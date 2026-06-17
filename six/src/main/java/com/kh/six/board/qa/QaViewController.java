package com.kh.six.board.qa;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/qa")
@RequiredArgsConstructor
@Slf4j
public class QaViewController {

    @GetMapping("insert")
    public String insert(HttpSession session){
        if(session.getAttribute("loginMemberVo") == null){
            throw new IllegalStateException("login first ...");
        }
        return "board/qa/insert";
    }

    @GetMapping("edit/{no}")
    public String edit(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null){
            throw new IllegalStateException("login first ...");
        }
        return "board/qa/edit";
    }

    @GetMapping("list/{no}")
    public String list(){
        return "board/qa/list";
    }

    @GetMapping("detail/{no}")
    public String detail(){
        return "board/qa/detail";
    }
}
