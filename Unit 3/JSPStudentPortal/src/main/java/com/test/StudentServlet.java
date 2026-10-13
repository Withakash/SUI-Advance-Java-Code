package com.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String ageInput = request.getParameter("age");
        String marksInput = request.getParameter("marks");

        if (name == null || name.trim().isEmpty()
                || ageInput == null || marksInput == null) {

            response.sendError(400, "Please enter all details.");
            return;
        }

        try {
            int age = Integer.parseInt(ageInput);
            int marks = Integer.parseInt(marksInput);

            if (age < 1 || age > 120 || marks < 0 || marks > 100) {
                response.sendError(400, "Invalid age or marks.");
                return;
            }

            request.setAttribute("name", name.trim());
            request.setAttribute("age", age);
            request.setAttribute("marks", marks);

            List<String> students = Arrays.asList(
                "Akash", "Rahul", "Priya", "Amit"
            );

            request.setAttribute("students", students);

            request.getRequestDispatcher("/result.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(400, "Age and marks must be whole numbers.");
        }
    }
}