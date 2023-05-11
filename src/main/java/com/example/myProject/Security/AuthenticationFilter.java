package com.example.myProject.Security;

import com.example.myProject.Service.CustomUserDetailsService;
import com.example.myProject.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            try {

                userName = jwtUtil.extractUsername(jwtToken);

            } catch (Exception e) {

                e.printStackTrace();

            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                userNamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthenticationToken);
            } else {

                System.out.println("Token is not validated..");

            }
        }
        filterChain.doFilter(request, response);

    }


//	@Override
//	protected void doFilterInternal(
//            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            String jwtToken = parseJwt(request);
//            if (jwtToken != null && jwtUtil.validateJwtToken(jwtToken)) {
//                String username = jwtUtil.extractUsername(jwtToken);
//
//                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception e) {
//            logger.error("Cannot set user authentication: {}", e);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//	
//	private String parseJwt(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//
//        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//            return headerAuth.substring(7, headerAuth.length());
//        }
//
//        return null;
//    }

}
