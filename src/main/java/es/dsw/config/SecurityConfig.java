package es.dsw.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.dsw.security.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	protected void configuer(HttpSecurity http) throws Exception 
	{
        http
        		.csrf(csrf -> csrf.disable())
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
			            		.requestMatchers("/styles/**").permitAll()
			            		.requestMatchers("/js/**").permitAll()
			            		.requestMatchers("/bootstrap/**").permitAll()
			            		.requestMatchers("/img/**").permitAll()
			            
			            		.anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
    			.formLogin(form -> form
    					           .loginPage("/login")
    					           .loginProcessingUrl("/loginprocess")
    					           .permitAll()
    					   )
    			.build();   		
	}
	
}
