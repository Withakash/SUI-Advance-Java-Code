package com.test;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

ServletConfig cnf = getServletConfig(); 
        
        String value = cnf.getInitParameter("Password");
        
        
        ServletContext ctx = getServletContext();
        String uname = ctx.getInitParameter("Username");
        out.println("<h2> UserName 0 =  "+ uname+ "     </h2>");

        out.println("<h2> Password 0 =  "+ value+ " </h2>");
    }
}

