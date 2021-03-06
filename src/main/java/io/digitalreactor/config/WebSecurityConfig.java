package io.digitalreactor.config;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.web.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by MStepachev on 07.07.2016.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/configuration/**",
                        "/accounts/**",
                        "/registration.html",
                        "/js/**",
                        "/css/**",
                        "/images/**",
                        "/fonts/**",
                        "/registration/**",
                        "/tilda_files/**",
                        "/index.html",
                        "/"
                ).permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login.html")
                .permitAll().defaultSuccessUrl("/sites.html");

        http.logout()
                .permitAll();

        http.csrf().disable();

        // http.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, AccountRepository accountRepository) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl(accountRepository)).passwordEncoder(passwordEncoder());
    }
}
