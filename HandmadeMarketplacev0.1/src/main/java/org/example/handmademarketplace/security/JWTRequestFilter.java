package org.example.handmademarketplace.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.handmademarketplace.Account.Account;
import org.example.handmademarketplace.Account.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AccountsService accountsService;

    @Autowired
    public JWTRequestFilter(JWTService JWTService, AccountsService accountsService) {
        this.jwtService = JWTService;
        this.accountsService = accountsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, java.io.IOException {
        String tokenHeader = request.getHeader("Authorization");
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                String token = tokenHeader.substring(7);
                try {
                    String username = jwtService.getUsername(token);

                    if(username != null){
                        Account account = accountsService.findByUsername(username);
                        if(account != null){
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, null, new ArrayList<>());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }

                }catch(JWTDecodeException ex){
                    System.out.println("Invalid token");
                }

            }
        filterChain.doFilter(request, response);
    }
}
