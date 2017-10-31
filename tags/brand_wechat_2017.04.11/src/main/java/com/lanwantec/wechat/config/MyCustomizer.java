package com.lanwantec.wechat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 需要定义好相应的Controller方法指向ErrorPage页面
 *
 * Created by lys on 17-1-17.
 */
@Configuration
public class MyCustomizer implements EmbeddedServletContainerCustomizer {

    @Value("${server.error.path:/error/error}")
    private  String ERROR_PATH ;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, ERROR_PATH));
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, ERROR_PATH));
        container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, ERROR_PATH));
        container.addErrorPages(new ErrorPage(HttpStatus.METHOD_FAILURE, ERROR_PATH));
        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, ERROR_PATH));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PATH));
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new MyCustomizer();
    }
}
