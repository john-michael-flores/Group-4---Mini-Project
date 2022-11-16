package com.miniproject.group4.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource_id";
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous()
                .disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/payrolls/**").hasAnyRole("ADMIN", "USER")
                .mvcMatchers(HttpMethod.POST, "/payrolls").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT, "/payrolls/{id}").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/payrolls/{id}").hasRole("ADMIN")
                .anyRequest().denyAll()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        http.headers().frameOptions().disable();
    }
}
