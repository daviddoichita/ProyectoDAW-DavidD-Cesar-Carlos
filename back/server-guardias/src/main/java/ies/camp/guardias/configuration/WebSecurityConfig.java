package ies.camp.guardias.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        requests -> requests.anyRequest().authenticated())
                .httpBasic(withDefaults());

        return http.build();
    }

	/*
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests()
				.requestMatchers("/login", "/cuadrante").permitAll()
				
				.requestMatchers("/cuadrante/firma").hasRole("USER")
				
				.requestMatchers("/cuadrante/nueva-falta").hasAnyRole("USER", "ADMIN")
				
				.requestMatchers("/cuadrante-direccion/").hasRole("ADMIN")
				
				.requestMatchers("/cuadrante-direccion/gestion-profesores/**").hasRole("ADMIN")
				
				.requestMatchers("/cuadrante-direccion/informe-faltas/**").hasRole("ADMIN")

				.requestMatchers("/cuadrante-direccion/informe-asistencias/**").hasRole("ADMIN")

				.requestMatchers("/cuadrante-direccion/informe-incidencias/**").hasRole("ADMIN")

				.anyRequest().authenticated().and().formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/cuadrante")
				.failureUrl("/login").permitAll().and()
				.logout().permitAll()
				.logoutSuccessUrl("/login").and().exceptionHandling()
				.accessDeniedPage("/errors/403");
		return http.build();
	} */
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception{

	return authenticationConfig.getAuthenticationManager();
	}
	
	@Bean
	@Lazy
	public PasswordEncoder passwordEncoder() {
	
		return new BCryptPasswordEncoder();
	}
}