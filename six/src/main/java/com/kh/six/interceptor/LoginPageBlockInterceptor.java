package com.kh.six.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginPageBlockInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if(session != null){

            Object admin = session.getAttribute("loginAdminVo");
            Object employee = session.getAttribute("loginEmployeeVo");
            Object member = session.getAttribute("loginMemberVo");

            if(admin != null){
                response.sendRedirect("/admin");
                return false;
            }

            if(employee != null){
                response.sendRedirect("/employee");
                return false;
            }

            if(member != null){
                response.sendRedirect("/");
                return false;
            }
        }

        return true;
    }
}
