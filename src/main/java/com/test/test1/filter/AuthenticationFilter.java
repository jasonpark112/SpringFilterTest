package com.test.test1.filter;

import com.test.test1.wrapper.CharResponseWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthnticationFilter initialized");
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 요청 데이터를 읽음
        String requestData = request.getParameter("body");
        System.out.println("Request data: " + requestData); // 요청 데이터 출력 (예: 클라이언트가 보낸 'body' 파라미터)

        // 응답을 수정하기 위해 response를 래핑
        CharResponseWrapper wrappedResponse = new CharResponseWrapper((HttpServletResponse) response);

        // 다음 필터 또는 서블릿(컨트롤러) 호출
        chain.doFilter(request, wrappedResponse);

        // 원래 응답 데이터를 가져옴
        String originalResponseContent = wrappedResponse.getCapturedResponse();
        System.out.println("Original Response: " + originalResponseContent);

        // 응답 데이터를 수정 (예: 응답 본문에 요청 데이터를 추가)
        String modifiedResponseContent = originalResponseContent + "\nModified response with request data: " + requestData;

        // 수정된 응답을 클라이언트에게 전송
        response.setContentLength(modifiedResponseContent.length());

        response.getWriter().write(modifiedResponseContent);

    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed");
    }


}
