package com.demo;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/name")
public class Testing extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        String username = 
            request.getParameter("firstname");
        int age = Integer.parseInt(request.getParameter("age"));

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Hello " + username + " your age is : " + age + "</h1>"
        );
    }
}