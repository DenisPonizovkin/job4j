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
import java.util.function.Function;
import java.util.stream.Collectors;

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
            req.setAttribute("password", u.getPassword());
            req.setAttribute("role", u.getRole().getName());
            List<User> users = validator.findAll();
            List<String> roles = users.stream()
                    .map(new Function<User, String>() {
                        @Override
                        public String apply(User user) {
                            return user.getRole().getName();
                        }
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
            req.setAttribute("roles", roles);
        } else {
            req.setAttribute("id", "");
            req.setAttribute("name", "");
            req.setAttribute("login", "");
            req.setAttribute("email", "");
            req.setAttribute("password", "");
            req.setAttribute("role", "");
        }
        req.getRequestDispatcher("WEB-INF/view/user-create-form.jsp").forward(req, res);
    }
}
