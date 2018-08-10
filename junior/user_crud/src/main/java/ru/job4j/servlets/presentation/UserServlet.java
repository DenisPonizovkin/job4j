package ru.job4j.servlets.presentation;

import ru.job4j.servlets.logic.ValidateService;
import ru.job4j.servlets.model.PostParameter2Function;
import ru.job4j.servlets.model.User;
import ru.job4j.servlets.persistent.MemoryStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

public class UserServlet extends javax.servlet.http.HttpServlet {

    private final PostParameter2Function map;
    private final ValidateService validator = ValidateService.getInstance();

    public Function<HttpServletRequest, Boolean> add() {
        return req -> {
            User u = new User();
            u.setId(Integer.parseInt(req.getParameter("id")));
            u.setName(req.getParameter("name"));
            u.setLogin(req.getParameter("login"));
            u.setEmail(req.getParameter("email"));
            u.setCreateDate(Integer.parseInt(req.getParameter("createDate")));
            return validator.add(u);
        };
    }

    public UserServlet() {
        map = new PostParameter2Function();
        map.add("add", this.add());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter writer = new PrintWriter(res.getOutputStream());
        for (User u: MemoryStore.getInstance().findAll()) {
            writer.append("User "
                    + u.getId() + ": "
                    + u.getEmail() + " "
                    + u.getLogin() + " "
                    + u.getName() + " "
                    + u.getCreateDate()
                    + "<br>================================================<br>");
        }
        writer.flush();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        map.run(req.getParameter("action"), req);
    }
}
