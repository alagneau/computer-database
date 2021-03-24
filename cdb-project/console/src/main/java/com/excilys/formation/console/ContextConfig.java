package com.excilys.formation.console;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.excilys.formation.console"})
@Import(com.excilys.formation.service.ContextConfig.class)
public class ContextConfig {

}
