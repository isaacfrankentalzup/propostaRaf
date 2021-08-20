package br.com.zup.propostaRaf.propostaRaf.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/api/propostas/**").hasAuthority("SCOPE_profile")
                                .antMatchers(HttpMethod.POST, "/api/propostas/**").hasAuthority("SCOPE_profile")
                                .antMatchers(HttpMethod.GET, "/api/cartoes/**").hasAuthority("SCOPE_profile")
                                .antMatchers(HttpMethod.POST, "/api/cartoes/**").hasAuthority("SCOPE_profile")
                                  .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
/*
.antMatchers(HttpMethod.GET, "/api/v1/biometrias/**").hasAuthority("SCOPE_profile")
                                .antMatchers(HttpMethod.POST, "/api/v1/biometrias/**").hasAuthority("SCOPE_profile")

 */