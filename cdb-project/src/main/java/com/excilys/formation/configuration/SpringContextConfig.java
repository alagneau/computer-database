package com.excilys.formation.configuration;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.excilys.formation.dao", "com.excilys.formation.service",
		"com.excilys.formation.controller", "com.excilys.formation.view"})
public class SpringContextConfig extends AbstractContextLoaderInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringContextConfig.class);
		return context;
	}
	
	@Bean(destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        HikariDataSource dataSource = new HikariDataSource(new HikariConfig("/hikari.properties"));

        return dataSource;
    }
}
