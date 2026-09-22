package com.converter;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/convert")
public class RupeeConverter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        double dollor =
            Double.parseDouble(request.getParameter("amount"));
        
        double rupees = dollor * 95.80;

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>" + dollor + " Dollor equals to : " + rupees + " Rupees</h1>"
        );
    }
}