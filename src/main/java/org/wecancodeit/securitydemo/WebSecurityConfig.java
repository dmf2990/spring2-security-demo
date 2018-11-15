package org.wecancodeit.securitydemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) // Enables @PreAuthorize, etc.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
		.authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/h2-console/**", "/css/**", "/js/**").permitAll()
		.anyRequest().authenticated()
		.and()
	.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
	.logout()
		.logoutSuccessUrl("/login?logout")
		.permitAll()
		.and()
	.csrf().disable()
	.headers().frameOptions().disable();
}


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("user1").password(passwordEncoder().encode("user1")).roles("USER")
          .and()
          .withUser("user2").password(passwordEncoder().encode("user2")).roles("USER")
          .and()
          .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
    }
    
  
    
}