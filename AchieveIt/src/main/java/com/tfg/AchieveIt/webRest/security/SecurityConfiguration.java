package com.tfg.AchieveIt.webRest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.impl.crypto.MacProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserService userService;
    private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    private static final byte[] secretBytes = secret.getEncoded();
    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);


    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    corsConfig.setAllowedHeaders(Arrays.asList("*"));
                    return corsConfig;
                })
                .and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.OPTIONS,"/login/**").permitAll()
                .anyRequest().permitAll()
                //.anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        User user = userService.processOAuthPostLogin(oauthUser);

                        String token = Jwts.builder()
                                .setSubject(user.getId().toString())
                                .signWith(SignatureAlgorithm.HS256, base64SecretBytes)
                                .compact();

                        String redirectUrl = "http://localhost:5173/Home?token=" + token;
                        response.sendRedirect(redirectUrl);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

                        System.out.println("error" + exception.getMessage());
                    }
                })
                .and()
                .oauth2Client();

        return http.build();
    }

    public String getJwtSecret() {
        return base64SecretBytes;
    }
}
