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
    	if (ls.size() == 0) {
    		for (int r = 0; r < 10; r++) {
    			for (int n = 0; n < 10; n++) {
    				Seat s = new Seat();
    				s.setBusy(false);
    				s.setRow(r + 1);
    				s.setNumber(n + 1);
    				hs.add(s);
    				ls.add(s);
    			}
    		}
    	}
    	String json = new Gson().toJson(ls);
    	res.setContentType("application/json");
    	res.setCharacterEncoding("UTF-8");
    	res.getWriter().write(json);
    }
}
