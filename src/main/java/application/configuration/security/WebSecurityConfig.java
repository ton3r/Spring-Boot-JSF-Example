package application.configuration.security;

import application.configuration.jsf.JSFRedirectStrategy;
import application.service.SystemUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuración de Spring Security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    SystemUserDetailsService systemUserService;

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
        auth.userDetailsService(systemUserService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    /**
     * We want to ignore all public resource files from the security filter, otherwise it will deny cache on the client side
     * @param web web security object
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/javax.faces.resource/**");
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }

    /**
     * In case session expires, JSF must AJAX-redirect to the login page
     * @return session management filter
     */
    @Bean
    public SessionManagementFilter sessionManagementFilter(){
        SessionManagementFilter filter = new SessionManagementFilter(httpSessionSecurityContextRepository());
        JSFRedirectStrategy strategy = new JSFRedirectStrategy();
        strategy.setInvalidSessionUrl("/login.xhtml");

        filter.setInvalidSessionStrategy(strategy);
        return filter;
    }

}