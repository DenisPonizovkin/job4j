package ru.job4j.servlets.presentation;

import ru.job4j.servlets.logic.ValidateService;
import ru.job4j.servlets.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCreateServlet extends HttpServlet {

    private final ValidateService validator = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        int id = Integer.parseInt(req.getParameter("id"));
        User user = new User();
        user.setId(id);
        if ((operation != null) && (operation.contains("delete"))) {
            if (validator.findById(id).getRole().equals("admin")) {
                validator.delete(user);
            } else {
                req.getSession().setAttribute("error", "You can't delete users");
                req.getRequestDispatcher("WEB-INF/view/error.jsp").forward(req, res);
            }
        } else {
            String name = req.getParameter("name");
            String login = (String) req.getSession().getAttribute("login");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = req.getParameter("role");

            user.setName(name);
            user.setLogin(login);
            user.setEmail(email);
            user.setPassword(password);
            user.setCreateDate(System.currentTimeMillis() / 1000);
            user.setRole(validator.getRoleByName(role));
            User oldInfo = validator.findById(id);
            if (oldInfo != null) {
                if (oldInfo.getLogin().equals(login) || oldInfo.getRole().getName().equals("admin")) {
                    validator.update(user);
                } else {
                    req.getSession().setAttribute("error", "You can't edit users");
                    req.getRequestDispatcher("WEB-INF/view/error.jsp").forward(req, res);
                }
            } else {
                if (validator.findByLogin(login).getRole().getName().equals("admin")) {
                    validator.add(user);
                } else {
                    req.getSession().setAttribute("error", "You can't add users");
                    req.getRequestDispatcher("WEB-INF/view/error.jsp").forward(req, res);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User u = new User();
        req.setAttribute("id", "");
        req.setAttribute("name", "");
        req.setAttribute("login", "");
        req.setAttribute("email", "");
        req.setAttribute("role", "");
        req.setAttribute("password", "");
        req.getRequestDispatcher("WEB-INF/view/user-create-form.jsp").forward(req, res);
    }
}
