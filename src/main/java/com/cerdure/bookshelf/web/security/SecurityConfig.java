package com.cerdure.bookshelf.web.security;

import com.cerdure.bookshelf.service.classes.login.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginServiceImpl loginService;
    private final DataSource dataSource;
    private final LoginFailureHandler loginFailureHandler;

    private static final String[] STATIC_WHITELIST = {
            "/img/**",
            "/css/**",
            "/js/**",
            "/upload-img/**",
    };

    private static final String[] DYNAMIC_WHITELIST = {
            "/", "/login/**", "/join/**", "/verify/**",
            "/book/**", "/todayBook-reset/**", "/home-search-input/**", "/bestBook/**",
            "/search/**", "/search-input/**",
            "/search-result/**", "/search-result-input/**", "/search-trend/**",
            "/event/**",
            "/review/**", "/review-my/**",
            "/inquire/**", "/inquire-detail/**",
            "/notice", "/notice-detail/**",
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {  // 회원가입 시 비밀번호 암호화에 사용할 Encoder 빈 등록
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers(DYNAMIC_WHITELIST).permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .usernameParameter("phone")
                        .passwordParameter("pw")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/")
                        .failureHandler(loginFailureHandler)
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                .and()
                    .rememberMe()
                        .rememberMeParameter("rememberMe")
                        .key("rememberMe")
                        .tokenValiditySeconds(86400)
                        .alwaysRemember(false)
                        .userDetailsService(loginService)
                        .tokenRepository(tokenRepository())
                .and()
                    .csrf().disable();

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(STATIC_WHITELIST);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder())
                .and()
                    .inMemoryAuthentication()
                        .withUser("admin").roles("ADMIN")
                        .password(passwordEncoder().encode("1234"));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}