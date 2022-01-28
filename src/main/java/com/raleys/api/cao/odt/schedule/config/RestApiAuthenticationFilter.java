package com.raleys.api.cao.odt.schedule.config;

import static com.raleys.api.cao.odt.schedule.model.Constants.HEADER_STRING;
import static com.raleys.api.cao.odt.schedule.model.Constants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author abhay.thakur
 *
 */
public class RestApiAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String reqUri = req.getRequestURI();
		if (!(reqUri.contains("swagger") || reqUri.contains("webjars") || reqUri.contains("api-docs")
				|| reqUri.contains("configuration") || reqUri.contains("actuator"))) {
			String header = req.getHeader(HEADER_STRING);
			if (header != null && header.startsWith(TOKEN_PREFIX)) {
				logger.warn("Found bearer string , Authorization");
			} else {
				logger.warn("couldn't find bearer string, Authorization in header");
				res.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"couldn't find bearer string, Authorization in header");

			}
		}
		chain.doFilter(req, res);

	}
}