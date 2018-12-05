package ru.job4j.servlets.presentation;


import ru.job4j.servlets.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;

public class HallServlet extends javax.servlet.http.HttpServlet {

   private final ConcurrentHashMap<User, Integer> users = new ConcurrentHashMap<User, Integer>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	List<String> list = new ArrayList<>();
    	list.add("item1");
    	list.add("item2");
    	list.add("item3");
    	String json = new Gson().toJson(list);

    	res.setContentType("application/json");
    	res.setCharacterEncoding("UTF-8");
    	res.getWriter().write(json);
    }
}
