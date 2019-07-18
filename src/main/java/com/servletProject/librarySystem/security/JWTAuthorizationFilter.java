package com.servletProject.librarySystem.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servletProject.librarySystem.domen.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.servletProject.librarySystem.security.SecurityConstants.HEADER_STRING;
import static com.servletProject.librarySystem.security.SecurityConstants.TOKEN_PREFIX;
import static com.servletProject.librarySystem.security.SecurityConstants.SECRET;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            log.info("Header not found.");
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
        log.info("Authorization by the token is successful.");
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null) {
                log.info("Token accepted.");
                return new UsernamePasswordAuthenticationToken(user, null, getAuthority(token));
            }
            log.info("User not found.");
            return null;
        }
        log.info("Token not found.");
        return null;
    }

    private Set<Role> getAuthority(String token) {

        String[] tokenParts = token.split("\\.");
        String body = new String(Base64.getDecoder().decode(tokenParts[1]), StandardCharsets.UTF_8);
        try {
            return parseAuthority(body);
        } catch (IOException e) {
            log.error("Attempt to assign roles failed.", e);
            return Collections.emptySet();
        }
    }

    private Set<Role> parseAuthority(String body) throws IOException {
        return StreamSupport.stream(mapper.readTree(body)
                .get("role").spliterator(), false)
                .map(JsonNode::asText)
                .map(Role::valueOf).collect(Collectors.toSet());
    }
}
