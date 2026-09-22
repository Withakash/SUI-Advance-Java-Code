package com.demo;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/hii")
public class ServletLC extends HttpServlet {
	

	
	@Override
	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response)
	                     throws ServletException, IOException {

	    response.setContentType("text/html");
	    
//	    System.out.println("1 : Do get is running");

	    
	    String name = "Amaaan ";
	    response.getWriter().println(
	        "<h1>Hello Class this is "+ name +" ye Redidrect ho raha hai Anchor tag se </h1>"
	    );
	}
	

	

}
