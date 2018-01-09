package br.com.verity.pause.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AADAuthenticationFilter aadAuthFilter;
    
    @Autowired
    private ADFilter adfilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/pause/**").authenticated();

        http.logout().logoutSuccessUrl("/").permitAll();

        http.authorizeRequests().anyRequest().permitAll();

        http.csrf().disable();
        //http.addFilterBefore(aadAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(adfilter, UsernamePasswordAuthenticationFilter.class);
        
    }
}
