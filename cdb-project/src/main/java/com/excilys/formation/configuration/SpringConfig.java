package com.excilys.formation.configuration;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.excilys.formation.dao", "com.excilys.formation.service",
		"com.excilys.formation.controller", "com.excilys.formation.view"})
public class SpringConfig extends AbstractContextLoaderInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}
	
	@Bean(destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }
}
