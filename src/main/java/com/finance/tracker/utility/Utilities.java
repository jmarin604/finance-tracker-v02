package com.finance.tracker.utility;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utilities {

    private Utilities() {
		throw new IllegalStateException("Utility class");
	}

    public static String getAuthenticatedUser() {
        UsernamePasswordAuthenticationToken authenticationToken = 
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authenticationToken.getPrincipal().toString();
    }

}
