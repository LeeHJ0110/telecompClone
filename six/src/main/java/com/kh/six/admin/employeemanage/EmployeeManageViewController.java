package com.kh.six.admin.employeemanage;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/employee/employeemanage")
@RequiredArgsConstructor
@Slf4j
public class EmployeeManageViewController {

    @GetMapping("/admin/insert")
    public String insert(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null && session.getAttribute("loginEmployeeVo") == null){

            throw new IllegalStateException("login first ...");
        }
        return "admin/employeemanage/insert";
    }

    @GetMapping("/edit/{no}")
    public String edit(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null && session.getAttribute("loginEmployeeVo") == null){

            throw new IllegalStateException("login first ...");
        }
        return "admin/employeemanage/edit";
    }

    @GetMapping("/list/{no}")
    public String list(HttpSession session) {
        if(session.getAttribute("loginAdminVo") == null && session.getAttribute("loginEmployeeVo") == null){

            throw new IllegalStateException("login first ...");
        }
        return "admin/employeemanage/list";
    }

    @GetMapping("/detail/{no}")
    public String detail(HttpSession session){
        if(session.getAttribute("loginAdminVo") == null && session.getAttribute("loginEmployeeVo") == null){

            throw new IllegalStateException("login first ...");
        }
        return "admin/employeemanage/detail";
    }
}
