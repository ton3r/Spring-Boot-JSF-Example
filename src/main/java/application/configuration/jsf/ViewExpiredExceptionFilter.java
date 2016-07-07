package application.configuration.jsf;

import java.io.IOException;
import java.util.Enumeration;

import javax.faces.application.ViewExpiredException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for managing JSF's ViewExpiredException, it will redirect to /expired.xhtml
 */
public class ViewExpiredExceptionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        try {
            chain.doFilter(req, response);
        } catch (Exception e) {

            if (e.getCause() instanceof ViewExpiredException) {
                String url = "";
                String queryString = "";
                if (request instanceof HttpServletRequest) {
                    url = ((HttpServletRequest)request).getRequestURL().toString();
                    queryString = ((HttpServletRequest)request).getQueryString();
                }
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect("/expired.xhtml?url="+url);
            } else {
                throw e;
            }
        }
    }

    @Override
    public void destroy() {


    }

}