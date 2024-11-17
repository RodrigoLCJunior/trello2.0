package org.example.trello2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings({ "removal", "deprecation" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers("/h2-console/**").permitAll() // Permitir acesso ao console H2
                .requestMatchers("/api/boards").permitAll() // Permitir acesso a /api/boards (GET)
                .requestMatchers("/api/boards/**").permitAll() // .authenticated() Requer autenticação para PUT, POST, DELETE em /api/boards
                .requestMatchers("/api/cards").permitAll() 
                .requestMatchers("/api/cards/**").permitAll()
                .requestMatchers("/api/tasklists").permitAll() 
                .requestMatchers("/api/tasklists/listar-por-coluna").permitAll() //listar-por-coluna
                .requestMatchers("/api/tasklists/**").permitAll()
                .requestMatchers("/api/tasklists/**/mover").permitAll() 
                .requestMatchers("/api/tasklists/**/editar").permitAll()
                .requestMatchers("/api/user").permitAll()
                .requestMatchers("/api/user/post").permitAll()
                .anyRequest().authenticated() // Requer autenticação para outros endpoints
            .and()
            .csrf().disable() // Desabilita CSRF
            .headers().frameOptions().disable(); // Permite uso de iframes no H2

        return http.build();
    }

    @SuppressWarnings("deprecation")
    @Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}
