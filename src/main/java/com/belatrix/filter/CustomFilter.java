package com.belatrix.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestBody;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomFilter implements Filter{
	
	private static final Logger LOGGER = LogManager.getLogger(RequestBody.class);
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("########## Initiating Custom filter ##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    	HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String requestId = request.getHeader("requestId");

        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        ThreadContext.put("requestId", requestId);
        ThreadContext.put("ipAddress", request.getRemoteAddr());
        ThreadContext.put("hostname", servletRequest.getServerName());
        ThreadContext.put("locale", servletRequest.getLocale().getDisplayName());
        
        LOGGER.info("Logging Request {} : {}", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        LOGGER.info("Logging Response {} : {}", response.getStatus(), response.getContentType());
        
        ThreadContext.clearMap();
    }

    @Override
    public void destroy() {
        // TODO: 10/12/18
    }

}
