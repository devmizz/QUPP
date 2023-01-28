package com.qupp.jwt;

import com.qupp.user.repository.User;
import com.qupp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String token = bearerToken.substring("Bearer ".length());

            if (jwtProvider.verify(token)) {
                Map<String, Object> claims = jwtProvider.getClaims(token);
                String email = (String) claims.get("email");
                User user = userService.findByEmail(email).orElseThrow(
                        () -> new NoSuchElementException("잘못된 접근입니다.")
                );

                forceAuthentication(user);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void forceAuthentication(User user) {
        UserContext userContext = new UserContext(user);

        UsernamePasswordAuthenticationToken authenticationFilter =
                UsernamePasswordAuthenticationToken.authenticated(
                        userContext,
                        null,
                        user.getAuthorities()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationFilter);
        SecurityContextHolder.setContext(context);
    }

}
