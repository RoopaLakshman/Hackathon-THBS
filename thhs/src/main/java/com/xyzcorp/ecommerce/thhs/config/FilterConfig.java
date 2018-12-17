package com.xyzcorp.ecommerce.thhs.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xyzcorp.ecommerce.thhs.filter.AuthenticationTokenFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<AuthenticationTokenFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthenticationTokenFilter> registrationBean = new FilterRegistrationBean<>();
		AuthenticationTokenFilter authFilter = new AuthenticationTokenFilter();

		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/product/*", "/cart/*","/submit/*");
		registrationBean.setOrder(1); // set precedence
		return registrationBean;
	}
}
