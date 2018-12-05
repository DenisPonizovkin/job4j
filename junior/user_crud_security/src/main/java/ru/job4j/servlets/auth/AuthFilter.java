package ru.job4j.servlets.auth;

import ru.job4j.servlets.model.User;

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
            User u = (User) session.getAttribute("user");
            if (u == null) {
                toSignIn(request, response);
            } else if (u.getLogin() == null) {
                toSignIn(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    private void toSignIn(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect(String.format("%s/signin", req.getContextPath()));
    }

    @Override
    public void destroy() {

    }
}
