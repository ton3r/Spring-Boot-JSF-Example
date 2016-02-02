package application.web;

import java.io.IOException;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("view")
public class LoginController {


    /**
     * Método invocado al renderear la forma de login, para verificar por errores
     * @param event
     */
    public void checkLoginErrors(ComponentSystemEvent event){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/test.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .get("SPRING_SECURITY_LAST_EXCEPTION") != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "User or password incorrect", ""));
            }
        }
    }

    /**
     * Redirección al hacer login
     *
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String doLogin() throws ServletException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extenalContext = facesContext.getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest)extenalContext.getRequest()).getRequestDispatcher("/login");
        dispatcher.forward((ServletRequest)extenalContext.getRequest(), (ServletResponse)extenalContext.getResponse());
        facesContext.responseComplete();
        return null;
    }

    /**
     * Redirección al hacer logout
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String doLogout() throws ServletException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extenalContext = facesContext.getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest)extenalContext.getRequest()).getRequestDispatcher("/logout");
        dispatcher.forward((ServletRequest)extenalContext.getRequest(), (ServletResponse)extenalContext.getResponse());
        facesContext.responseComplete();
        return null;
    }

}