package com.liangzc.demo.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "ClassFilter", urlPatterns = "/*")
public class ClassFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       log.info("ClassFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        log.info("=== ClassFilter 收到请求：{} {} ===", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("ClassFilter 销毁");
    }
}
