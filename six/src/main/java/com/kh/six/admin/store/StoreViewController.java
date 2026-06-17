package com.kh.six.admin.store;

import com.kh.six.employee.EmployeeVo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee/employeemanage/admin/store")
@Slf4j
public class StoreViewController {

    // 리스트 페이지
    @GetMapping("/list/{currentPage}")
    public String list(@PathVariable int currentPage, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        return "agency/agencylist";
    }

    // 상세 페이지
    @GetMapping("/detail/{no}")
    public String detail(@PathVariable String no, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        return "agency/agencydetail";
    }

    // 수정 페이지
    @GetMapping("/edit/{no}")
    public String edit(@PathVariable String no, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        return "agency/agencyedit";
    }

    // 등록 페이지
    @GetMapping("/write")
    public String write(HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        return "agency/agencywrite";
    }
}