//package com.servletProject.librarySystem.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/", "/login**", "/js/**", "/error**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().logout().logoutSuccessUrl("/").permitAll()
//                .and()
//                .csrf().disable();
//    }
//
//}
