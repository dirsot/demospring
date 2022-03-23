package com.example.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("{noop}pass")
                .authorities("ROLE_USER")
                .and()
                .withUser("user2")
                .password("{noop}pass")
                .authorities("ROLE_ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatcher(
//                        EndpointRequest.toAnyEndpoint()
//                                .excluding("health", "info", "notes"))
//                .authorizeRequests()
//                .anyRequest().hasRole("ADMIN")
//                .and()
//                .httpBasic()
        http.authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ADMIN")
                .antMatchers("/person/add", "/person/all")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "/**")
                .permitAll()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

}
