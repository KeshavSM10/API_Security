package authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtutil;

    public RequestFilter(JWTUtil jwtUtil) {
        this.jwtutil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
            throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7); // Remove "Bearer " prefix

        try {
            String username = jwtutil.ExtractUsername(token);
            if (jwtutil.validateToken(token, username)) {
            	
            	UsernamePasswordAuthenticationToken usernamepasswordauthtoken = new UsernamePasswordAuthenticationToken(username,null,null);
                SecurityContextHolder.getContext().setAuthentication(usernamepasswordauthtoken);
            }
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or expired token");
            return;
        }

        chain.doFilter(request, response);
    }
}
