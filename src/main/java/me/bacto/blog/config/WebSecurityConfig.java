package me.bacto.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.bacto.blog.auth.BaseSecurityHandler;
import me.bacto.blog.auth.ajax.AjaxAuthenticationProvider;
import me.bacto.blog.auth.ajax.filter.AjaxAuthenticationFilter;
import me.bacto.blog.auth.jwt.JwtAuthenticationProvider;
import me.bacto.blog.auth.jwt.filter.JwtAuthenticationFilter;
import me.bacto.blog.auth.jwt.matcher.SkipPathRequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENTRY_POINT = "/login";
    private static final String TOKEN_ENTRY_POINT = "/token";
    private static final String ERROR_ENTRY_POINT = "/error";
    private static final String POST_ENTRY_POINT = "/post/**";
    private static final String USER_ENTRY_POINT = "/user/**";
    private static final String ROOT_ENTRY_POINT = "/**";
    private static final String AUTH_ENTRY_POINT = "/auth";
    private static final String[] SWAGGER_ENTRY_POINTS = {"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**"};

    private final ObjectMapper objectMapper;
    private final BaseSecurityHandler securityHandler;
    private final JwtAuthenticationProvider jwtProvider;
    private final AjaxAuthenticationProvider ajaxProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxProvider)
                .authenticationProvider(jwtProvider);
    }

    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter(ajaxAuthenticationMatcher(), objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(securityHandler);
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

    @Bean
    public AntPathRequestMatcher ajaxAuthenticationMatcher() {
        return new AntPathRequestMatcher(LOGIN_ENTRY_POINT, HttpMethod.POST.name());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtAuthenticationMatcher());
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

    @Bean
    public SkipPathRequestMatcher jwtAuthenticationMatcher() {
        List<String> skipUrls = new ArrayList<>();
        skipUrls.addAll(Arrays.asList(LOGIN_ENTRY_POINT, TOKEN_ENTRY_POINT, ERROR_ENTRY_POINT));
        skipUrls.addAll(Arrays.asList(POST_ENTRY_POINT));
        skipUrls.addAll(Arrays.asList(SWAGGER_ENTRY_POINTS));
        return new SkipPathRequestMatcher(skipUrls);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_ENTRY_POINTS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(TOKEN_ENTRY_POINT).permitAll()
                .antMatchers(LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(ERROR_ENTRY_POINT).permitAll()
                .antMatchers(ROOT_ENTRY_POINT).permitAll()
                .antMatchers(POST_ENTRY_POINT).permitAll()
                .antMatchers(USER_ENTRY_POINT).authenticated()
                .antMatchers(AUTH_ENTRY_POINT).authenticated()

                .anyRequest().permitAll();

    }
}
