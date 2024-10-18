package com.test.test1.config;


import com.test.test1.filter.AuthenticationFilter;
import com.test.test1.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean<TestFilter> loggingFilter() {
        FilterRegistrationBean<TestFilter> registrationBean = new FilterRegistrationBean<>();



        registrationBean.setFilter(new TestFilter());
        registrationBean.addUrlPatterns("/api/*"); // 필터가 적용될 URL 적용
        registrationBean.setOrder(1); //필터 순서 설정


        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/api/*");  // 필터가 적용될 URL 패턴
        registrationBean.setOrder(2);  // 필터 실행 순서 설정
        return registrationBean;
    }


}
