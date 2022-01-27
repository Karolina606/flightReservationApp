package com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws ServletException, IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

		String redirectURL = request.getContextPath();

		if (userDetails.hasRole("ADMIN")) {
			redirectURL = "flightRestApi";
		} else if (userDetails.hasRole("PASSENGER")) {
			redirectURL = "flightForUserRestApi";
		}
		response.sendRedirect(redirectURL);

	}

}
