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

    public Function<HttpServletRequest, Boolean> delete() {
        return req -> {
            User u = new User();
            u.setId(Integer.parseInt(req.getParameter("id")));
            return validator.delete(u);
        };
    }

    public Function<HttpServletRequest, Boolean> update() {
        return req -> {
            User u = new User();
            u.setId(Integer.parseInt(req.getParameter("id")));
            u.setName(req.getParameter("name"));
            u.setLogin(req.getParameter("login"));
            u.setEmail(req.getParameter("email"));
            u.setCreateDate(Integer.parseInt(req.getParameter("createDate")));
            return validator.update(u);
        };
    }

    public UserServlet() {
        map = new PostParameter2Function();
        map.add("add", this.add());
        map.add("delete", this.delete());
        map.add("update", this.update());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect("users-list.jsp");
        //res.setContentType("text/html");
        //PrintWriter writer = new PrintWriter(res.getOutputStream());
        //writer.append("<html>");
        //writer.append("<body>");
        //writer.append("<table>");
        //for (User u: MemoryStore.getInstance().findAll()) {
        //    writer.append("<tr>");
        //    writer.append("<td>");
        //    writer.append("User["
        //            + u.getId() + "]<br>email: "
        //            + u.getEmail() + "<br>login: "
        //            + u.getLogin() + "<br>name:"
        //            + u.getName() + "<br>create date:"
        //            + u.getCreateDate());
        //    writer.append("</td>");

        //    writer.append("<td>");
        //    writer.append("<% int id = " + u.getId() + "%>");
        //    writer.append("<form action=\"/\" method=\"post\">");
        //    writer.append("<input type=\"hidden\" name=\"id\" value=\"<%=id%>\"/>");
        //    writer.append("<button type=\"submit\">Delete</button>");
        //    writer.append("</form>");
        //    writer.append("</td>");
        //    writer.append("</tr>");
        //}
        //writer.append("</table>");
        //writer.append("</body>");
        //writer.append("</html>");
        //writer.flush();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter writer = new PrintWriter(res.getOutputStream());
        if (!map.run(req.getParameter("action"), req)) {
            writer.append("Operation is not valid\n");
        } else {
            writer.append("Operation complete\n");
        }
        writer.flush();
    }
}
