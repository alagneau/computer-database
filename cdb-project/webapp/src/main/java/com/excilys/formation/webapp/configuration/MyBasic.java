package com.excilys.formation.webapp.configuration;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class MyBasic extends BasicAuthenticationEntryPoint {
	@Override
	public void afterPropertiesSet() {
		setRealmName("realmForCurl");
	}

}
