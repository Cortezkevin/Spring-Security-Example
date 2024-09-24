package com.spring.security_impl.security.jwt;

import com.spring.security_impl.model.AuthUser;
import com.spring.security_impl.model.User;
import com.spring.security_impl.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(request.getRequestURI().contains("/api/auth/")){
                filterChain.doFilter( request, response );
                return;
            }

            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if( authHeader == null || authHeader.isEmpty()){
                filterChain.doFilter( request, response );
                return;
            }

            String token = authHeader.replace("Bearer", "").trim();

            String username = jwtUtils.getUsernameFromToken( token );
            Optional<User> userOptional = userRepository.findByUsername( username );

            if( userOptional.isEmpty() ){
                filterChain.doFilter( request, response );
                return;
            }

            UserDetails userDetails = new AuthUser( userOptional.get() );
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication( authentication );
            filterChain.doFilter( request, response );
        }catch (Exception e){
            handleException(e, response);
        }
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        String jsonResponse = "{\"error\": {\"message\": \"" + e.getMessage() + "\"}}";

        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
