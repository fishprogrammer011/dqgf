//package com.yipin.yihuo_admin.config;
//
//import org.jetbrains.annotations.NotNull;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @Author: wangTao
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure( HttpSecurity http) throws Exception{
//        http
//            .formLogin().loginProcessingUrl("/tologin")
//            .and().logout().logoutUrl("/api/admin/logout");
//        http.authorizeRequests()
//                //无需认证的为static下的静态资源，以及/index请求
//                .antMatchers("/api/file","/api/admin/login","**/ui/**","**/swagger-resources/**","**/swagger-ui.html/**", "/v2/api-docs","/doc.html").permitAll()
//                //其它所有请求都需要进行验证
//                .anyRequest().authenticated();
//        http.csrf().disable();
//    }
//
//}
