package ru.job4j.servlets.presentation;

import ru.job4j.servlets.logic.ValidateService;
import ru.job4j.servlets.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserUpdateServlet extends HttpServlet {

    private final ValidateService validator = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User u = validator.findById(id);
        if (u != null) {
            req.setAttribute("id", u.getId() + "");
            req.setAttribute("name", u.getName());
            req.setAttribute("login", u.getLogin());
            req.setAttribute("email", u.getEmail());
        } else {
            req.setAttribute("id", "");
            req.setAttribute("name", "");
            req.setAttribute("login", "");
            req.setAttribute("email", "");
        }
        req.getRequestDispatcher("user-create-form.jsp").forward(req, res);
    }
}
