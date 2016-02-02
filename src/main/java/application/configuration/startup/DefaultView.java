package application.configuration.startup;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuración para redireccionar por defecto a Login al poner una URL root
 */
@Configuration
public class DefaultView extends WebMvcConfigurerAdapter{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "redirect:/login.xhtml" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );

        super.addViewControllers( registry );
    }
}