package com.example.studentintern.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
             *user detailsService interface  takes Users name and finds in Db
         **/


        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                        .antMatchers("/api/intern/save/students/info").permitAll()
                        .antMatchers("/api/intern/login").permitAll()
                        .antMatchers("/api-docs").permitAll()
                        .antMatchers("/api/intern/*").hasRole("MANAGER")
                        .antMatchers("/api/intern/*").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

    }



    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
