package com.online.microservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author oxj
 * @create 2020-04-02
 */

@Configuration
@EnableTransactionManagement
@MapperScan("com.online.microservice.mapper")
//@ComponentScan("com.online.microservice.handler")
public class EduConfig {

    //乐观锁插件
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
