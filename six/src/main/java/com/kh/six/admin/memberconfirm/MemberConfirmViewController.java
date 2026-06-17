package com.kh.six.admin.memberconfirm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee/membermanage/confirm-list")
@RequiredArgsConstructor
@Slf4j
public class MemberConfirmViewController {

    @GetMapping("/{no}")
    public String list(@PathVariable String no, HttpSession session) {
        if (session.getAttribute("loginAdminVo") == null
                && session.getAttribute("loginEmployeeVo") == null) {
            throw new IllegalStateException("login first ...");
        }

        log.info("confirm list view page no = {}", no);

        return "admin/memberconfirm/list";
    }
}
