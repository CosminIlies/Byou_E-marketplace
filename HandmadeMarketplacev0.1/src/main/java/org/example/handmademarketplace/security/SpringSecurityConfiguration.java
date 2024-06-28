package org.example.handmademarketplace.security;

import org.example.handmademarketplace.Users.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    public SpringSecurityConfiguration(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


    //TODO: add csrf and headers
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomUserDetailsService customUserDetailsService) throws Exception{

        http.csrf( csrf -> csrf.disable());
        http.headers( headers -> headers.frameOptions( options -> options.disable()));

        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/products/**").permitAll()
                        .anyRequest().authenticated();
                }
        );

        http.userDetailsService(customUserDetailsService);

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
