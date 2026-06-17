package com.kh.six.board.faq;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/faq")
@RequiredArgsConstructor
@Slf4j
public class FaqViewController {

    private final  FaqMapper faqMapper;

    @GetMapping("insert")
    public String insert(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null){
            throw new IllegalStateException("login first ...");
        }
        return "board/faq/insert";
    }

    @GetMapping("edit/{no}")
    public String edit(HttpSession session,@PathVariable String no, Model model){
        if(session.getAttribute("loginAdminVo") == null){
            throw new IllegalStateException("login first ...");
        }
        FaqVo vo = faqMapper.selectOne(no);
        model.addAttribute("vo", vo);
        return "board/faq/edit";
    }

    @GetMapping("list/{no}")
    public String list(@PathVariable int no){
        return "board/faq/list";
    }

}
