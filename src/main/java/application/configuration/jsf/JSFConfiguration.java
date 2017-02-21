package application.configuration.jsf;
import java.util.EnumSet;
import java.util.HashMap;


import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.faces.config.ConfigureListener;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Created by anonymous on 1/31/16.
 */
@Configuration
public class JSFConfiguration {
    /**
     * Configuración de ViewScope
     *
     * @return ViewScope
     */
    @Bean
    public static ViewScope viewScope() {
        return new ViewScope();
    }

    /**
     * Allows the use of @Scope("view") on Spring @Component, @Service and @Controller
     * beans
     */
    @Bean
    public static CustomScopeConfigurer scopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("view", viewScope());
        configurer.setScopes(hashMap);
        return configurer;
    }

    /*
     * Below sets up the Faces Servlet for Spring Boot
     */
    @Bean
    public FacesServlet facesServlet() {
        return new FacesServlet();
    }

    /**
     * Configuración de JSF
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                facesServlet(), "*.xhtml");
        registration.setName("FacesServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    /**
     * Configuración de JSF
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(
                new ConfigureListener());
    }

    /**
     * Configuración de servlet startup de JSF
     *
     * @return
     */
    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext)
                    throws ServletException {

                //Los parámetros del servlet (web.xml) son agregados aquí
                //De todas formas se debe de dejar el archivo web.xml para que no arroje errores JSF

                //Asignamos el tema de Bootstrap
                servletContext.setInitParameter("primefaces.THEME",
                        "bootstrap");
                servletContext.setInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL","true");

                //Configuración de taglibs con Spring Security
                servletContext.setInitParameter(
                        "com.sun.faces.forceLoadConfiguration", "true");
                servletContext.setInitParameter(
                        "javax.faces.FACELETS_LIBRARIES",
                        "/WEB-INF/springsecurity.taglib.xml");
                servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/springsecurity.taglib.xml");

                EnumSet<DispatcherType> tiposDispatcher = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
                servletContext.addFilter("securityFilter",
                        new DelegatingFilterProxy("springSecurityFilterChain")).addMappingForUrlPatterns(tiposDispatcher, false, "/*");

                //Configuración del filter upload de Primefaces
                servletContext.setInitParameter("primefaces.UPLOADER", "commons");
                servletContext.addFilter("PrimeFaces FileUpload Filter", new FileUploadFilter())
                        .addMappingForServletNames(tiposDispatcher, false, "FacesServlet");

                //ViewExpiredException filter register
                servletContext.addFilter("errorHandlerFilter", new ViewExpiredExceptionFilter())
                        .addMappingForUrlPatterns(tiposDispatcher,false, "/*");

            }
        };
    }
}
