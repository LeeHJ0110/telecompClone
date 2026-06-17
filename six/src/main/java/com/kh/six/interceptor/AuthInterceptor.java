package com.kh.six.interceptor;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.member.MemberVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        EmployeeVo admin = session == null ? null : (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo employee = session == null ? null : (EmployeeVo) session.getAttribute("loginEmployeeVo");
        MemberVo member = session == null ? null : (MemberVo) session.getAttribute("loginMemberVo");
        boolean isLogin = admin != null || employee != null || member != null;

        if(!isLogin){
            response.sendRedirect("/error/401");
            return false;
        }

        // admin 페이지 접근
        if(uri.contains("/admin/")){
            if(admin == null){
                response.sendRedirect("/error/403"); // 권한 없음
                return false;
            }
        }


        // employee 페이지 접근
        if(uri.contains("/employee/")){
            if(employee == null && admin == null){
                response.sendRedirect("/error/403");
                return false;
            }
        }

        // member 페이지 접근
        if(uri.contains("/mypage/")){
            if(member == null){
                response.sendRedirect("/error/403");
                return false;
            }
        }

        return true;
    }
}
