package com.sparta.topster.global.filter;

import static com.sparta.topster.domain.user.excepetion.UserException.TOKEN_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.topster.domain.user.entity.UserRoleEnum;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.security.UserDetailsServiceImpl;
import com.sparta.topster.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromHeader(request);

        if (StringUtils.hasText(tokenValue)) {
            if (!jwtUtil.validateToken(tokenValue)) {
                ObjectMapper ob = new ObjectMapper();
                response.setStatus(400);

                String json = ob.writeValueAsString(new ServiceException(TOKEN_ERROR));
                PrintWriter writer = response.getWriter();

                writer.println(json);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            if (isTokenExpired(info, response)) {
                refreshAccessToken(info.getSubject(), response);
            }

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private boolean isTokenExpired(Claims info, HttpServletResponse response) {
        Date expiration = info.getExpiration();
        if (expiration != null && expiration.before(new Date())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return true;
        }
        return false;
    }

    private void refreshAccessToken(String username, HttpServletResponse response) {
        String newAccessToken = jwtUtil.createToken(username, getUserRoleFromRedis(username));
        response.setHeader(JwtUtil.REFRESH_TOKEN_PREFIX, newAccessToken);
    }

    private UserRoleEnum getUserRoleFromRedis(String username) {
        return UserRoleEnum.USER;
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }
}
