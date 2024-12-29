package com.abn.recipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${security.user.name}") private String encodedUsername; 
	
	@Value("${security.user.password}") private String encodedPassword;

	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	                .csrf(csrf -> csrf.disable())
	                .authorizeHttpRequests(auth -> auth
	                        .requestMatchers("/api/recipes/**").authenticated()
	                        .anyRequest().permitAll()
	                )
	                .httpBasic(withDefaults());
	        return http.build();
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {
	    	
	    	String username = new String(Base64.getDecoder().decode(encodedUsername)); 
	    	String password = new String(Base64.getDecoder().decode(encodedPassword));
	    
	    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	        manager.createUser(User.withDefaultPasswordEncoder()
	                .username(username)
	                .password(password)
	                .roles("ADMIN")
	                .build());
	        return manager;
	    }
	
	
}
