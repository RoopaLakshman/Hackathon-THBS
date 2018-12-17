package com.xyzcorp.ecommerce.thhs.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyzcorp.ecommerce.thhs.helper.CSVFileHelper;
import com.xyzcorp.ecommerce.thhs.model.Message;

public class AuthenticationTokenFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		
		if (null == request.getHeader("token") || (request.getHeader("token").isEmpty())) {
			Message message = new Message();
			message.setCode("Bad Request");

			((HttpServletResponse) response).setHeader("Content-Type", "application/json");
			((HttpServletResponse) response).setStatus(400);
			ObjectMapper mapper = new ObjectMapper();
			String resp = mapper.writeValueAsString(message);

			response.getOutputStream().write(resp.getBytes(Charset.forName("UTF-8")));
			return;
		}
		if (null != request.getHeader("token") && (!request.getHeader("token").isEmpty())) {
			
			String token = request.getHeader("token");
			List<String> tokens = new ArrayList<String>();

			CSVFileHelper csvhelper = new CSVFileHelper();
			tokens = csvhelper.getAllTokens();
			boolean tokenExist = false;
			for(String authToken : tokens){
				String[] tokenParts = authToken.split(":");
				if(tokenParts[2].equalsIgnoreCase(token)){
					tokenExist = true;
				} 
			}
			if (!tokenExist) {
				Message message = new Message();
				message.setCode("Unauthorized");

				((HttpServletResponse) response).setHeader("Content-Type", "application/json");
				((HttpServletResponse) response).setStatus(401);
				ObjectMapper mapper = new ObjectMapper();
				String resp = mapper.writeValueAsString(message);

				response.getOutputStream().write(resp.getBytes(Charset.forName("UTF-8")));
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
