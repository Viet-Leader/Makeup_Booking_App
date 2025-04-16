package com.example.bookingmakeup.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class ReceptionistAccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String role = (String) session.getAttribute("role");
            if ("receptionist".equals(role)) {
                return true;
            } else {
                session.setAttribute("accessDeniedMessage", "Bạn không có quyền truy cập vào trang lễ tân!");
            }
        }

        response.sendRedirect("/home");
        return false;
    }
}
