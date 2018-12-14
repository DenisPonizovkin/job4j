package ru.job4j.servlets.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

import ru.job4j.servlets.persistence.Seat;
import ru.job4j.servlets.service.HallService;
import ru.job4j.servlets.service.StoreService;

public class HallServlet extends javax.servlet.http.HttpServlet {

	private final StoreService hs = new HallService();
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	List<Seat> ls = hs.findAll();
    	String json = new Gson().toJson(ls);
    	res.setContentType("application/json");
    	res.setCharacterEncoding("UTF-8");
    	res.getWriter().write(json);
    }
}
