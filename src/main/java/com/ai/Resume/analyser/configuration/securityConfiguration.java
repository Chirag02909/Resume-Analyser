package com.ai.Resume.analyser.configuration;


import com.ai.Resume.analyser.jwt.jwtFilter;
import com.ai.Resume.analyser.service.failureHandler;
import com.ai.Resume.analyser.service.successHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfiguration {

    @Autowired
    private entryPointService userDetails;

    @Autowired
    private jwtFilter jwtfilter;

    @Autowired
    private successHandler successHandler;

    @Autowired
    private failureHandler failureHandler;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        return http
//
//                .authorizeHttpRequests(requests -> requests
//
//                        .requestMatchers(
//                                "/resumeAnalyser/entry/v1/**",
//                                "/",
//                                "/forgotpassword",
//                                "/static/**",
//                                "/index.html",
//                                "/manifest.json",
//                                "/assets/**",
//                                "/oauth2/**",
//                                "/login/**"
//                        )
//
//                        .permitAll()
//
//                        .anyRequest().authenticated()
//                )
//
//                .cors(Customizer.withDefaults())
//
//                .csrf(AbstractHttpConfigurer::disable)
//
//                .logout(AbstractHttpConfigurer::disable)
//
//                .addFilterBefore(jwtfilter,
//                        UsernamePasswordAuthenticationFilter.class)
//
//                .oauth2Login(oauth -> oauth
//                        .successHandler(successHandler)
//                        .failureHandler(failureHandler)
//                )
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                )
//
//                .build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider(userDetails);

        authenticationProvider.setPasswordEncoder(
                new BCryptPasswordEncoder(12));

        return authenticationProvider;
    }
}
