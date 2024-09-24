package com.spring.security_impl.security;

import com.spring.security_impl.security.jwt.JwtTokenFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private CustomAuthenticationEntrypoint customAuthenticationEntrypoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorize ->
                    authorize
                            .dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.FORWARD).permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/api/public/**").permitAll()
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                )
                .exceptionHandling( httpException -> httpException
                        .authenticationEntryPoint( customAuthenticationEntrypoint )
                        .accessDeniedHandler( customAccessDeniedHandler )
                )
                .addFilterBefore( jwtTokenFilter, UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder( passwordEncoder() );
        authenticationProvider.setUserDetailsService( customUserDetailsService );
        return new ProviderManager( authenticationProvider );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
