package ru.job4j.servlets.model;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PostParameter2Function {

    private final Map<String, Function<HttpServletRequest, Boolean>> map = new HashMap<>();

    public void add(String parameterNanme, Function<HttpServletRequest, Boolean> f) {
        map.put(parameterNanme, f);
    }

    public void run(String key, HttpServletRequest req) {
        map.get(key).apply(req);
    }
}
