package com.servletProject.librarySystem.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servletProject.librarySystem.domen.Role;
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

import static com.servletProject.librarySystem.security.SecurityConstants.HEADER_STRING;
import static com.servletProject.librarySystem.security.SecurityConstants.TOKEN_PREFIX;
import static com.servletProject.librarySystem.security.SecurityConstants.SECRET;

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
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
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
                return new UsernamePasswordAuthenticationToken(user, null, getAuthority(token));
            }
            return null;
        }
        return null;
    }

    private List<Role> getAuthority(String token) {
        List<Role> authorityList = new ArrayList<>();
        String[] tokenParts = token.split("\\.");
        String body = new String(Base64.getDecoder().decode(tokenParts[1]), StandardCharsets.UTF_8);
        try {
            fillAuthorityList(authorityList, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return !authorityList.isEmpty() ? authorityList : Collections.emptyList();
    }

    private void fillAuthorityList(List<Role> authorityList, String body) throws IOException {
        Map<String, Object> map = mapper.readValue(body, Map.class);
        List<String> roles = (List<String>) map.get("Role");
        for (String role : roles) {
            authorityList.add(Role.valueOf(role));
        }
    }
}
