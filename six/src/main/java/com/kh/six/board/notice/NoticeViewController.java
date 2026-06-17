package com.kh.six.board.notice;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/board/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeViewController {


    @GetMapping("insert")
    public String insert(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null){
            throw new IllegalStateException("login first ...");
        }
        return "board/notice/insert";
    }

    @GetMapping("edit/{no}")
    public String edit(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null){
            throw new IllegalStateException("login first ...");
        }
        return "board/notice/edit";
    }

    @GetMapping("list/{no}")
    public String list(){
        return "board/notice/list";
    }

    @GetMapping("detail/{no}")
    public String detail(){
        return "board/notice/detail";
    }



}