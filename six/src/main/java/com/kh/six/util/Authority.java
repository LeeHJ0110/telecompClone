package com.kh.six.util;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.member.MemberVo;
import jakarta.servlet.http.HttpSession;

public class Authority {
    public static void checkAdmin(HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        if (loginAdminVo == null) {
            throw new IllegalStateException("ADMIN ONLY");
        }
    }
    public static void checkEmployee(HttpSession session){
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        if (loginEmployeeVo == null) {
            throw new IllegalStateException("EMPLOYEE ONLY");
        }
    }
    public static void checkMember(HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        if (loginMemberVo == null) {
            throw new IllegalStateException("LOGIN USER ONLY");
        }
    }
}
