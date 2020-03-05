package com.algamoney.algamoneyapi.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.algamoney.algamoneyapi.config.property.AlgamoneyApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFiltro implements Filter {
	
	private AlgamoneyApiProperty algamoneyApiProperty;
	
	public CorsFiltro(AlgamoneyApiProperty algamoneyApiProperty) {
		this.algamoneyApiProperty = algamoneyApiProperty;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getOrigemPermitida());
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		if (StringUtils.equalsIgnoreCase("OPTIONS", request.getMethod()) && StringUtils.equalsIgnoreCase(algamoneyApiProperty.getOrigemPermitida(), request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS, PUT");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			response.setHeader("Access-Control-Max-Age", "3600");
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
		
	}

}
