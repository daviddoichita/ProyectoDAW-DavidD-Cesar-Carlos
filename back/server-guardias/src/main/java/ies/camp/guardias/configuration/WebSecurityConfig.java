package ies.camp.guardias.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class WebSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(requests -> requests.requestMatchers("/**").permitAll()
		.requestMatchers("/**").authenticated().anyRequest().authenticated())
		.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/cuadrante").failureUrl("/login").permitAll())
		.logout(logout -> logout.permitAll().logoutSuccessUrl("/login"));

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}


	/*
	 * @Bean
	 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception {
	 * 
	 * http.authorizeHttpRequests()
	 * .requestMatchers("/login", "/cuadrante").permitAll()
	 * 
	 * .requestMatchers("/cuadrante/firma").hasRole("USER")
	 * 
	 * .requestMatchers("/cuadrante/nueva-falta").hasAnyRole("USER", "ADMIN")
	 * 
	 * .requestMatchers("/cuadrante-direccion/").hasRole("ADMIN")
	 * 
	 * .requestMatchers("/cuadrante-direccion/gestion-profesores/**").hasRole(
	 * "ADMIN")
	 * 
	 * .requestMatchers("/cuadrante-direccion/informe-faltas/**").hasRole("ADMIN")
	 * 
	 * .requestMatchers("/cuadrante-direccion/informe-asistencias/**").hasRole(
	 * "ADMIN")
	 * 
	 * .requestMatchers("/cuadrante-direccion/informe-incidencias/**").hasRole(
	 * "ADMIN")
	 * 
	 * .anyRequest().authenticated().and().formLogin()
	 * .loginPage("/login")
	 * .defaultSuccessUrl("/cuadrante")
	 * .failureUrl("/login").permitAll().and()
	 * .logout().permitAll()
	 * .logoutSuccessUrl("/login").and().exceptionHandling()
	 * .accessDeniedPage("/errors/403");
	 * return http.build();
	 * }
	 */

}