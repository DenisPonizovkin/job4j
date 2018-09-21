package ru.job4j.servlets.auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = ((HttpServletRequest) request).getSession();
        if (req.getRequestURI().contains("/signin")) {
            chain.doFilter(request, response);
        } else {
            synchronized (session) {
                if (session.getAttribute("login") == null) {
                    HttpServletResponse resp = (HttpServletResponse) response;
                    resp.sendRedirect(String.format("%s/signin", req.getContextPath()));
                    return;
                }
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
