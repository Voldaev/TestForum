package MadTests.TestForum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final RequestMatcher FREE_URLS_1 = new OrRequestMatcher(
            new AntPathRequestMatcher("/**"),
            new AntPathRequestMatcher("/registration/**"));
    private static final RequestMatcher PROTECTED_URLS_2 = new OrRequestMatcher(
            new AntPathRequestMatcher("/users/**")
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .requestMatchers(FREE_URLS_1).permitAll()
                .requestMatchers(PROTECTED_URLS_2).authenticated()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/security/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
