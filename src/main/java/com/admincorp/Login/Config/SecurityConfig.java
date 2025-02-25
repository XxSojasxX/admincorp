package com.admincorp.Login.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.admincorp.Login.JWT.JwtAuthenticationFilter;
import com.admincorp.Login.User.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    // Configuración de CORS
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Origen permitido (cambia si es necesario)
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(csrf ->
                        csrf
                                .disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll() // Permite el acceso sin autenticación a las rutas de auth
                                .requestMatchers("/swagger-ui/*", "/v3/api-docs/*").permitAll() // Permite el acceso a Swagger
                                .requestMatchers("/admincorp/users/**").hasAuthority(Role.ADMIN.name()) // Permite solo a ADMIN
                                .requestMatchers("/admincorp/admin/**").hasAuthority(Role.ADMIN.name()) // Permite solo a ADMIN
                                .requestMatchers("/admincorp/proyecs/**").hasAnyAuthority(Role.LEADER.name (), Role.ADMIN.name()) // Permite a LIDER y ADMIN
                                .requestMatchers("/admincorp/activities/**").hasAnyAuthority(Role.LEADER.name (), Role.ADMIN.name(), Role.STAFF.name()) // Permite a LIDER, ADMIN y STAFF
                                .requestMatchers("/admincorp/employees/**").hasAnyAuthority(Role.RH.name (), Role.ADMIN.name()) // Permite a RH y ADMIN
                                .anyRequest().authenticated() // Requiere autenticación para el resto de las rutas
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless para no usar sesiones
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}