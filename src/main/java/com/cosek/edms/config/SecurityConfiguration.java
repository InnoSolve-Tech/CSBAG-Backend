package com.cosek.edms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.cosek.edms.helper.Constants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final RequestLoggingFilter requestLoggingFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http  .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(AUTH_ROUTE)
                                .permitAll()
                                // User permissions
                                .requestMatchers(HttpMethod.GET, "/api/v1/users","/api/v1/users/{id}").hasAuthority(READ_USER)
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/create-users").hasAuthority(CREATE_USER)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{userID}/roles/{roleID}","/api/v1/users/update/{id}","/api/v1/users/roles-update/{id}").hasAuthority(UPDATE_USER)
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").hasAuthority(DELETE_USER)


                                // Role permissions
                                .requestMatchers(HttpMethod.GET, "/api/v1/roles/all").hasAuthority(READ_ROLE)
                                .requestMatchers(HttpMethod.POST, "/api/v1/roles/create-roles").hasAuthority(CREATE_ROLE)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/roles/add-permission").hasAuthority(UPDATE_ROLE)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/roles/remove-permission").hasAuthority(UPDATE_ROLE)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/roles/update-permissions").hasAuthority(UPDATE_ROLE)
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/**").hasAuthority(DELETE_ROLE)

                                // Permission management permissions
                                .requestMatchers(HttpMethod.GET, "/api/v1/permissions/all").hasAuthority(READ_PERMISSION)
                                .requestMatchers(HttpMethod.POST, "/api/v1/permissions/add").hasAuthority(CREATE_PERMISSION)
                                .requestMatchers(HttpMethod.POST, "/api/v1/permissions/remove").hasAuthority(DELETE_PERMISSION)

                                .requestMatchers(HttpMethod.GET, "/api/v1/procurement/all").hasAuthority(READ_REQUISITION)
                                .requestMatchers(HttpMethod.POST, "/api/v1/procurement/create").hasAuthority(CREATE_REQUISITION)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/procurement/update/{id}").hasAuthority(UPDATE_REQUISITION)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/procurement/updatestatus/{id}").hasAuthority(UPDATE_REQUISITION)

                                .requestMatchers(HttpMethod.GET, "/api/v1/bids/all").hasAuthority(READ_BIDS)
                                .requestMatchers(HttpMethod.POST, "/api/v1/bids/create").hasAuthority(CREATE_BIDS)
//                                .requestMatchers(HttpMethod.PUT, "/api/v1/bids/update/{id}").hasAuthority(UPDATE_BIDS)
//                                .requestMatchers(HttpMethod.PUT, "/api/v1/procurement/updatestatus/{id}").hasAuthority(UPDATE_REQUISITION)


                                // Dashboard permissions
                                .requestMatchers(HttpMethod.POST, USER_ROUTE).hasAuthority(CREATE_USER)
                                .requestMatchers(HttpMethod.GET, USER_ROUTE).hasAuthority(READ_USER)
                                .requestMatchers(HttpMethod.DELETE, USER_ROUTE).hasAuthority(DELETE_USER)
                                .requestMatchers(HttpMethod.PUT, USER_ROUTE).hasAuthority(UPDATE_USER)
                                .requestMatchers(HttpMethod.POST, ROLE_ROUTE).hasAuthority(CREATE_ROLE)
                                .requestMatchers(HttpMethod.GET, ROLE_ROUTE).hasAuthority(READ_ROLE)
                                .requestMatchers(HttpMethod.DELETE, ROLE_ROUTE).hasAuthority(DELETE_ROLE)
                                .requestMatchers(HttpMethod.PUT, ROLE_ROUTE).hasAuthority(UPDATE_ROLE)
                                .requestMatchers(HttpMethod.POST, PERMISSION_ROUTE).hasAuthority(CREATE_PERMISSION)
                                .requestMatchers(HttpMethod.GET, PERMISSION_ROUTE).hasAuthority(READ_PERMISSION)
                                .requestMatchers(HttpMethod.DELETE, PERMISSION_ROUTE).hasAuthority(DELETE_PERMISSION)
                                .requestMatchers(HttpMethod.PUT, PERMISSION_ROUTE).hasAuthority(UPDATE_PERMISSION)
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(requestLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
