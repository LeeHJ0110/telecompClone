package com.kh.six.admin.counsel;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee/counsel")
@RequiredArgsConstructor
@Slf4j
public class CounselViewController {

    @GetMapping("insert")
    public String insert(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null && session.getAttribute("loginEmployeeVo") == null){
            throw new IllegalStateException("login first ...");
        }
        return "admin/counsel/insert";
    }

    @GetMapping("/list/{no}")
    public String list(HttpSession session) {
        if(session.getAttribute("loginAdminVo") == null && session.getAttribute("loginEmployeeVo") == null){

            throw new IllegalStateException("login first ...");
        }
        return "admin/counsel/list";
    }
}
