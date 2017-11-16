package com.Interceptor;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter
public class AuthFilter implements Filter{

    public AuthFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);

            String reqURI = req.getRequestURI();
            if (ses == null || !"".equals(ses.getAttribute("Login_name"))) {
                res.sendRedirect(req.getContextPath() + "/index-login.jsp");
                System.err.println("Filter Working" + req.getContextPath());

            } else {
                chain.doFilter(request, response);
            }

        } catch (IOException | ServletException t) {
            System.out.println("error in filter" + t.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
