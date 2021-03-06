package com.excilys.formation.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.excilys.formation.service"})
@Import(com.excilys.formation.dao.ContextConfig.class)
public class ContextConfig {

}
