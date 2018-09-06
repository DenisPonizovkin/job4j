package ru.job4j.servlets.presentation;

import ru.job4j.servlets.model.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils {

    public static HttpServletResponse createForm(HttpServletResponse res, User u) throws IOException {
        String template =
                "<html>"
                + "<body>"
                + "<form action=\"/\" method=\"post\">"
                + "ID: <input type=\"text\" name=\"id\" value = \"%s\"><br>"
                + "Name: <input type=\"text\" name=\"name\" value = \"%s\"><br>"
                + "Email: <input type=\"text\" name=\"email\" value = \"%s\"><br>"
                + "Login: <input type=\"text\" name=\"login\" value = \"%s\"><br>"
                + "<button type=\"submit\">Save</button>"
                + "</form>"
                + "</body>"
                + "</html>";
        String form = String.format(template, "", "", "", "");
        if (!u.isEmpty()) {
            form = String.format(template, u.getId() + "", u.getName(), u.getEmail(), u.getLogin());
        }

        res.setContentType("text/html");
        PrintWriter writer = new PrintWriter(res.getOutputStream());
        writer.append(form);
        writer.flush();
        return res;
    }

}
