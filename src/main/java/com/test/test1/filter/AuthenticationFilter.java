package com.test.test1.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthnticationFilter initialized");
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("AuthenticationFilter: Checking authentication");

        // 실제 인증 로직을 여기에 추가 만약 문제가 있다면 여기서 처리가능

        chain.doFilter(request,response);
        System.out.println("AuthenticationFilter: Response is being filtered");
    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed");
    }


}
