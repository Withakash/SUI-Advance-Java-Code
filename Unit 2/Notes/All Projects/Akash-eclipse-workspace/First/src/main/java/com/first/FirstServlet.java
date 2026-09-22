package com.first;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hi")
public class FirstServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
//        Scanner sc = new Scanner(System.in);
//        String name = sc.nextLine();
        String name = "Mohan";

        out.println("<h1>Hello Servlet!</h1>" + name + " hiiii");
        out.println("<p>Welcome heyeyyeyeyye.</p>");
    }
}