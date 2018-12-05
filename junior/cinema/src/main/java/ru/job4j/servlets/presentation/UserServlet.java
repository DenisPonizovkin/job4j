package ru.job4j.servlets.presentation;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.servlets.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class UserServlet extends javax.servlet.http.HttpServlet {

   private final ConcurrentHashMap<User, Integer> users = new ConcurrentHashMap<User, Integer>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        User u = mapper.readValue(builder.toString(), User.class);
        users.put(u, users.size() + 1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/json");
        PrintWriter writer = new PrintWriter(res.getOutputStream());
        writer.append("[{'fname': 'test_fname', 'sname': 'sname_test', 'sex': 'male', 'desc': 'test'}]");
        writer.flush();
    }

}
