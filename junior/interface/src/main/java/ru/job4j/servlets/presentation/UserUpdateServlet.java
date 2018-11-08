package ru.job4j.servlets.presentation;

import ru.job4j.servlets.logic.ValidateService;
import ru.job4j.servlets.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserUpdateServlet extends HttpServlet {

    private final ValidateService validator = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User u = validator.findById(id);
        List<String> countries = new ArrayList<String>();
        countries.add("Helsinki");
        countries.add("London");
        countries.add("Moscow");
        countries.add("New Yourk");
        req.setAttribute("countries", countries);
        if (u != null) {
            req.setAttribute("id", u.getId() + "");
            req.setAttribute("name", u.getName());
            req.setAttribute("login", u.getLogin());
            req.setAttribute("email", u.getEmail());
            req.setAttribute("city", u.getCity());
            req.setAttribute("countries", countries);
        } else {
            req.setAttribute("id", "");
            req.setAttribute("name", "");
            req.setAttribute("login", "");
            req.setAttribute("email", "");
            req.setAttribute("city", "");
            req.setAttribute("countries", countries);
        }
        req.getRequestDispatcher("WEB-INF/view/user-create-form.jsp").forward(req, res);
    }
}
