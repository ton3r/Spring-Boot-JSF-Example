package application.configuration.startup;


import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {
    /**
     * Configuración de la consola de la base de datos H2
     *
     * @return
     */
    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
                new WebServlet());
        registrationBean.addUrlMappings("/console/*");

        //registrationBean.addInitParameter("webAllowOthers", "true");

        return registrationBean;
    }
}