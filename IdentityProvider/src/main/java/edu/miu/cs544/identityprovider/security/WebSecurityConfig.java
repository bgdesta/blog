package edu.miu.cs544.identityprovider.security;

import edu.miu.cs544.identityprovider.jwt.AuthEntryPointJwt;
import edu.miu.cs544.identityprovider.jwt.JwtAuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthEntryPointJwt authEntryPointJwt;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable().cors().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
//                .antMatchers("/security/login/", "/security/register/", "/security/logout/", "/security/resetPassword/")
//                    .permitAll()
                .anyRequest().permitAll();

        httpSecurity.addFilterBefore(
                jwtAuthTokenFilter,
            UsernamePasswordAuthenticationFilter.class
        );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
