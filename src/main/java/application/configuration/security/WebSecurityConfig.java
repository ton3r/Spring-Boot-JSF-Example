package application.configuration.security;

import application.repository.SystemUserRepository;
import application.service.SystemUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuración de Spring Security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    SystemUserDetailsService systemUserRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Para visualización de Consola de H2
        http.headers().frameOptions().disable();

        //CSRF es manejado por JSF
        http.csrf().disable();
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login.xhtml");
        http
                .authorizeRequests()
                .antMatchers("/login.xhtml").permitAll()
                .antMatchers("/test.xhtml").hasAnyRole("USER","ADMIN")
                .antMatchers("/admin.xhtml").hasRole("ADMIN")
                .and().formLogin().loginPage("/login.xhtml")
                .loginProcessingUrl("/login").defaultSuccessUrl("/test.xhtml").and()
                .exceptionHandling().accessDeniedPage("/denied.xhtml");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(systemUserRepository).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}