package com.example.shopapp.configurations;

import com.example.shopapp.components.JwtTokenUtil;
import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->{
                    requests.requestMatchers(
                            "api/v1/users/register",
                            "api/v1/users/login"
                            )
                            .permitAll()
                            .requestMatchers(GET,"api/v1/categories/**").permitAll()
                            .requestMatchers(POST,"api/v1/categories/**").hasRole(Role.ADMIN)
                            .requestMatchers(PUT,"api/v1/categories/**").hasRole(Role.ADMIN)
                            .requestMatchers(DELETE,"api/v1/categories/**").hasRole(Role.ADMIN)

                            .requestMatchers(GET,"api/v1/products/**").permitAll()
                            .requestMatchers(POST,"api/v1/products/**").hasRole(Role.ADMIN)
                            .requestMatchers(PUT,"api/v1/products/**").hasRole(Role.ADMIN)
                            .requestMatchers(DELETE,"api/v1/products/**").hasRole(Role.ADMIN)

                            .requestMatchers(GET, "api/v1/orders/**").hasAnyRole(Role.ADMIN,Role.USER)
                            .requestMatchers(POST, "api/v1/orders/**").hasAnyRole(Role.ADMIN,Role.USER)
                            .requestMatchers(PUT, "api/v1/orders/**").hasRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/v1/orders/**").hasRole(Role.ADMIN)

                            .requestMatchers(GET, "api/v1/orders_details/**").hasAnyRole(Role.ADMIN,Role.USER)
                            .requestMatchers(POST, "api/v1/order_details/**").hasAnyRole(Role.ADMIN,Role.USER)
                            .requestMatchers(PUT, "api/v1/order_details/**").hasRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/v1/order_details/**").hasRole(Role.ADMIN)
                            .anyRequest().authenticated();
                });
        return httpSecurity.build();
    }
}
