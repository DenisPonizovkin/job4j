package ru.job4j.servlets.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ru.job4j.servlets.persistence.Account;
import ru.job4j.servlets.persistence.Seat;
import ru.job4j.servlets.service.AccountHallService;
import ru.job4j.servlets.service.HallService;

public class PayServlet extends javax.servlet.http.HttpServlet {

	private final AccountHallService ahs = new AccountHallService();
	private final HallService hs = new HallService();
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
        	BufferedReader reader = req.getReader();
        	while ((line = reader.readLine()) != null) {
        		jb.append(line);
        	}
        } catch (Exception e) {
        		System.out.print(e.getMessage());
        }

		ObjectMapper mapper = new ObjectMapper();
    	JsonNode obj = mapper.readTree(jb.toString());
    	String name = obj.get("name").asText();
        String phone = obj.get("phone").asText();
        int place = obj.get("place").asInt();
        int row = obj.get("row").asInt();
        Seat s = hs.findSeatByRowNumber(row, place);
        s.setBusy(true);

        Account a = new Account();
        a.setName(name);
        a.setPhone(phone);
        a.setSeatId(s.getId());

        try {
        	ahs.add(a, s);
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        //if (jsonTree.isJsonObject()) {
        //	JsonObject jo = jsonTree.getAsJsonObject();
        //	String name = jo.get("name").getAsString();
        //	String phone = jo.get("phone").getAsString();
        //	int place = Integer.parseInt(jo.get("place").getAsString());
        //	int row = Integer.parseInt(jo.get("row").getAsString());
        //
        //	Seat s = hs.findSeatByRowNumber(row, place);
        //	s.setBusy(true);
        //
        //	Account a = new Account();
        //	a.setName(name);
        //	a.setPhone(phone);
        //	a.setSeatId(s.getId());
        //
        //	try {
		//		ahs.add(a, s);
		//	} catch (SQLException e) {
		//		e.printStackTrace();
		//	}
        //}
        res.sendRedirect("index.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    }
}
