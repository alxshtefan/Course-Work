package ua.khnu.shtefanyankovska.filter;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/EncodingFilter")
public class EncodingFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(EncodingFilter.class.getName());

    private String encoding;

    public void init(FilterConfig fConfig) throws ServletException {
        LOG.trace("EncodingFilter init");
        encoding = fConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String enc = req.getCharacterEncoding();

        if (enc == null) {
            request.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        LOG.trace("EncodingFilter destroy");
    }

}
