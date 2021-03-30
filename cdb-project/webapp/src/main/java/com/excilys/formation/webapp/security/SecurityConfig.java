package com.excilys.formation.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.formation.webapp.configuration.MyBasic;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private DataSource dataSource;
	
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MyBasic myBasic;
 
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder)
        .withUser("user").password(passwordEncoder.encode("123456")).roles("USER")
        .and()
        .withUser("admin").password(passwordEncoder.encode("123456")).roles("USER", "ADMIN");
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.authorizeRequests()
        .mvcMatchers("/login")
        	.permitAll()
        .mvcMatchers(HttpMethod.DELETE, "/**")
        	.hasRole("ADMIN")
        .mvcMatchers("/**")
            .hasAnyRole("ADMIN", "USER")
            .and()
        .httpBasic()
        	.realmName("realmForCurl")
        	.authenticationEntryPoint(myBasic)
            .and()
        .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/dashboard")
            .failureUrl("/login?error=true")
            .permitAll()
            .and()
        .logout()
            .logoutSuccessUrl("/login?logout=true")
            .invalidateHttpSession(true)
            .permitAll()
            .and()
        .csrf()
            .disable();
            
    }
}
