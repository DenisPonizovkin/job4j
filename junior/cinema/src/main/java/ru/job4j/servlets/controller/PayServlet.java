package ru.job4j.servlets.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ru.job4j.servlets.persistence.Account;
import ru.job4j.servlets.persistence.Seat;
import ru.job4j.servlets.service.AccountService;
import ru.job4j.servlets.service.HallService;
import ru.job4j.servlets.service.StoreService;

public class PayServlet extends javax.servlet.http.HttpServlet {

	private final StoreService<Account> as = new AccountService();
	private final StoreService hs = new HallService();
	
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

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jb.toString());
        if (jsonTree.isJsonObject()) {
        	JsonObject jo = jsonTree.getAsJsonObject();
        	String name = jo.get("name").toString();
        	String phone = jo.get("phone").toString();
        	String place = jo.get("place").toString();
        	String row = jo.get("row").toString();
        	
        	Seat s = new Seat();
        	s.setBusy(true);
        	s.setNumber(Integer.parseInt(place));
        	s.setRow(Integer.parseInt(row));
        	
        	hs.add(s);
        	Account a = new Account();
        	a.setName(name);
        	a.setPhone(phone);
        	a.setId(
        			(
        				((HallService) hs).findSeatByRowNumber(s.getRow(), s.getNumber())
        			).getId()
        	);
        	as.add(a);
        } 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    }
}
