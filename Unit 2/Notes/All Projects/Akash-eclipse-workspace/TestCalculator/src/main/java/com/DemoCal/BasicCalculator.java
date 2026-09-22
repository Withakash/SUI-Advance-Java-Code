package com.DemoCal;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class BasicCalculator extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        // Get values from HTML form
        double num1 = Double.parseDouble(
                request.getParameter("num1"));

        double num2 = Double.parseDouble(
                request.getParameter("num2"));

        String operation =
                request.getParameter("operation");

        // Perform calculation
        double result = 0;

        if (operation.equals("add")) {

            result = num1 + num2;

        } else if (operation.equals("subtract")) {

            result = num1 - num2;

        } else if (operation.equals("multiply")) {

            result = num1 * num2;

        } else if (operation.equals("divide")) {

            if (num2 == 0) {
                response.getWriter().println(
                    "<h1>Cannot divide by zero!</h1>"
                );
                return;
            }

            result = num1 / num2;
        }

        // Send response
        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Calculator Result</h1>"
        );

        response.getWriter().println(
            "<p>First Number: " + num1 + "</p>"
        );

        response.getWriter().println(
            "<p>Second Number: " + num2 + "</p>"
        );

        response.getWriter().println(
            "<p>Operation: " + operation + "</p>"
        );

        response.getWriter().println(
            "<h2>Result: " + result + "</h2>"
        );
    }
}
