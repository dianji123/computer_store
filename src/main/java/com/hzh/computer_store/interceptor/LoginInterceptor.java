package com.hzh.computer_store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 拦截器的作用是将所有的请求统一拦截到拦截器中,可以在拦截器中定义过滤的规则,
 * 如果不满足系统设置的过滤规则,该项目统一的处理是重新去打开login.html页面(重定向和转发都可以,推荐使用重定向)*/
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * preHandler
     * 调用时间：Controller方法处理之前
    执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序一个接一个执行
    注意事项：若返回false，则中断执行，不会进入afterCompletion（调用时间：响应提交给客户端之后）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }

}
