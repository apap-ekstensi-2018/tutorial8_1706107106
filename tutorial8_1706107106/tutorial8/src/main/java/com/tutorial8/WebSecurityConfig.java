package com.tutorial8;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
		 .authorizeRequests()
		 .antMatchers("/").permitAll()
		 .antMatchers("/css/**").permitAll()
		 .antMatchers("/js/**").permitAll()
		 .antMatchers("/student/viewall").hasRole("ADMIN")
		 .antMatchers("/student/view/**").hasRole("USER")		
		 .anyRequest().authenticated()
		 .and()
		 .formLogin()
		 .loginPage("/login")
		 .permitAll()
		 .and()
		 .logout()
		 .permitAll();
	}
	
//	@Autowired
//	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception
//	{
//	 auth.inMemoryAuthentication()
//	 .withUser("admin").password("{noop}admin").roles("ADMIN");
//	 auth.inMemoryAuthentication()
//	 .withUser("user").password("{noop}user").roles("USER");
//	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.passwordEncoder(NoOpPasswordEncoder.getInstance())
		.usersByUsernameQuery("select username,password,enabled from users where username=?")
		.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}
}
