package com.example.userservice.config;

import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder pwdEncoder;
    private Environment env;

    @Autowired
    public WebSecurityConfig(UserService userService, BCryptPasswordEncoder pwdEncoder,
                             Environment env) {
        this.userService = userService;
        this.pwdEncoder = pwdEncoder;
        this.env = env;
    }

    // 인증
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( userService ).passwordEncoder( pwdEncoder );
    }

    // 권한
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests()
        //        .antMatchers("/users").permitAll();
                // .anyRequest().authenticated();
        http.authorizeRequests()
             .antMatchers("/users/**")
             .hasIpAddress("localhost")
             .and()
             .addFilter( getAuthenticationFilter() );

        http.csrf().disable();
        http.headers().frameOptions().disable(); // h2-console 접근
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter( authenticationManager(), userService, env );
        return authenticationFilter;
    }



}
