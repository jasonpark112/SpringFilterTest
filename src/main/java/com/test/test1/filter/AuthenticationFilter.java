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

        // 응답을 수정하기 위해 response를 래핑, 이렇게 하면 버퍼에 저장이 되고 실제 데이터를 수정할 수 있다.
        CharResponseWrapper wrappedResponse = new CharResponseWrapper((HttpServletResponse) response);

        // 다음 필터 또는 서블릿(컨트롤러) 호출 -> 최종 필터까지 가서 최종 필터의 응답값을 변형하면서 최초 여기 까지 옴
        chain.doFilter(request, wrappedResponse);

        // 원래 응답 데이터를 가져옴
        String originalResponseContent = wrappedResponse.getCapturedResponse();
        System.out.println("Original Response: " + originalResponseContent);

        // 응답 데이터를 수정 (예: 응답 본문에 요청 데이터를 추가)
        String modifiedResponseContent = originalResponseContent + "\nModified response with request data: " + requestData;

        byte[] responseBytes = modifiedResponseContent.getBytes("UTF-8");
        // content-length는 바이트 수를 요구한다. 영어, 띄어쓰기는 전부 1바이트지만, 한글은 개당 3바이트이기 때문에 단순 글자 길이로 했을 경우 전부 못 읽어 낸다.
        // UTF-8은 참고하자면 특정 언어를 컴퓨터 언어 비트인 저급언어로 변형하는 즉, 인코딩 방식이다.
        response.setContentLength(responseBytes.length);
        System.out.println(responseBytes.length); // -> 한글로 인해서 영어로만 되어 있다면 52바이트가 56바이트가 된다.


        // 수정된 응답을 클라이언트에게 전송

        //문자열을 직접 담아서 보내고, 이 문자열은 내부적으로 바이트 배열로 변환되어 전송한다. 그러면 클라이언트는 이 바이트 배열을 다시 문자열로 디코딩하여 사용자가
        //볼 수 있는 형태로 표시

        // 최종 필터까지 간 뒤, 역순으로 돌아오면서 response 데이터가 변형이 일어날텐데, 이때 버퍼에 저장해두면서 변형하고 최종 변형한 뒤, 버퍼에서 클라이언트로 응답
        response.getWriter().write(modifiedResponseContent);

//        response.getOutputStream().write(responseBytes); -> 이거는 바로 바이트 단위로 전송

    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed");
    }


}
