package com.test.test1.filter;


import jakarta.servlet.*;

import java.io.IOException;

public class TestFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter initialized");
    }


    @Override
    public void doFilter(ServletRequest  request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Request is being filtered");

        // 필터 체인의 다음 단계로 요청을 전달
        chain.doFilter(request, response);


        System.out.println("Response is being filtered");
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter destroyed");
    }

}
