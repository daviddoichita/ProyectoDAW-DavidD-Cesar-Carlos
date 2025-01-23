package ies.camp.guardias.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import ies.camp.guardias.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extraer el token del header
        final String authHeader = request.getHeader("Authorization");

        // Comprobar si el token es nulo o no empieza por Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrae el jwt del header
            final String jwt = authHeader.substring(7);
            // Extrae el email del jwt
            final String userEmail = jwtService.extractUsername(jwt);

            // Recoge la autenticacion actual del contexto
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Si el usuario no esta autenticado y el email no es nulo
            if (userEmail != null && authentication == null) {
                // Carga los detalles del usuario
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Valida el token JWT
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Crea un token de autenticacion
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    // Añade los detalles de la autenticacion
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Establece la autenticacion en el contexto
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continua con la cadena
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // Controla la excepcion y la resuelve
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
