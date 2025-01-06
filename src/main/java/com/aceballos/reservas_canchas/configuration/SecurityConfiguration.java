package com.aceballos.reservas_canchas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.aceballos.reservas_canchas.configuration.filter.JwtAuthenticationFilter;
import com.aceballos.reservas_canchas.configuration.filter.JwtValidationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> authz
        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/auth/registro").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/usuarios").hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.GET, "/api/usuarios/{email}").hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.GET, "/api/canchas").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/canchas-activas").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/canchas/{id}").hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.POST, "/api/canchas").hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.PUT, "/api/canchas").hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.DELETE, "/api/canchas/{id}").hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.GET, "/api/reservas/{id}").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/reservas").permitAll()
        .anyRequest().authenticated())
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtValidationFilter(authenticationManager()))
        .csrf(config -> config.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return corsBean;
    }
}
