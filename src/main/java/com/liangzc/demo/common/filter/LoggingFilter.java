package com.liangzc.demo.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "LoggingFilter", urlPatterns = "/*")
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1. 前置处理：记录请求信息
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestUri = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        long startTime = System.currentTimeMillis();
        log.info("=== LoggingFilter 收到请求：{} {} ===", method, requestUri);

        try {
            // 2. 放行到下一个过滤器或目标资源
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 3. 后置处理：记录响应信息和执行时间
            long endTime = System.currentTimeMillis();
            log.info("=== 完成请求：{} {}，耗时：{}ms ===", method, requestUri, (endTime - startTime));
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LoggingFilter 初始化");
    }

    @Override
    public void destroy() {
        log.info("LoggingFilter 销毁");
    }
}
