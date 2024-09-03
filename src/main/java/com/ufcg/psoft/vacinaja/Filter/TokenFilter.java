package com.ufcg.psoft.vacinaja.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import com.ufcg.psoft.vacinaja.service.JWTServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		Cookie cookie = WebUtils.getCookie(req, "token");

		if(cookie == null) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String token = cookie.getValue();
		
		try {
			Jwts.parser().setSigningKey(JWTServiceImpl.TOKEN_KEY).parseClaimsJws(token).getBody();
			
				
		} catch (SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException
				| UnsupportedJwtException | IllegalArgumentException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}
		
		chain.doFilter(request, response);

	}

}
