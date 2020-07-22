package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // /h2-console/뒤에 어떤 데이터 파일이 오더라도 통과되도록 함(permition처리)
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        //쓰지 않을 부분은 사용하지 않도록 설정함.
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws  Exception{
        auth.inMemoryAuthentication()
                .withUser("Kenneth")
                .password("{noop}test1234")
                .roles("USER");
    }
}
