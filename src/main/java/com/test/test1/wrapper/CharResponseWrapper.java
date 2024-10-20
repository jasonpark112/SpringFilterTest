package com.test.test1.wrapper;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CharResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private PrintWriter printWriter = null;
    private boolean isUsingWriter = false;

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (isUsingWriter) {
            return printWriter;
        }
        isUsingWriter = true;
        printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, getCharacterEncoding()));
        return printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (isUsingWriter) {
            throw new IllegalStateException("getWriter() has already been called for this response");
        }
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true; // 항상 준비 상태로 가정
            }

            @Override
            public void setWriteListener(jakarta.servlet.WriteListener listener) {
                // 비동기 처리 시 필요하지만, 이 예제에서는 구현하지 않습니다.
            }
        };
    }

    public String getCapturedResponse() throws IOException {
        if (printWriter != null) {
            printWriter.flush();
        }
        return byteArrayOutputStream.toString(getCharacterEncoding());
    }
}
