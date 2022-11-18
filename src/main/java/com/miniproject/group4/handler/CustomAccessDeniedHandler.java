package com.miniproject.group4.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.group4.enums.Message;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Map map = new LinkedHashMap();
        map.put("Status: ", HttpServletResponse.SC_UNAUTHORIZED);
        map.put("Method: ", request.getMethod());
        map.put("Message: ", Message.ACCESS_DENIED.getMessage().formatted(request.getRemoteUser(), request.getMethod(), request.getServletPath()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
