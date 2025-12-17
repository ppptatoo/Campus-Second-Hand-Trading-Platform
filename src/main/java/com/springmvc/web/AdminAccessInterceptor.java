package com.springmvc.web;

import com.springmvc.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lvr1997
 */
public class AdminAccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session
                = request.getSession();
        Object adminObj = session.getAttribute("admin");
        if (adminObj == null) {
            //发起重定向
            String path="/admin/toLogin";
            response.sendRedirect(path);
            return false;//返回false不再执行后续的控制器
        }
        
        // 额外检查：确保 admin 对象确实拥有管理员权限（power > 50）
        User admin = (User) adminObj;
        if (admin.getPower() == null || admin.getPower() <= 50) {
            // 权限不足
            String path="/admin/toLogin";
            response.sendRedirect(path);
            return false;
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
