package com.example.chatapplication.config.jwt;


import com.example.chatapplication.common.Category;
import com.example.chatapplication.custom.UserDetailCustom;
import com.example.chatapplication.custom.exception.GeneralException;
import com.example.chatapplication.util.JwtTokenUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtService;

    @Autowired
    private UserDetailsService userService;

    @Value("${jwt.secret}")
    private String backendSecret;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            {
        try {
            String jwt = getJwtFromRequest(request);
            System.err.println(jwt);
            if (jwt != null ) {
                String username = jwtService.getUsernameFromToken(jwt,backendSecret);

                UserDetailCustom userDetails = (UserDetailCustom) userService.loadUserByUsername(username);
                System.err.println(userDetails);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_FORMAT.name(),"Authenticaiton Fail");

        }
                System.out.println("---------------------------------------");
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
