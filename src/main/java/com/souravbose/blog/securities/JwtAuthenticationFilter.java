package com.souravbose.blog.securities;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Get Token

		String requestToken = request.getHeader("Authorization");

		System.out.println(requestToken);

		String username = null;

		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);

			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("No Token");
			} catch (ExpiredJwtException e) {
				System.out.println("Token expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid");	
			}

		} else {
			System.out.println("No Token or does not starts with Bearer");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if (this.jwtTokenHelper.validateToken(token, userDetails)) {
				// To Authenticate
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			else {
				System.out.println("Invalid token");
			}
		}
		else {
			System.out.println("Username is null or SecurityContext is not null");
		}
		
		filterChain.doFilter(request, response);
	}

}
