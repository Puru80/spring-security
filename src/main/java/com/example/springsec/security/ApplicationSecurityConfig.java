package com.example.springsec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.springsec.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    public final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Authorise every request using Basic Auth
        http.authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                ;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userStudent = User.builder()
                        .username("Kisha Agarwal")
                        .password(passwordEncoder.encode("Kisha@2005"))
                        .roles(STUDENT.name()) //ROLE_STUDENT
                .build();
        UserDetails userAdmin = User.builder()
                .username("Puru Agarwal")
                .password(passwordEncoder.encode("Puru@1812"))
                .roles(ADMIN.name()) //ROLE_ADMIN
                .build();

        return new InMemoryUserDetailsManager(userStudent, userAdmin);
    }
}
