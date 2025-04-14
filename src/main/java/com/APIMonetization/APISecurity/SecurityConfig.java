package com.APIMonetization.APISecurity;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import authentication.JWTUtil;
import authentication.RequestFilter;
import ratelimitter.RateLimitFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JWTUtil jwtutil;

	public SecurityConfig(JWTUtil jwtutil) {
		
		this.jwtutil = jwtutil;
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login","/api/apisecurity/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new RequestFilter(jwtutil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationconfiguration) throws Exception {
        return authenticationconfiguration.getAuthenticationManager();
    }
	
	@Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitingFilter() {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
