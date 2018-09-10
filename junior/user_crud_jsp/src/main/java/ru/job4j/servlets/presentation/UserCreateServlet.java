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
            validator.delete(user);
        } else {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String email = req.getParameter("email");

            user.setName(name);
            user.setLogin(login);
            user.setEmail(email);
            user.setCreateDate(System.currentTimeMillis() / 1000);
            if (validator.findById(id) != null) {
                validator.update(user);
            } else {
                validator.add(user);
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
        req.getRequestDispatcher("user-create-form.jsp").forward(req, res);
    }
}
